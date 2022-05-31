package com.khvorostin.simplespring.skoda_showroom;

import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductRepository {

    List< Product > productList;

    @PostConstruct
    public void fillRepository() {
        productList = Arrays.asList(
                new Product(1L, "Rapid", 1602000.00),
                new Product(2L, "Octavia", 2613000.00),
                new Product(3L, "Kodiaq", 3364000.00),
                new Product(4L, "Superb", 3386000.00),
                new Product(5L, "Karoq", 2716000.00)
        );
    }

    public List< Product > getProducts() {
        return productList;
    }

    public Product getProduct(Long id) {
        return productList.stream()
                .filter(product -> Objects.equals(product.getId(), id))
                .findAny()
                .orElse(null);
    }

    public void showAvailableProducts() {
        System.out.println("\nСейчас в шоуруме доступны следующие модели Skoda:");
        for (Product car : productList) {
            System.out.println(car);
        }
    }
}
