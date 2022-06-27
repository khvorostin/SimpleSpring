package com.khvorostin.simplespring.repositories;

import com.khvorostin.simplespring.models.Order;
import com.khvorostin.simplespring.models.Product;
import com.khvorostin.simplespring.models.User;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderDao {

    public OrderDao() {}

    public Order findById(Session session, Long id) {
        return session.get(Order.class, id);
    }

    public List< Order > findAll(Session session) {
        Query< Order > query = session.createQuery("FROM Order", Order.class);
        return query.getResultList();
    }

    public List< Product > findProductsByUserId(Session session, Long id) {
        NativeQuery< Product > query = session.createNativeQuery("SELECT DISTINCT p.* FROM orders AS o INNER JOIN products AS p WHERE o.user_id = :id", Product.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List< User > findUsersByProductId(Session session, Long id) {
        NativeQuery< User > query = session.createNativeQuery("SELECT DISTINCT u.* FROM orders AS o INNER JOIN users AS u WHERE o.product_id = :id", User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public Order saveOrUpdate(Session session, Order order) {
        session.persist(order);
        session.flush();
        return order;
    }

    public void deleteById(Session session, Long id) {
        Order order = session.get(Order.class, id);
        session.remove(order);
        session.flush();
    }
}
