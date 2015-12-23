package model.ui.products;

import model.ui.products.Customer;

import java.util.*;

public class Transaction {

    //    private Product product;
    private List<Product> products;
    //    private int quantity;
    private Customer customer;
    private Calendar calendar;
    private int discount = 0;
    private double totalPrice;
    private int id;

    public Transaction() {
        products = new ArrayList<>();
        calendar = new GregorianCalendar();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        totalPrice += product.getPrice();
//        product = applyDiscount(product);
        products.add(product);
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        calculateTotalPrice();
        applyDiscount();
    }

    private void calculateTotalPrice() {
        for (Product p : products) {
            totalPrice += p.getPrice() * p.getQuantity();
        }
    }

    public void setProductsWithoutDiscount(List<Product> products) {
        this.products = products;
        calculateTotalPrice();
    }

    public double getPrice() {
        return totalPrice;
    }

    public int getDay() {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMounth() {
        return calendar.get(Calendar.MONTH) + 1;
    }

    public int getYear() {
        return calendar.get(Calendar.YEAR);
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public void setCalendar(Date date) {
        calendar.setTime(date);
    }

    private void applyDiscount() {
        if (totalPrice > 20000 && totalPrice < 30000) {
            System.out.print("5% discount applied. Old price: " + totalPrice + "$ new price: ");
            setDiscount(5);
            updatePrice();
            System.out.println(totalPrice + "$");
        } else if (totalPrice >= 30000) {
            System.out.print("10% discount applied. Old price: " + totalPrice + "$ new price: ");
            setDiscount(10);
            updatePrice();
            System.out.println(totalPrice + "$");
        }
    }

    private void updatePrice() {

        totalPrice = 0;

        for (Product p : products) {
            p.setPrice(p.getPrice() - p.getPrice() * discount / 100);
            totalPrice += p.getPrice() * p.getQuantity();
        }
    }
}
