package md.utm.isa.pr.food.aggregator.service;

import md.utm.isa.pr.food.aggregator.dto.ClientOrderDto;
import md.utm.isa.pr.food.aggregator.dto.ResponseClientOrderDto;
import md.utm.isa.pr.food.aggregator.dto.RestaurantDto;

public interface FoodOrderingService {
    ResponseClientOrderDto postOrder(ClientOrderDto clientOrder);

    String register(RestaurantDto restaurant);
}
