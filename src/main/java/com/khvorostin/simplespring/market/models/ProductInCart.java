package com.khvorostin.simplespring.market.models;

public class ProductInCart {

    private String title;
    private int price;
    private int counter;

    public ProductInCart(String title, int price) {
        this.title = title;
        this.price = price;
        this.counter = 0;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
