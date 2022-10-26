package md.utm.isa.pr.food.aggregator.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDto implements Serializable {
    private Long orderId;
    private Long restaurantId;
    private List<Long> items;
    private Integer priority;
    private Double maxWait;
    private Long createdTime;
}
