package md.utm.isa.pr.food.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseClientOrderDto {
    private Long clientId;
    private List<ResponseOrderDto> orders = new ArrayList<>();
    private List<Long> unpreparedOrders = new ArrayList<>();

    public boolean isPrepared() {
        return unpreparedOrders.isEmpty();
    }
}
