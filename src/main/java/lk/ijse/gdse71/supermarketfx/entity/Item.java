package lk.ijse.gdse71.supermarketfx.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "Item")
public class Item {
    @Id
    @Column(name = "itemId")
    private String itemId;

    @Column(length = 100)
    private String itemName;
    private int quantity;

    @Column(scale = 2,name = "unit_price",precision = 10)
    private double unitPrice;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsList;
}
