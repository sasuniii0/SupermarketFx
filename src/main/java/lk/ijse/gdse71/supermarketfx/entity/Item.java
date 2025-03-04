package lk.ijse.gdse71.supermarketfx.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Item {
    @Id
    private String itemId;
    private String itemName;
    private int quantity;
    private double unitPrice;
}
