package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry;
import sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntryRepository;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

@Service
public class CartEntryService implements ICartEntryService {

    @Autowired
    CartEntryRepository repository;

    @Override
    public CartEntry create() {
        return this.repository.save(new CartEntry());
    }

    @Override
    public CartEntry getById(long id) throws NotFoundException {
        CartEntry cartEntry = this.repository.findCartEntryById(id);
        if (cartEntry == null)
            throw new NotFoundException();
        return cartEntry;
    }

    @Override
    public void delete(long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }

    @Override
    public CartEntry save(CartEntry cartEntry) {
        return this.repository.save(cartEntry);
    }
}
