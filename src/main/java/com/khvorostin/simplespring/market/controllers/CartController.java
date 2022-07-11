package com.khvorostin.simplespring.market.controllers;

import com.khvorostin.simplespring.market.exceptions.ResourceNotFoundException;
import com.khvorostin.simplespring.market.models.Product;
import com.khvorostin.simplespring.market.models.ProductInCart;
import com.khvorostin.simplespring.market.services.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    private Map<Long, ProductInCart > cart = new HashMap<>();

    private ProductsService productsService;

    @PostMapping("/{id}")
    public void addProduct(@PathVariable Long id) {
        Product product = productsService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product id " + id + " not found"));

        if (!cart.containsKey(id)) {
            ProductInCart productInCart = new ProductInCart(product.getTitle(), product.getPrice());
            productInCart.setCounter(1);
            cart.put(id, productInCart);
        } else {
            ProductInCart productInCart = cart.get(id);
            productInCart.setCounter(productInCart.getCounter() + 1);
            // cart.replace(id, productInCart);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        Product product = productsService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product id " + id + " not found"));

        if (cart.containsKey(id)) {
            ProductInCart productInCart = cart.get(id);
            int count = productInCart.getCounter();
            if (count > 1) {
                productInCart.setCounter(--count);
                // cart.replace(id, productInCart);
            } else {
                cart.remove(id);
            }
        }
    }

    @GetMapping
    public Map<Long, ProductInCart > findAll() {
        return cart;
    }
}
