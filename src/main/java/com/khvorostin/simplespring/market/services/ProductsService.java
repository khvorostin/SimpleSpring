package com.khvorostin.simplespring.market.services;

import com.khvorostin.simplespring.market.models.Product;
import com.khvorostin.simplespring.market.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    private ProductRepository productRepository;

    public ProductsService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page< Product > findAll(int pageIndex, int pageSize) {
        return productRepository.findAll(PageRequest.of(pageIndex, pageSize));
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllByPriceAfter(int minPrice){
        return productRepository.findAllByPriceAfter(minPrice);
    }

    public List<Product> findAllByPriceBefore(int maxPrice){
        return productRepository.findAllByPriceBefore(maxPrice);
    }

    public List<Product> findAllByPriceBetween(int minPrice, int maxPrice){
        return productRepository.findAllByPriceBetween(minPrice, maxPrice);
    }

    public void delete(Product product) {
        productRepository.delete(product);
    }

}
