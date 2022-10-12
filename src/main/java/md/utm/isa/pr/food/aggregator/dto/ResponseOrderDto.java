package md.utm.isa.pr.food.aggregator.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResponseOrderDto {
    private Long orderId;
    private Long restaurantId;
    private String restaurantAddress;
    private Long estimateWaitingTime;
    private Long createdTime;
    private Long registeredTime;
}
