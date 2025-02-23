package lk.ijse.gdse71.supermarketfx.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailsDto {
    private String orderId;
    private String itemId;
    private int qtyOnHand;
    private double price;
}
