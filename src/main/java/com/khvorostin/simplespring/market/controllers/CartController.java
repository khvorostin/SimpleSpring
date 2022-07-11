package com.khvorostin.simplespring.market.controllers;

import com.khvorostin.simplespring.market.models.ProductInCart;
import com.khvorostin.simplespring.market.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Component
@RestController
@RequestMapping("api/v1/cart")
public class CartController {

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/{id}")
    public void addProduct(@PathVariable Long id) {
        cartRepository.addProduct(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable Long id) {
        cartRepository.deleteProduct(id);
    }

    @GetMapping
    public Map<Long, ProductInCart > findAll() {
        return cartRepository.findAll();
    }
}
