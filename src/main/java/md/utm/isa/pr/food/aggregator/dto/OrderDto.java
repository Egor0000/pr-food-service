package md.utm.isa.pr.food.aggregator.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long orderId;
    private Long restaurantId;
    private List<Long> items;
    private Integer priority;
    private Double maxWait;
    private Long createdTime;
}
