package sk.stuba.fei.uim.oop.assignment3.cart.data;
import lombok.Data;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;

import javax.persistence.*;

@Data
@Entity
public class CartEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Product product;

    private Long amount;

    public CartEntry() {

    }

    public CartEntry(Product product, Long amount) {
        this.product = product;
        this.amount = amount;
    }
}
