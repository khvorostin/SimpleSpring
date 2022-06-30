package com.khvorostin.simplespring.services;

import com.khvorostin.simplespring.SessionFactoryBean;
import com.khvorostin.simplespring.models.Order;
import com.khvorostin.simplespring.repositories.OrderDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class OrdersService {
    private final SessionFactory sessionFactory;

    @Autowired
    private final OrderDao orderDao;

    public OrdersService(OrderDao orderDao) {
        sessionFactory = SessionFactoryBean.getSessionFactory();
        this.orderDao = orderDao;
    }

    public List< Order > findAll() {
        List< Order > orders;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            orders = orderDao.findAll(session);
            session.getTransaction().commit();
        }
        return orders;
    }

    public Order findOne(Long id) {
        Order user;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            user = orderDao.findById(session, id);
            session.getTransaction().commit();
        }
        return user;
    }
}
