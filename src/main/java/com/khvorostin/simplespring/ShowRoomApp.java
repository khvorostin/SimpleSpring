package com.khvorostin.simplespring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.khvorostin.simplespring")
public class ShowRoomApp {

    public static void main(String[] args) {
        SpringApplication.run(ShowRoomApp.class, args);
    }
}
