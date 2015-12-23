package model.sql;

import com.mysql.jdbc.V1toV2StatementInterceptorAdapter;
import com.sun.deploy.security.ValidationState;
import com.sun.deploy.ui.FancyButton;
import model.*;
import model.Comparator;
import model.ui.products.Car;
import model.ui.products.Customer;
import model.ui.products.Product;
import model.ui.products.Transaction;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;

import java.sql.Date;
import java.util.*;

/**
 * Created by Ivan on 25.10.2015.
 */
public class CarShopSQL implements Shop {

    private final String URLDERBY = "jdbc:derby:C:/Temp/CarShopDb/carshop;";

    private Connection connection;

    private DataSource dataSource;

    private String dbType;

    private Comparator comparator = new Comparator();

    public CarShopSQL() {
    }

    public CarShopSQL(String dbType) {

        this.dbType = dbType;
        dataSource = new DataSource(dbType);
        connectToDb();
    }

    private void connectToDb() {

        try {
            connection = dataSource.getConnection();
            if (!connection.isClosed()) {
                System.out.println("Connection with db established");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {

        try {
            if (dbType.equals("derby")) {
                DriverManager.getConnection(URLDERBY + "shutdown=true");
            }

            connection.close();

        } catch (SQLException e) {
            if (e.getErrorCode() == 45000) {
                // ignore
            } else {
                e.printStackTrace();
            }

        }

        try {
            if (connection.isClosed()) {
                System.out.println("Connection with db is closed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Transaction sellProducts(List<Product> products, String customerName) {

        Customer customer = getCustomer(customerName);

        if (customer.getId() == -1) {
            addCustomer(customerName);
            customer = getCustomer(customerName);
        }

        Transaction t = new Transaction();
        generateTransactionID(t);

        t.setProducts(products);
        t.setCustomer(customer);

        addTransaction(t);
        addToSoldTable(t);
        updateStore(products);

        return t;
    }

    private void updateStore(List<Product> soldProducts) {

        String getQuantityFromStore = "SELECT quantity FROM store WHERE product_id = ?";
        String updateStoreSQL = "UPDATE store SET quantity = ? WHERE product_id = ?";

        try (PreparedStatement getQuantity = connection.prepareStatement(getQuantityFromStore);
             PreparedStatement statement = connection.prepareStatement(updateStoreSQL);) {

            for (Product p : soldProducts) {

                getQuantity.setInt(1, p.getId());
                ResultSet resultSet = getQuantity.executeQuery();
                resultSet.next();
                int quantity = resultSet.getInt("quantity");

                statement.setInt(1, (quantity - p.getQuantity()));
                statement.setInt(2, p.getId());

                statement.execute();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addToSoldTable(Transaction t) {

        String addToSoldSQL = "INSERT INTO sold_products VALUES ((SELECT product_id FROM store WHERE product_id=?),?,?,?,?,?,?," +
                "(SELECT transaction_id FROM transactions WHERE transaction_id=?))";

        try (PreparedStatement statement = connection.prepareStatement(addToSoldSQL);) {

            for (Product p : t.getProducts()) {

                statement.setInt(1, p.getId());
                statement.setString(2, p.getBrand());
                statement.setString(3, p.getName());
                statement.setString(4, p.getColor());
                statement.setString(5, p.getAdditionalInfo());
                statement.setDouble(6, p.getPrice());
                statement.setInt(7, p.getQuantity());
                statement.setInt(8, t.getId());

                statement.execute();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void generateTransactionID(Transaction t) {

        String getIDs = "SELECT transaction_id FROM transactions";

        String month = String.valueOf(t.getMounth());
        String day = String.valueOf(t.getDay());

        if (day.length() == 1) {
            day = "0" + day;
        }

        String transactionId = month + day;

        try (PreparedStatement statement = connection.prepareStatement(getIDs);) {
            ResultSet resultSet = statement.executeQuery();

            int counter = 0;

            while (resultSet.next()) {

                String storedId = String.valueOf(resultSet.getInt("transaction_id"));
                storedId = storedId.substring(0, 4);

                if (storedId.equals(transactionId)) {
                    counter++;
                }

            }

            transactionId += String.valueOf(counter);

            t.setId(Integer.parseInt(transactionId));


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addTransaction(Transaction t) {

        String addTransactionSQL = "INSERT INTO transactions VALUES (?," +
                "(SELECT customer_id FROM customers WHERE customer_name=?),?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(addTransactionSQL);) {

            statement.setInt(1, t.getId());
            statement.setString(2, t.getCustomer().getName());
            statement.setInt(3, t.getProducts().size());
            statement.setInt(4, t.getDiscount());
            statement.setDouble(5, t.getPrice());
            statement.setDate(6, new Date(Calendar.getInstance().getTimeInMillis()));

            statement.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void addCustomer(String customerName) {

        String addCustomerSQL = "INSERT INTO customers (customer_name) VALUES (?)";

        try (PreparedStatement statement = connection.prepareStatement(addCustomerSQL);) {
            statement.setString(1, customerName);

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private Customer getCustomer(String customerName) {

        Customer customer = new Customer();
        customer.setId(-1);
        String getCustomerSQL = "SELECT * FROM customers WHERE customer_name = ?";

        try (PreparedStatement statement = connection.prepareStatement(getCustomerSQL);) {
            statement.setString(1, customerName);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                customer.setName(resultSet.getString("customer_name"));
                customer.setId(resultSet.getInt("customer_id"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    private Customer getCustomer(int id) {

        Customer customer = new Customer();

        String getCustomerSQL = "SELECT * FROM customers WHERE customer_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(getCustomerSQL);) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                customer.setName(resultSet.getString("customer_name"));
                customer.setId(resultSet.getInt("customer_id"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;

    }

    @Override
    public void editProduct(Product p, String fieldName, Object value) {

        String editSQL = "";

        String editBrand = "UPDATE store SET product_brand=? WHERE product_id=?";
        String editName = "UPDATE store SET product_name=? WHERE product_id=?";
        String editColor = "UPDATE store SET color=? WHERE product_id=?";
        String editCharacteristics = "UPDATE store SET characteristics=? WHERE product_id=?";
        String editPrice = "UPDATE store SET price=? WHERE product_id=?";
        String editQuantity = "UPDATE store SET quantity=? WHERE product_id=?";
        String addQuantity = "UPDATE store SET quantity=quantity+? WHERE product_id=?";

        switch (fieldName) {
            case "brand":
                if (value instanceof String) editSQL = editBrand;
                break;
            case "name":
                if (value instanceof String) editSQL = editName;
                break;
            case "color":
                if (value instanceof String) editSQL = editColor;
                break;
            case "characteristics":
                if (value instanceof String) editSQL = editCharacteristics;
                break;
            case "price":
                if (value instanceof Double) editSQL = editPrice;
                break;
            case "quantity":
                if (value instanceof Integer) editSQL = editQuantity;
                break;
            case "addQuantity":
                if (value instanceof Integer) editSQL = addQuantity;
                break;
        }

        if (editSQL.equals("")) {
            System.out.println("incorrect input data");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(editSQL);) {
            statement.setObject(1, value);
            statement.setInt(2, p.getId());
            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void removeProduct(int id) {

        String removeFromStoreQuery = "DELETE FROM store WHERE product_id=?";
        String removeFromSoldQuery = "DELETE FROM sold_products WHERE product_id=?";

        try (PreparedStatement statement = connection.prepareStatement(removeFromSoldQuery);
             PreparedStatement statement2 = connection.prepareStatement(removeFromStoreQuery)) {
            statement.setInt(1, id);
            statement2.setInt(1, id);
            statement.execute();
            statement2.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeProduct(Product p) {
        removeProduct(p.getId());
    }

    @Override
    public void addProduct(Product p) {

        int productId = getIdIfExist(p);

        if (productId > 0) {
            p.setId(productId);
            editProduct(p, "addQuantity", p.getQuantity());
            System.out.println("Product " + p.getBrand() + " " + p.getName() + " Color: " + p.getColor()
                    + " " + p.getAdditionalInfo() + " added to store");
            return;
        }

        String addSQL = "INSERT INTO store (product_brand, product_name, " +
                "color, characteristics, price, quantity)  VALUES (?,?,?,?,?,?)";

        try (PreparedStatement statement = connection.prepareStatement(addSQL);) {
            statement.setString(1, p.getBrand());
            statement.setString(2, p.getName());
            statement.setString(3, p.getColor());
            statement.setString(4, p.getAdditionalInfo());
            statement.setDouble(5, p.getPrice());
            statement.setInt(6, p.getQuantity());

            statement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Product " + p.getBrand() + " " + p.getName() + " Color: " + p.getColor()
                + " " + p.getAdditionalInfo() + " added to store");
    }

    public void clearTransactions() {

        try (PreparedStatement statement1 = connection.prepareStatement("DELETE FROM transactions ");
             PreparedStatement statement2 = connection.prepareStatement("DELETE FROM sold_products")) {
            statement1.execute();
            statement2.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("transactions and sold tables cleared");
    }

    private int getIdIfExist(Product p) {

        String getSameProductSQL = "SELECT * FROM store WHERE product_brand=? AND product_name=? AND color=? " +
                "AND characteristics=?";

        String getSameProductDerby = "SELECT * FROM store WHERE product_brand=? AND product_name=?" +
                " AND color=? AND characteristics=?";

        String query = getSameProductSQL;

        switch (dbType) {
            case "derby":
                query = getSameProductDerby;
                break;
        }

        try (PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setString(1, p.getBrand());
            statement.setString(2, p.getName());
            statement.setString(3, p.getColor());
            statement.setString(4, p.getAdditionalInfo());

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("product_id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    @Override
    public Product getById(int id) {

        Product p = new Car();
        String getByIdSQL = "SELECT * FROM store WHERE product_id=?";

        try (PreparedStatement statement = connection.prepareStatement(getByIdSQL);) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();


            if (resultSet.next()) {
                p.setId(resultSet.getInt("product_id"));
                p.setBrand(resultSet.getString("product_brand"));
                p.setName(resultSet.getString("product_name"));
                p.setColor(resultSet.getString("color"));
                p.setAdditionalInfo(resultSet.getString("characteristics"));
                p.setPrice(resultSet.getDouble("price"));
                p.setQuantity(resultSet.getInt("quantity"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public Set<String> productBrands() {
        Set<String> brands = new TreeSet<>();

        String getBrandsSQL = "SELECT DISTINCT product_brand FROM store";

        try (PreparedStatement statement = connection.prepareStatement(getBrandsSQL)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                brands.add(resultSet.getString("product_brand"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return brands;
    }

    @Override
    public List<String> getProductsByBrand(String brand) {

        List<String> productsInfo = new ArrayList<>();

        String getProductSQL = "SELECT * FROM store WHERE product_brand=?";

        try (PreparedStatement statement = connection.prepareStatement(getProductSQL);) {

            statement.setString(1, brand);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String carInMenu = resultSet.getString("product_name") + ", Color: " +
                        resultSet.getString("color") + ", " + resultSet.getString("characteristics") + ", "
                        + resultSet.getDouble("price") + "$ ID# " + resultSet.getInt("product_id");

                int quantity = resultSet.getInt("quantity");
                if (quantity > 1) {
                    carInMenu += " (" + quantity + ") ";
                }
                if (quantity > 0) {
                    productsInfo.add(carInMenu);
                }

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productsInfo;
    }

    @Override
    public ArrayList<Transaction> getTransactions() {

        ArrayList<Transaction> transactions = new ArrayList<>();

        String getTransactionsSQL = "SELECT * FROM transactions";

        try (PreparedStatement statement = connection.prepareStatement(getTransactionsSQL);) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Transaction t = new Transaction();
                t.setId(resultSet.getInt("transaction_id"));

                List<Product> products = getProductsByTransaction(t.getId());
                Customer customer = getCustomer(resultSet.getInt("customer_id"));

                t.setProductsWithoutDiscount(products);
                t.setCustomer(customer);
                t.setDiscount(resultSet.getInt("discount"));
                t.setCalendar(resultSet.getDate("date"));

                transactions.add(t);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return transactions;
    }

    private List<Product> getProductsByTransaction(int transactionId) {

        List<Product> products = new ArrayList<>();


        String getProductsSQL = "SELECT * FROM sold_products WHERE transaction_id=?";

        try (PreparedStatement statement = connection.prepareStatement(getProductsSQL);) {

            statement.setInt(1, transactionId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Product p = new Car();

                p.setId(resultSet.getInt("product_id"));
                p.setBrand(resultSet.getString("product_brand"));
                p.setName(resultSet.getString("product_name"));
                p.setColor(resultSet.getString("color"));
                p.setAdditionalInfo(resultSet.getString("characteristics"));
                p.setPrice(resultSet.getDouble("price"));
                p.setQuantity(resultSet.getInt("quantity"));

                products.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    public ArrayList<Product> getFullDataBase() {

        ArrayList<Product> fullDb = new ArrayList<>();

        String getFullDb = "SELECT * FROM store";

        try (PreparedStatement statement = connection.prepareStatement(getFullDb);) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Product p = new Car();

                p.setId(resultSet.getInt("product_id"));
                p.setBrand(resultSet.getString("product_brand"));
                p.setName(resultSet.getString("product_name"));
                p.setColor(resultSet.getString("color"));
                p.setAdditionalInfo(resultSet.getString("characteristics"));
                p.setPrice(resultSet.getDouble("price"));
                p.setQuantity(resultSet.getInt("quantity"));

                fullDb.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(fullDb, comparator);

        return fullDb;
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {

        ArrayList<Customer> customers = new ArrayList<>();

        String getCustomersQuery = "SELECT * FROM customers";

        try (PreparedStatement statement = connection.prepareStatement(getCustomersQuery);) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                Customer c = new Customer();

                c.setId(resultSet.getInt("customer_id"));

                String[] customerName = resultSet.getString("customer_name").split(" ");

                c.setName(customerName[0]);
                String secondName = "";

                for (int i = 1; i < customerName.length; i++) {
                    secondName += customerName[i] + " ";
                }
                c.setSecondName(secondName);

                customers.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(customers, comparator);

        return customers;
    }

    @Override
    public Transaction getLastTransaction() {

        ArrayList<Transaction> transactions = getTransactions();

        return transactions.get(transactions.size() - 1);
    }
}
