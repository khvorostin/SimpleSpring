package com.khvorostin.simplespring.repositories;

import com.khvorostin.simplespring.models.Product;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProductDao {

    public ProductDao() {}

    public Product findById(Session session, Long id) {
        return session.get(Product.class, id);
    }

    public List<Product> findAll(Session session) {
        Query< Product > query = session.createQuery("FROM Product", Product.class);
        return query.getResultList();
    }

    public Product saveOrUpdate(Session session, Product product) {
        session.persist(product);
        session.flush();
        return product;
    }

    public void deleteById(Session session, Long id) {
        Product product = session.get(Product.class, id);
        session.remove(product);
        session.flush();
    }
}
