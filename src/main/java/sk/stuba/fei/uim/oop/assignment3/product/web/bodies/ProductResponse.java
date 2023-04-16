package sk.stuba.fei.uim.oop.assignment3.product.web.bodies;

import lombok.Getter;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;

@Getter
public class ProductResponse {

    private final long id;
    private final String name;
    private final String description;
    private final long amount;
    private final String unit;
    private final double price;

    public ProductResponse(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.amount = p.getAmount();
        this.unit = p.getUnit();
        this.price = p.getPrice();
    }
}
