package md.utm.isa.pr.food.aggregator.service;

import md.utm.isa.pr.food.aggregator.dto.*;
import reactor.core.publisher.Mono;

public interface FoodOrderingService {
    ResponseClientOrderDto postOrder(ClientOrderDto clientOrder) throws Exception;

    String register(RestaurantDto restaurant);

    MenuDto getMenu();

    String postRating(RestaurantRating rating) throws Exception;
}
