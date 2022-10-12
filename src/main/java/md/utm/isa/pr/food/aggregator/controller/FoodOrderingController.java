package md.utm.isa.pr.food.aggregator.controller;

import lombok.RequiredArgsConstructor;
import md.utm.isa.pr.food.aggregator.dto.ClientOrderDto;
import md.utm.isa.pr.food.aggregator.dto.ResponseClientOrderDto;
import md.utm.isa.pr.food.aggregator.dto.RestaurantDto;
import md.utm.isa.pr.food.aggregator.service.FoodOrderingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FoodOrderingController {
    private final FoodOrderingService foodOrderingService;

    @PostMapping("order")
    public ResponseClientOrderDto postOrder(@RequestBody ClientOrderDto orderDto) {
        return foodOrderingService.postOrder(orderDto);
    }

    @PostMapping("register")
    public String register(@RequestBody RestaurantDto restaurant) {
        return foodOrderingService.register(restaurant);
    }
}
