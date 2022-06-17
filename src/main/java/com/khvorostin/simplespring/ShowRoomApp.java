package com.khvorostin.simplespring;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EntityScan(basePackages = "com.khvorostin.simplespring")
public class ShowRoomApp {

    public static void main(String[] args) {
        SpringApplication.run(ShowRoomApp.class, args);
    }

    @Bean
    public static SessionFactory sessionFactoryBean() {
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
    }
}
