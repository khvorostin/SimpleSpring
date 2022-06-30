package com.khvorostin.simplespring.services;

import com.khvorostin.simplespring.SessionFactoryBean;
import com.khvorostin.simplespring.models.Product;
import com.khvorostin.simplespring.models.User;
import com.khvorostin.simplespring.repositories.OrderDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private final SessionFactory sessionFactory;

    @Autowired
    private final OrderDao orderDao;

    public HistoryService(OrderDao orderDao) {
        sessionFactory = SessionFactoryBean.getSessionFactory();
        this.orderDao = orderDao;
    }

    public List< Product > findProductsByUserId(Long id) {
        List< Product > products;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            products = orderDao.findProductsByUserId(session, id);
            session.getTransaction().commit();
        }
        return products;
    }

    public List< User > findUsersByProductId(Long id) {
        List< User > users;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            users = orderDao.findUsersByProductId(session, id);
            session.getTransaction().commit();
        }
        return users;
    }

}
