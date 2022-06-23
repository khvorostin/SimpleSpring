package com.khvorostin.simplespring.services;

import com.khvorostin.simplespring.ShowRoomApp;
import com.khvorostin.simplespring.models.Product;
import com.khvorostin.simplespring.repositories.ProductDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

@Service
public class ProductsService {

    private final SessionFactory sessionFactory;

    @Autowired
    private final ProductDao productsRepository;

    public ProductsService(ProductDao productsRepository) {
        sessionFactory = ShowRoomApp.sessionFactoryBean();
        this.productsRepository = productsRepository;
    }

    public List< Product > findAll() {
        List< Product > products;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            products = productsRepository.findAll(session);
            session.getTransaction().commit();
        }
        return products;
    }

    public Product findOne(Long id) {
        Product product;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            product = productsRepository.findById(session, id);
            session.getTransaction().commit();
        }
        return product;
    }

    public Long addProduct(String title, double cost) throws Exception {
        Product nextProduct = new Product();
        nextProduct.setTitle(title);
        nextProduct.setCost(cost);

        Product product;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            product = productsRepository.saveOrUpdate(session, nextProduct);
            session.getTransaction().commit();
        }

        if (product == null) {
            throw new Exception("New product haven't added");
        }
        return product.getId();
    }

    public void decrCost(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Product product = productsRepository.findById(session, id);
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
            productsRepository.saveOrUpdate(session, product);
            session.getTransaction().commit();
        }
    }

    public void incCost(Long id) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Product product = productsRepository.findById(session, id);
            // продукт не найден
            if (null == product) {
                return;
            }

            double cost = product.getCost();
            product.setCost(++cost);
            productsRepository.saveOrUpdate(session, product);
            session.getTransaction().commit();
        }
    }
}
