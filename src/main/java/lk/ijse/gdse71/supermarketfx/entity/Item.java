package lk.ijse.gdse71.supermarketfx.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Item")
public class Item {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(length = 100 , name = "name")
    private String itemName;
    private int quantity;

    @Column(name = "price")
    private double unitPrice;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<OrderDetails> orderDetailsList;
}
