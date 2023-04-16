package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCartRepository;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartEntry;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;

import java.util.List;

@Service
public class ShoppingCartService implements IShoppingCartService {

    @Autowired
    private ShoppingCartRepository repository;

    @Autowired
    private IProductService productService;

    @Autowired
    private ICartEntryService cartEntryService;

    @Override
    public ShoppingCart create() {
        return this.repository.save(new ShoppingCart());
    }

    @Override
    public ShoppingCart getById(long id) throws NotFoundException {
        ShoppingCart cart = this.repository.findShoppingCartById(id);
        if (cart == null) {
            throw new NotFoundException();
        }
        return cart;
    }

    @Override
    public void delete(long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }

    @Override
    public ShoppingCart addToCart(long id, CartEntry body) throws NotFoundException, IllegalOperationException {
        ShoppingCart cart = this.getUnpaid(id);
        this.productService.removeAmount(body.getProductId(), body.getAmount());
        var existingEntry = this.findCartEntryWithProduct(cart.getShoppingList(), body.getProductId());
        if (existingEntry == null) {
            sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry cartEntry = cartEntryService.create();
            cartEntry.setAmount(body.getAmount());
            cartEntry.setProduct(productService.getById(body.getProductId()));
            cart.getShoppingList().add(cartEntryService.save(cartEntry));
        } else {
            existingEntry.setAmount(existingEntry.getAmount() + body.getAmount());
            cartEntryService.save(existingEntry);
        }
        return this.repository.save(cart);
    }

    @Override
    public double payForCart(long id) throws NotFoundException, IllegalOperationException {
        ShoppingCart cart = this.getUnpaid(id);
        double sum = cart.getShoppingList().stream().mapToDouble(item -> item.getAmount() * item.getProduct().getPrice()).sum();
        cart.setPayed(true);
        this.repository.save(cart);
        return sum;
    }

    private ShoppingCart getUnpaid(long id) throws NotFoundException, IllegalOperationException {
        ShoppingCart cart = this.getById(id);
        if (cart.isPayed()) {
            throw new IllegalOperationException();
        }
        return cart;
    }

    private sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry findCartEntryWithProduct(List<sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry> entries, long productId) {
        for (var entry : entries) {
            if (entry.getProduct().getId().equals(productId)) {
                return entry;
            }
        }
        return null;
    }
}
