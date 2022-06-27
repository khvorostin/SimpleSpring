package com.khvorostin.simplespring.repositories;

import com.khvorostin.simplespring.models.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {

    public UserDao() {}

    public User findById(Session session, Long id) {
        return session.get(User.class, id);
    }

    public List< User > findAll(Session session) {
        Query< User > query = session.createQuery("FROM User", User.class);
        return query.getResultList();
    }

    public User saveOrUpdate(Session session, User user) {
        session.persist(user);
        session.flush();
        return user;
    }

    public void deleteById(Session session, Long id) {
        User user = session.get(User.class, id);
        session.remove(user);
        session.flush();
    }
}
