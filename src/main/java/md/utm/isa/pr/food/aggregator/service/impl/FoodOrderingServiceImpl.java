package md.utm.isa.pr.food.aggregator.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import md.utm.isa.pr.food.aggregator.dto.*;
import md.utm.isa.pr.food.aggregator.service.FoodOrderingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FoodOrderingServiceImpl implements FoodOrderingService {
    private ConcurrentMap<Long, RestaurantDto> registeredRestaurants = new ConcurrentHashMap<>();
    private ConcurrentMap<Long, HttpClient> endpoints = new ConcurrentHashMap<>();

    private WebClient clientServiceEndpoint;
    private ConcurrentMap<Long, ClientOrderDto> orders = new ConcurrentHashMap<>();

    private ObjectMapper om = new ObjectMapper();
    private ConcurrentMap<Long, ResponseClientOrderDto> responseOrders = new ConcurrentHashMap<>();
    @Value("${client.address}")
    private String clientAddress;
    @Value("${client.port}")
    private int clientPort;

    @PostConstruct
    private void onInit() {
//        clientServiceEndpoint = WebClient.builder().baseUrl(String.format("http://%s:%s/%s", clientAddress, clientPort, )).build()
    }
    @Override
    public ResponseClientOrderDto postOrder(ClientOrderDto clientOrder) throws Exception {
        log.info("Received order with client id: {}", clientOrder.getClientId());

        orders.put(clientOrder.getClientId(), clientOrder);

        if (responseOrders.get(clientOrder.getClientId()) !=null) {
            log.error("ERROR");
        }
        responseOrders.put(clientOrder.getClientId(),
                ResponseClientOrderDto.builder()
                        .clientId(clientOrder.getClientId())
                        .orders(new ArrayList<>())
                        .unpreparedOrders(clientOrder.getOrders().stream()
                                .map(OrderDto::getOrderId)
                                .collect(Collectors.toList()))
                        .build());


        for (OrderDto order: clientOrder.getOrders()) {
            if (registeredRestaurants.containsKey(order.getRestaurantId())) {
                RestaurantDto restaurant = registeredRestaurants.get(order.getRestaurantId());

                HttpClient httpClient = endpoints.get(restaurant.getRestaurantId());
                if (httpClient == null) {
                    httpClient = HttpClient.newHttpClient();
                    endpoints.put(order.getRestaurantId(), httpClient);
                }

                log.info("POST order {}", order);

                HttpRequest request = HttpRequest.newBuilder().header( "content-type", "application/json")
                        .uri(new URI("http://"+restaurant.getAddress() + "/v2/order"))
                        .POST(HttpRequest.BodyPublishers.ofByteArray(om.writeValueAsString(order).getBytes()))
                        .build();

                String json = httpClient.send(request, HttpResponse.BodyHandlers.ofString()).body();
                ResponseOrderDto responseOrderDto = om.readValue(json, ResponseOrderDto.class);
                addOrderInfo(clientOrder.getClientId(), responseOrderDto);
            }
        }


        log.info("{}", responseOrders.get(clientOrder.getClientId()).isPrepared());
        return responseOrders.get(clientOrder.getClientId());
    }

    @Override
    public String register(RestaurantDto restaurant) {
        registeredRestaurants.put(restaurant.getRestaurantId(), restaurant);
        log.info("Registered restaurant {}", restaurant);
        return "OK";
    }

    @Override
    public MenuDto getMenu() {
        MenuDto menuDto = new MenuDto();
        menuDto.setRestaurants((long) registeredRestaurants.size());
        menuDto.setRestaurantData(new ArrayList<>(registeredRestaurants.values()));
        return menuDto;
    }

    private boolean addOrderInfo(long clientId, ResponseOrderDto responseOrderDto) {
        log.info("CLIENT {}", clientId);
        responseOrders.get(clientId).getOrders().add(responseOrderDto);
        responseOrders.get(clientId).getUnpreparedOrders().remove(responseOrderDto.getOrderId());
        if (responseOrders.get(clientId).isPrepared()) {
            orders.remove(clientId);
            return true;
        }
        return false;
    }
}
