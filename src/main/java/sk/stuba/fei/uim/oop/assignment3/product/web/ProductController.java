package sk.stuba.fei.uim.oop.assignment3.product.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.product.logic.IProductService;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.Amount;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductRequest;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductResponse;
import sk.stuba.fei.uim.oop.assignment3.product.web.bodies.ProductUpdateRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService service;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductResponse> getAllProducts() {
        return this.service.getAll().stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest body) {
        return new ResponseEntity<>(new ProductResponse(this.service.create(body)), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse getProduct(@PathVariable("id") Long productId) throws NotFoundException {
        return new ProductResponse(this.service.getById(productId));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse updateProduct(@PathVariable("id") Long productId, @RequestBody ProductUpdateRequest body) throws NotFoundException {
        return new ProductResponse(this.service.update(productId, body));
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable("id") Long productId) throws NotFoundException {
        this.service.delete(productId);
    }

    @GetMapping(value = "/{id}/amount", produces = MediaType.APPLICATION_JSON_VALUE)
    public Amount getAmount(@PathVariable("id") Long productId) throws NotFoundException {
        return new Amount(this.service.getAmount(productId));
    }

    @PostMapping(value = "/{id}/amount", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Amount addAmount(@PathVariable("id") Long productId, @RequestBody Amount body) throws NotFoundException {
        return new Amount(this.service.addAmount(productId, body.getAmount()));
    }
}
