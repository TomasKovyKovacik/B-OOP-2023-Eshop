package sk.stuba.fei.uim.oop.assignment3.product.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.data.Product;
import sk.stuba.fei.uim.oop.assignment3.product.data.ProductRepository;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductRequest;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductUpdateRequest;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository repository;

    @Override
    public List<Product> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Product create(ProductRequest request) {
        return this.repository.save(new Product(request));
    }

    @Override
    public Product getById(long id) throws NotFoundException {
        Product p = this.repository.findProductById(id);
        if (p == null) {
            throw new NotFoundException();
        }
        return p;
    }

    @Override
    public Product update(long id, ProductUpdateRequest request) throws NotFoundException {
        Product p = this.getById(id);
        if (request.getName() != null) {
            p.setName(request.getName());
        }
        if (request.getDescription() != null) {
            p.setDescription(request.getDescription());
        }
        return this.repository.save(p);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }

    @Override
    public long getAmount(long id) throws NotFoundException {
        return this.getById(id).getAmount();
    }

    @Override
    public long addAmount(long id, long increment) throws NotFoundException {
        Product p = this.getById(id);
        p.setAmount(p.getAmount() + increment);
        this.repository.save(p);
        return p.getAmount();
    }

    @Override
    public void removeAmount(long id, long decrement) throws NotFoundException, IllegalOperationException {
        Product p = this.getById(id);
        if (p.getAmount() < decrement) {
            throw new IllegalOperationException();
        }
        p.setAmount(p.getAmount() - decrement);
        this.repository.save(p);
    }
}
