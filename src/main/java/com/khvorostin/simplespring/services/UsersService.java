package com.khvorostin.simplespring.services;

import com.khvorostin.simplespring.SessionFactoryBean;
import com.khvorostin.simplespring.ShowRoomApp;
import com.khvorostin.simplespring.models.User;
import com.khvorostin.simplespring.repositories.UserDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    private final SessionFactory sessionFactory;

    @Autowired
    private final UserDao userDao;

    public UsersService(UserDao userDao) {
        sessionFactory = SessionFactoryBean.getSessionFactory();
        this.userDao = userDao;
    }

    public List< User > findAll() {
        List< User > users;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            users = userDao.findAll(session);
            session.getTransaction().commit();
        }
        return users;
    }

    public User findOne(Long id) {
        User user;
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            user = userDao.findById(session, id);
            session.getTransaction().commit();
        }
        return user;
    }
}
