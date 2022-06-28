package com.khvorostin.simplespring.market.repositories;

import com.khvorostin.simplespring.market.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository< Product, Long> {

    List< Product > findAllByPriceAfter(int minPrice);

    List< Product > findAllByPriceBefore(int maxPrice);

    List< Product > findAllByPriceBetween(int minPrice, int maxPrice);
}
