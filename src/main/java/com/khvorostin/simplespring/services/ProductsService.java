package com.khvorostin.simplespring.services;

import com.khvorostin.simplespring.models.Product;
import com.khvorostin.simplespring.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private ProductsRepository productsRepository;

    @Autowired
    public ProductsService(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List< Product > findAll() {
        return productsRepository.findAll();
    }

    public Product findOne(Long id) {
        return productsRepository.findOne(id);
    }

    public Long addProduct(String title, double cost) {
        Long nextId = productsRepository.getNextId();
        productsRepository.addProduct(new Product(nextId, title, cost));
        return nextId;
    }

}
