package com.robiul.firstapk.products.entity;

public class Product {
    private int id;
    private String name;
    private String email;
    private Double price;
    private Integer quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product(int id, String name, String email, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.price = price;
        this.quantity = quantity;
    }

    public Product(String name, String email, Double price, Integer quantity) {
        this.name = name;
        this.email = email;
        this.price = price;
        this.quantity = quantity;
    }

    public Product() {
    }
}
