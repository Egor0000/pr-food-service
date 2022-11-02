package md.utm.isa.pr.food.aggregator.dto;

import lombok.Data;

@Data
public class ResponseRating {
    private Long restaurantId;
    private Double restaurantAverageRating;
    private Long preparedOrders;
}
