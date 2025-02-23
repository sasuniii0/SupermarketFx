package lk.ijse.gdse71.supermarketfx.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Item {
    private String itemId;
    private String itemName;
    private int quantity;
    private double unitPrice;
}
