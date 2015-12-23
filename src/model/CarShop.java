package model;

import model.sql.Shop;
import model.ui.products.Product;

import model.ui.products.Car;
import model.ui.products.Customer;
import model.ui.products.Transaction;


import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

public class CarShop implements Shop {

    public Map<String, Product> store = new TreeMap<String, Product>();
    public Map<Integer, Product> iDStore = new HashMap<>();
    public ArrayList<Transaction> transactions = new ArrayList<>();

    private Comparator comparator = new Comparator();

    public Transaction sellProducts (List<Product> products, String customerName) {
        return null;
    }


    public void editProduct(Product p, String fieldName, Object value) {  // Remove this method!!
        Class clazz = p.getClass();                                   // Create methods for each field!!

        Method[] methods = clazz.getMethods();
        Method invokedMethod = null;

        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                invokedMethod = m;
            }
        }

        if (invokedMethod == null) {
            System.out.println("Setter for required field not found");
        } else {
            Type[] methodTypes = invokedMethod.getGenericParameterTypes();
            boolean isTypePresent = false;

            if (methodTypes.length == 1) {
                try {
                    invokedMethod.invoke(p, value);
                    System.out.println("Parameter " + fieldName + " for product " + clazz.getSimpleName() +
                            " " + p.getName() + " is changed to " + value);
                } catch (Exception e) {
                    if (e instanceof IllegalArgumentException) {
                        System.out.println("Can't set value " + value + " to parameter " + fieldName +
                                ". Argument type mismatch");
                    } else {
                        e.printStackTrace();
                    }
                }
            }


        }


    }

    public void removeProduct(int id) {
        removeProduct(getById(id));
    }

    public void removeProduct(Product p) {
        store.remove(p.getProductKey());
        iDStore.remove(p.getId());
        System.out.println("Product " + p.getClass().getSimpleName() + " " + p.getName()
                + " " + p.getAdditionalInfo() + " price: " + p.getPrice() + " id# "
                + p.getId() + " removed");
    }

    public void removeProductType(Product p) {
        Set<String> keys = store.keySet();

        for (String s : keys) {
            String typeName = s.substring(0, s.indexOf(' '));
            if (p.getClass().getSimpleName().equals(typeName)) {
                iDStore.remove(store.get(s).getId());
                store.remove(s);
            }
        }

        System.out.println("Product type " + p.getClass().getSimpleName() + " removed");
    }


    public void addProduct(Product p) {
        p.updateProductKey();
        String carKey = p.getProductKey();

        if (!store.containsKey(carKey)) {
            store.put(carKey, p);
        } else {
            p = store.get(carKey);
            p.setQuantity(p.getQuantity() + 1);
        }

        generateId(p);

        iDStore.put(p.getId(), p);

        System.out.println("Product " + p.getBrand() + " " + p.getName() + " "
                + p.getAdditionalInfo() + " added to store");


    }


    private void generateId(Product p) {

        int id = 0;
        int typeCounter = 0;
        Set<String> keys = store.keySet();

        switch (p.getBrand()) {
            case "BMW" : id = 1000;
                break;
            case "Honda": id = 2000;
                break;
            case "Mercedes": id = 3000;
                break;
            case "Opel": id = 4000;
                break;
            case "Porsche": id = 5000;
                break;
        }

        for (String s : keys) {
            s = s.substring(0, s.indexOf(' '));
            if (p.getClass().getSimpleName().equals(s)) {
                typeCounter++;
            }
        }
        p.setId(id + typeCounter);

        while (isSameIdPresent(p)) {
            p.setId(p.getId() + 1);
        }

    }

    public boolean isSameIdPresent(Product p) {
        Set<Integer> iDKeys = iDStore.keySet();
        for (int i : iDKeys) {
            if (i == p.getId()) {
                return true;
            }
        }
        return false;
    }

    public int getSortIndex(Product p) {
        return (p.getId() - p.getId() % 1000) / 1000;
    }

    public ArrayList getTransactions() {
        return transactions;
    }

    @Override
    public ArrayList<Product> getFullDataBase() {

        ArrayList <Product> products = new ArrayList<>();
        Set <String> keys = store.keySet();

        for (String s: keys) {

            Product p = store.get(s);
            products.add(p);
        }

        return products;
    }

    @Override
    public void closeConnection() {

    }

    public Product getById(int id) {
        if (iDStore.containsKey(id)) {
            return iDStore.get(id);
        } else {
            System.out.println("Car with ID# " + id + " not found");
            return null;
        }
    }

    public Set <String> productBrands() {

        Set productBrands = new TreeSet<String>();

        Set <String> storeKeys = store.keySet();

        Product p;

        for (String s: storeKeys) {
            p = store.get(s);
            productBrands.add(p.getBrand());
        }
        return productBrands;
    }

    public List <String> getProductsByBrand(String brand) {

        List <String> productsByBrand = new ArrayList<>();

        Set storeKeys = store.keySet();

        Product p;

        for (Object key: storeKeys) {

            p = store.get(key);

            if (p.getBrand().equals(brand)) {

                String carInMenu = p.getName() + ", Color: " + p.getColor() + p.getAdditionalInfo() + ", "
                        + p.getPrice() + "$ ID# " + p.getId();
                if (p.getQuantity() > 1) {
                    carInMenu += " (" + p.getQuantity() + ") ";
                }

                productsByBrand.add(carInMenu);
            }

            Collections.sort(productsByBrand);
        }

        return productsByBrand;
    }

    @Override
    public Transaction getLastTransaction() {
        return transactions.get(transactions.size()-1);
    }

    @Override
    public ArrayList<Customer> getAllCustomers() {
        return null;
    }
}
