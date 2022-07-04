package com.khvorostin.simplespring.market.controllers;

import com.khvorostin.simplespring.market.exceptions.ResourceNotFoundException;
import com.khvorostin.simplespring.market.models.Product;
import com.khvorostin.simplespring.market.services.ProductsService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {

    private static final int PAGE_SIZE = 10;

    private ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping
    public Page< Product > findAll(@RequestParam (name = "p", defaultValue = "1") int pageIndex) {
        if (pageIndex < 1) {
            pageIndex = 1;
        }

        return productsService.findAll(pageIndex-1, PAGE_SIZE);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productsService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product id " + id + " not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product save(@RequestBody Product product) {
//        Product product = new Product();
//        product.setId(p.getId());
//        product.setTitle(p.getTitle());
//        product.setPrice(p.getPrice());
        return productsService.save(product);
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public Product update(@RequestBody Product product) {
//        Product product = new Product();
//        product.setId(p.getId());
//        product.setTitle(p.getTitle());
//        product.setPrice(p.getPrice());
        return productsService.save(product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id) {
        productsService.delete(id);
    }
}
