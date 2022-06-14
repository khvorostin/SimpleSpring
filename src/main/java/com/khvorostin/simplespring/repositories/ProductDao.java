package com.khvorostin.simplespring.repositories;

import com.khvorostin.simplespring.models.Product;
import com.khvorostin.simplespring.utils.SessionUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class ProductDao {

    private SessionUtil sessionUtil;

    @Autowired
    public ProductDao(SessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    public Product findById(Long id) {
        sessionUtil.openSession();
        String sql = "SELECT id, title, price FROM products WHERE id = :id";
        Session session = sessionUtil.getSession();
        Query< Product > query = session.createNativeQuery(sql, Product.class);
        query.setParameter("id", id);
        Product product = query.getSingleResult();
        sessionUtil.closeSession();
        return product;
    }

    public List<Product> findAll() {
        sessionUtil.openSession();
        String sql = "SELECT id, title, price FROM products ORDER BY id LIMIT 10"; // чтобы не тащить всё, если будет 100500 записей
        Session session = sessionUtil.getSession();
        Query< Product > query = session.createNativeQuery(sql, Product.class);
        List<Product> products = query.list();
        sessionUtil.closeSession();
        return products;
    }

    public Product saveOrUpdate(Product product) {
        Session session = sessionUtil.openTransactionSession();
        session.persist(product);
        session.flush();
        sessionUtil.closeTransactionSession();
        return product;
    }

    public void deleteById(Long id) {
        Session session = sessionUtil.openTransactionSession();
        session.remove(findById(id));
        session.flush();
        sessionUtil.closeTransactionSession();
    }
}
