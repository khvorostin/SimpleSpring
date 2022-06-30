package com.khvorostin.simplespring.market.controllers;

import com.khvorostin.simplespring.market.models.Product;
import com.khvorostin.simplespring.market.services.ProductsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    private ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/products")
    public List< Product > findAll() {
        return productsService.findAll();
    }


    @GetMapping("/products/price")
    public List<Product> findByPrice(@RequestParam (name = "min") int minPrice, @RequestParam (name = "max") int maxPrice) {
        return productsService.findAllByPriceBetween(minPrice, maxPrice);
    }

    @GetMapping("/products/price/filter-min")
    public List<Product> findByMinPrice(@RequestParam (name = "min") int minPrice){
        return productsService.findAllByPriceAfter(minPrice);
    }

    @GetMapping("/products/price/filter-max")
    public List<Product> findByMaxPrice(@RequestParam (name = "max") int maxPrice){
        return productsService.findAllByPriceBefore(maxPrice);
    }

    @GetMapping("/products/{id}")
    public Product findById(@PathVariable Long id) {
        return productsService.findById(id).get();
    }

    @PostMapping("/products")
    public Product save(@RequestBody Product p) {
        Product product = new Product();
        product.setId(p.getId());
        product.setTitle(p.getTitle());
        product.setPrice(p.getPrice());
        return productsService.save(product);
    }

    @GetMapping("/products/delete/{id}")
    public void delete(@PathVariable Long id) {
        Product product = findById(id);
        productsService.delete(product);
    }
}
