package sk.stuba.fei.uim.oop.assignment3.cart.logic;

import sk.stuba.fei.uim.oop.assignment3.cart.data.ShoppingCart;
import sk.stuba.fei.uim.oop.assignment3.cart.web.bodies.CartEntry;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

public interface IShoppingCartService {
    ShoppingCart create();

    ShoppingCart getById(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    ShoppingCart addToCart(long id, CartEntry body) throws NotFoundException, IllegalOperationException;

    double payForCart(long id) throws NotFoundException, IllegalOperationException;
}
