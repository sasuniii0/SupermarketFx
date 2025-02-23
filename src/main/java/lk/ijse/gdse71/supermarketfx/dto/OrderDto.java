package lk.ijse.gdse71.supermarketfx.dto;

import lombok.*;

import java.sql.Date;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDto {
    private String orderId;
    private String customerId;
    private Date orderDate;

    private ArrayList<OrderDetailsDto> orderDetailsDtos;
}
