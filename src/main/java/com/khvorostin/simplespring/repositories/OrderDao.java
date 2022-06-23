package com.khvorostin.simplespring.repositories;

import com.khvorostin.simplespring.ShowRoomApp;
import com.khvorostin.simplespring.models.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OrderDao {

    private SessionFactory sessionFactory;

    public OrderDao() {
        sessionFactory = ShowRoomApp.sessionFactoryBean();
    }

    public Order findById(Session session, Long id) {
        return session.get(Order.class, id);
    }

    public List< Order > findAll(Session session) {
        Query< Order > query = session.createQuery("FROM Order", Order.class);
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
