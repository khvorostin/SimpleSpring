package com.khvorostin.simplespring.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

@Component
public class SessionUtil {

    private final SessionFactory sessionFactory;

    public SessionUtil() {
        Configuration configuration = configuration();
        sessionFactory = configuration
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }

    private Configuration configuration() {
        return new Configuration();
    }

    private Session session;

    private Transaction transaction;

    public Session getSession() {
        return session;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public Session openSession() {
        return sessionFactory.openSession();
    }

    public Session openTransactionSession() {
        session = openSession();
        transaction = session.beginTransaction();
        return session;
    }

    public void closeSession() {
        session.close();
    }

    public void closeTransactionSession() {
        transaction.commit();
        closeSession();
    }
}
