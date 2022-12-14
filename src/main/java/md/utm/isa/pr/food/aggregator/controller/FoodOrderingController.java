package md.utm.isa.pr.food.aggregator.controller;

import lombok.RequiredArgsConstructor;
import md.utm.isa.pr.food.aggregator.dto.*;
import md.utm.isa.pr.food.aggregator.service.FoodOrderingService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class FoodOrderingController {
    private final FoodOrderingService foodOrderingService;

    @PostMapping("order")
    public ResponseClientOrderDto postOrder(@RequestBody ClientOrderDto orderDto) throws Exception {
        return foodOrderingService.postOrder(orderDto);
    }

    @PostMapping("register")
    public String register(@RequestBody RestaurantDto restaurant) {
        return foodOrderingService.register(restaurant);
    }

    @GetMapping("menu")
    public MenuDto getMenu() {
        return foodOrderingService.getMenu();
    }

    @PostMapping("rating")
    public String postRating(@RequestBody RestaurantRating rating) throws Exception {
        return foodOrderingService.postRating(rating);
    }
}
