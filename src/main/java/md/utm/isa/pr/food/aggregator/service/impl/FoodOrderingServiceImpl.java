package md.utm.isa.pr.food.aggregator.service.impl;

import lombok.extern.slf4j.Slf4j;
import md.utm.isa.pr.food.aggregator.dto.ClientOrderDto;
import md.utm.isa.pr.food.aggregator.dto.OrderDto;
import md.utm.isa.pr.food.aggregator.dto.ResponseClientOrderDto;
import md.utm.isa.pr.food.aggregator.dto.RestaurantDto;
import md.utm.isa.pr.food.aggregator.service.FoodOrderingService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
@Slf4j
public class FoodOrderingServiceImpl implements FoodOrderingService {
    private ConcurrentMap<Long, RestaurantDto> registeredRestaurants = new ConcurrentHashMap<>();
    private ConcurrentMap<Long, WebClient> endpoints = new ConcurrentHashMap<>();
    private ConcurrentMap<Long, ClientOrderDto> orders = new ConcurrentHashMap<>();

    @PostConstruct
    private void onInit() {

    }
    @Override
    public ResponseClientOrderDto postOrder(ClientOrderDto clientOrder) {
        log.info("Received order with client id: {}", clientOrder.getClientId());

        orders.put(clientOrder.getClientId(), clientOrder);

        for (OrderDto order: clientOrder.getOrders()) {
            if (registeredRestaurants.containsKey(order.getRestaurantId())) {
                RestaurantDto restaurant = registeredRestaurants.get(order.getRestaurantId());

                WebClient webClient = endpoints.get(restaurant.getRestaurantId());
                if (webClient == null) {
                    webClient = WebClient.create("http://"+restaurant.getAddress());
                    endpoints.put(order.getRestaurantId(), webClient);
                }

                log.info("POST order {}", order);

                webClient.post()
                    .uri(String.format("%s%s", restaurant.getAddress(), "/v2/order"))
                    .body(BodyInserters.fromValue(order))
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(String.class)
                        .subscribe(resp -> log.info("Response from restaurant {}: {}", restaurant.getRestaurantId(), resp));
                return new ResponseClientOrderDto();
            }
        }
        return null;
    }

    @Override
    public String register(RestaurantDto restaurant) {
        registeredRestaurants.put(restaurant.getRestaurantId(), restaurant);
        log.info("Registered restaurant (id={})", restaurant.getRestaurantId());
        return "OK";
    }
}
