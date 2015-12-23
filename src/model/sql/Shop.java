package model.sql;

import model.ui.products.Car;
import model.ui.products.Customer;
import model.ui.products.Product;
import model.ui.products.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Ivan on 25.10.2015.
 */
public interface Shop {

    Transaction sellProducts(List<Product> products, String customerName);

    void editProduct(Product p, String fieldName, Object value);

    void removeProduct(int id);

    void removeProduct(Product p);

    void addProduct(Product p);

    Product getById(int id);

    Set<String> productBrands();

    List<String> getProductsByBrand(String brand);

    ArrayList<Transaction> getTransactions();

    ArrayList<Product> getFullDataBase();

    void closeConnection();

    Transaction getLastTransaction();

    ArrayList<Customer> getAllCustomers();

}
