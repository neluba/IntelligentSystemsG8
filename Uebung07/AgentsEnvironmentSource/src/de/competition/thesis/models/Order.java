package de.competition.thesis.models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Order implements Serializable {

    private String product;
    private int end;
    private int start;
    private int nextMoment;
    private int quantity;
    private int priority;
    private String orderName;

    @Expose(serialize = false)
    List<Product> products;

    public Order(){
        products = new ArrayList<>();
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
      products.add(product);
    }

    public String getProduct() {
        return product;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPriority() {
        return priority;
    }

    public String getOrderName() {
        return orderName;
    }

    public int getNextMoment() {
        return nextMoment;
    }

    public void setNextMoment(int nextMoment) {
        this.nextMoment = nextMoment;
    }
}
