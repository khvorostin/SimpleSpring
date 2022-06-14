package com.khvorostin.simplespring.repositories;

import com.khvorostin.simplespring.models.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDao {

    private SessionFactory sessionFactory;

    public ProductDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Product findById(Long id) {
        Product product;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Transaction transaction = session.beginTransaction();
            String sql = "SELECT id, title, price FROM products WHERE id = :id";
            Query< Product > query = session.createNativeQuery(sql, Product.class);
            query.setParameter("id", id);
            product = query.getSingleResult();
            session.getTransaction().commit();
        }
        return product;
    }

    public List<Product> findAll() {
        List<Product> products;
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            String sql = "SELECT id, title, price FROM products ORDER BY id LIMIT 10"; // чтобы не тащить всё, если будет 100500 записей
            Query< Product > query = session.createNativeQuery(sql, Product.class);
            products = query.list();
            session.getTransaction().commit();
        }
        return products;
    }

    public Product saveOrUpdate(Product product) {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.persist(product);
            session.flush();
            session.getTransaction().commit();
        }
        return product;
    }

    public void deleteById(Long id) {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            session.remove(findById(id));
            session.flush();
            session.getTransaction().commit();
        }
    }
}
