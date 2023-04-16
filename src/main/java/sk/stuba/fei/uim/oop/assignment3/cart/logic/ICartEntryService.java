package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import sk.stuba.fei.uim.oop.assignment3.cart.data.CartEntry;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

public interface ICartEntryService {

    CartEntry create();

    CartEntry getById(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    CartEntry save(CartEntry cartEntry);
}
