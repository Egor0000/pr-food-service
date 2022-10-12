package md.utm.isa.pr.food.aggregator.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResponseClientOrderDto {
    private Long clientId;
    private List<ResponseClientOrderDto> orders = new ArrayList<>();
}
