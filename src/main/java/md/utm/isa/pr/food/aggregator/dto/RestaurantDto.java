package md.utm.isa.pr.food.aggregator.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantDto {
    private Long restaurantId;
    private String name;
    private String address;
    private Long menuItems;
    private List<Food> menu;
    private Double rating;
}
