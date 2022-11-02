package md.utm.isa.pr.food.aggregator.dto;

import lombok.Data;

import java.util.List;

@Data
public class RestaurantRating {
    private Long clientId;
    private Long orderId;
    private List<PreparedOrderDto> orders;
}
