package com.khvorostin.simplespring.skoda_showroom;

public class Product {

    private Long id;
    private String title;
    private double price;

    public Product(Long id, String title, double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return title + " (id " + id + "), цена: от " + price + " рублей";
    }
}
