package com.khvorostin.simplespring.services;

import com.khvorostin.simplespring.models.Product;
import com.khvorostin.simplespring.repositories.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    private ProductDao productsRepository;

    @Autowired
    public ProductsService(ProductDao productsRepository) {
        this.productsRepository = productsRepository;
    }

    public List< Product > findAll() {
        return productsRepository.findAll();
    }

    public Product findOne(Long id) {
        return productsRepository.findById(id);
    }

    public Long addProduct(String title, double cost) {
        Product nextProduct = new Product();
        nextProduct.setTitle(title);
        nextProduct.setCost(cost);

        Product product = productsRepository.saveOrUpdate(nextProduct);
        return product.getId();
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

        product.setCost(--cost);
        productsRepository.saveOrUpdate(product);
    }

    public void incCost(Long id) {
        Product product = findOne(id);
        // продукт не найден
        if (null == product) {
            return;
        }

        double cost = product.getCost();
        product.setCost(++cost);
        productsRepository.saveOrUpdate(product);
    }
}
