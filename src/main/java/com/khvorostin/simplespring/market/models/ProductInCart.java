package com.khvorostin.simplespring.market.models;

public class ProductInCart {

    private Long id;
    private String title;
    private int price;
    private int counter;

    public ProductInCart(Long id, String title, int price) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.counter = 1;
    }

    public int incrCounter() {
        return ++counter;
    }

    public int decrCounter() {
        return --counter;
    }

    public int getCounter() {
        return counter;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
}
