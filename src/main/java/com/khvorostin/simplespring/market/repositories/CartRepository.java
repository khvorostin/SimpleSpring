package com.khvorostin.simplespring.market.repositories;

import com.khvorostin.simplespring.market.exceptions.ResourceNotFoundException;
import com.khvorostin.simplespring.market.models.Product;
import com.khvorostin.simplespring.market.models.ProductInCart;
import com.khvorostin.simplespring.market.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Component
public class CartRepository {

    private final Map< Long, ProductInCart > cart = new HashMap<>();

    @Autowired
    private ProductsService productsService;

    public CartRepository() {
    }

    public void addProduct(Long id) {
        if (!cart.containsKey(id)) {
            addNewProduct(id);
        } else {
            ProductInCart productInCart = cart.get(id);
            productInCart.incrCounter();
        }
    }

    public void deleteProduct(Long id) {
        if (!cart.containsKey(id)) {
            return;
        }

        ProductInCart productInCart = cart.get(id);
        int newCount = productInCart.decrCounter();
        if (newCount < 1) {
            cart.remove(id);
        }
    }

    private void addNewProduct(Long id) {
        Product product = productsService
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product id " + id + " not found"));
        ProductInCart productInCart = new ProductInCart(product.getId(), product.getTitle(), product.getPrice());
        cart.put(id, productInCart);
    }

    @GetMapping
    public Map< Long, ProductInCart > findAll() {
        return cart;
    }
}
