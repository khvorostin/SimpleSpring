package com.khvorostin.simplespring.services;

import com.khvorostin.simplespring.models.Product;
import com.khvorostin.simplespring.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void decrCost(Long id) {
        Product product = findOne(id);
        // продукт не найден
        if (null == product) {
            return;
        }

        double cost = product.getCost();
        // цена не может быть меньше нуля
        if (cost <= 0) {
            return;
        }

        Map<String, Object> newValues = new HashMap<>();
        newValues.put("cost", --cost);

        productsRepository.edit(id, newValues);
    }

    public void incCost(Long id) {
        Product product = findOne(id);
        // продукт не найден
        if (null == product) {
            return;
        }

        double cost = product.getCost();

        Map<String, Object> newValues = new HashMap<>();
        newValues.put("cost", ++cost);

        productsRepository.edit(id, newValues);
    }
}
