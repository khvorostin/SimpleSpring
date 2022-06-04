package com.khvorostin.simplespring.repositories;

import com.khvorostin.simplespring.models.Product;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class ProductsRepository {

    private List< Product > productList;

    @PostConstruct
    public void init() {
        productList = new ArrayList<>();
        productList.add(new Product(1L, "Rapid", 1602000.00));
        productList.add(new Product(2L, "Octavia", 2613000.00));
        productList.add(new Product(3L, "Kodiaq", 3364000.00));
        productList.add(new Product(4L, "Superb", 3386000.00));
        productList.add(new Product(5L, "Karoq", 2716000.00));
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(productList);
    }

    public Product findOne(Long id) {
        return productList.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findFirst()
                .get();
    }

    public Long getNextId() {
        Product product = productList.stream().max(Comparator.comparing(Product::getId)).get();
        return product.getId() + 1;
    }

    public void addProduct(Product product) {
        productList.add(product);
    }
}
