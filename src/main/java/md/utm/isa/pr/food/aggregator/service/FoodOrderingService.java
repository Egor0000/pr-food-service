package md.utm.isa.pr.food.aggregator.service;

import md.utm.isa.pr.food.aggregator.dto.ClientOrderDto;
import md.utm.isa.pr.food.aggregator.dto.MenuDto;
import md.utm.isa.pr.food.aggregator.dto.ResponseClientOrderDto;
import md.utm.isa.pr.food.aggregator.dto.RestaurantDto;
import reactor.core.publisher.Mono;

public interface FoodOrderingService {
    ResponseClientOrderDto postOrder(ClientOrderDto clientOrder) throws Exception;

    String register(RestaurantDto restaurant);

    MenuDto getMenu();
}
