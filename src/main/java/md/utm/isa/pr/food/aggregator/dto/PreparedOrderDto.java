package md.utm.isa.pr.food.aggregator.dto;

import lombok.Data;

@Data
public class PreparedOrderDto {
    private Long orderId;
    private Long tableId;
    private Long waiterId;
    private Double maxWait;
    private Long pickUpTime;
    private Long cookingTime;
    private int rating;
    private Integer restaurantId;
}
