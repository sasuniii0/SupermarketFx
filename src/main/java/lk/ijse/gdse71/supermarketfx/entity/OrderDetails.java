package lk.ijse.gdse71.supermarketfx.entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetails {
    private String orderId;
    private String itemId;
    private int qtyOnHand;
    private double price;
}
