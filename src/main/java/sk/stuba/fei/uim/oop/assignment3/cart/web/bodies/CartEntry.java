package sk.stuba.fei.uim.oop.assignment3.cart.web.bodies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartEntry {

    private Long productId;

    private Long amount;

    public CartEntry(sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry cartEntry) {
        this.productId = cartEntry.getProduct().getId();
        this.amount = cartEntry.getAmount();
    }
}
