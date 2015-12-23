package model.ui;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import model.sql.CarShopSQL;
import model.sql.Shop;
import model.sql.ShopInitializer;
import model.ui.listeners.*;
import model.ui.products.Product;
import model.ui.products.Transaction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by Ivan on 04.11.2015.
 */
public class ControllerUI {

    private StartPageUI startPage;
    private CarShopUI carshopPage;
    private ReportUI reportPage;
    private FullDataBaseUI fullDataBasePage;
    private AddProductUI addProductUI;
    private EditRemoveProductUI editRemoveUI;
    private BasketPageUI basketPage;
    private CustomersUI customersPage;
    private Shop shop;
    private String dbType;
    private String version;
    private ArrayList<Product> productsInBasket;
    private ShopInitializer initializer;

    public ControllerUI() {

        createStartPage();

    }

    private void createStartPage() {

        startPage = new StartPageUI();
        startPage.addCustomerButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startCustomerVersion();
                } catch (CommunicationsException e1) {

                    JOptionPane.showMessageDialog(startPage, "MySQL server not found, please try Derby database version",
                            "MySQL server connection error", JOptionPane.ERROR_MESSAGE);

                    restartProgram();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        startPage.addEnterButtonListener(new EnterButtonActionListener(startPage, this));

        productsInBasket = new ArrayList<>();

    }

    public void startCustomerVersion() throws Exception {

        dbType = startPage.getDbType();
        version = "customer";

        System.out.println("Db type: " + dbType);
        initializer = new ShopInitializer(dbType);
        shop = new CarShopSQL(dbType);
        checkIfStoreEmpty();
        enableClosingConnectionAfterExit();
        createCarChopPage();

        switchPanelToShopPage();

    }

    public void startSellerVersion() throws Exception {

        dbType = startPage.getDbType();
        version = "seller";

        initializer = new ShopInitializer(dbType);
        enableClosingConnectionAfterExit();

        shop = new CarShopSQL(dbType);
        checkIfStoreEmpty();
        createCarChopPage();

        System.out.println("Db type: " + dbType);
        addProductUI = new AddProductUI();
        addProductUI.setGetProductButtonListener(new GetProductActionListener(addProductUI, this));
        addProductUI.setAddButtonListener(new AddProductActionListener(addProductUI, this));

        editRemoveUI = new EditRemoveProductUI();
        editRemoveUI.setGetProductButtonListener(new GetProductActionListener(editRemoveUI, this));
        editRemoveUI.setSubmitProductButtonListener(new SubmitProductActionListener(editRemoveUI, this));
        editRemoveUI.setRemoveProductButtonListener(new RemoveProductActionListener(editRemoveUI, this));

        reportPage = new ReportUI();
        createReportData();

        fullDataBasePage = new FullDataBaseUI();
        fullDataBasePage.fillTheTable(shop.getFullDataBase());

        customersPage = new CustomersUI();
        customersPage.fillTheTable(shop.getAllCustomers(), shop.getTransactions());

        startPage.addSellerMenu();
        startPage.setBuyButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanelToShopPage();
            }
        });
        startPage.setAddButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanelToAddProductPage();
            }
        });
        startPage.setReportButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanelToReportPage();
            }
        });
        startPage.setEditRemoveButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanelToEditRemove();
            }
        });
        startPage.setFullDbButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanelToFullDbPage();
            }
        });
        startPage.setCustomersButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanelToCustomersPage();
            }
        });
        switchPanelToReportPage();
    }

    public void restartProgram() {

        startPage.frame.setVisible(false);

//        startPage.frame.setContentPane(new StartPageUI());
        createStartPage();

    }

    private void enableClosingConnectionAfterExit() {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                shop.closeConnection();
                System.out.println("Connection with db is closed");
            }
        }));
    }

    private void createCarChopPage() {

        carshopPage = new CarShopUI();
        carshopPage.generateRadiobuttons(shop.productBrands().toArray());
        carshopPage.createArraysForJList(productsForJList());
        carshopPage.setJList(carshopPage.productLists[0]);
        carshopPage.setListenerToRadioButtons(new ProductSelectActionListener(carshopPage));
        carshopPage.setAddToBasketActionListener(new AddToBasketActionListener(carshopPage, this));
        carshopPage.setToBasketActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanelToBasketPage();
            }
        });

        basketPage = new BasketPageUI();
        basketPage.setRemoveProductActionListener(new RemoveFromBasketActionListener(basketPage, carshopPage, this));
        basketPage.setIncreaseActionListener(new IncreaseButtonActionListener(basketPage, carshopPage, this));
        basketPage.setDecreaseActionListener(new DecreaseButtonActionListener(basketPage, carshopPage, this));
        basketPage.setPurchaseActionListener(new PurchaseButtonActionListener(basketPage, this));
        basketPage.setToShopActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchPanelToShopPage();
            }
        });

    }

    public Product getProductFromShop(int id) {
        return shop.getById(id);
    }

    public void updateProductInStore(Product p, String fieldName, Object value) {
        shop.editProduct(p, fieldName, value);
    }

    public void updateProductOnFullDbPage(Product p, int quantity) {
        fullDataBasePage.updateTable(p, quantity);
    }

    public void sellProducts(String customerName) {

        Transaction t = shop.sellProducts(productsInBasket, customerName);

        if (version.equals("customer")) {
            JOptionPane.showMessageDialog(basketPage, "Congratulations, " + customerName + "! Your transaction completed successful!",
                    "Successful!", JOptionPane.INFORMATION_MESSAGE);
            switchPanelToShopPage();

        } else {
            JOptionPane.showMessageDialog(basketPage, "Transaction with " + customerName + " completed successful",
                    "Successful!", JOptionPane.INFORMATION_MESSAGE);
            updateReportData();
            fullDataBasePage.updateTable(productsInBasket);
            customersPage.updateTable(t);
            switchPanelToReportPage();
        }

        productsInBasket = new ArrayList<>();
        createCarChopPage();


    }

    public List<Product> getProductsInBasket() {
        return productsInBasket;
    }

    public Shop getShop() {
        return shop;
    }

    public void addProductToBasket(Product p) {

        boolean addNew = true;

        for (Product product : productsInBasket) {
            if (p.getId() == product.getId()) {
                product.setQuantity(product.getQuantity() + p.getQuantity());
                addNew = false;
            }
        }
        if (addNew) {
            productsInBasket.add(p);
            carshopPage.setItemsInBasket(productsInBasket.size());
        }

    }

    public void generateListData() {

        Vector list = new Vector();
        List<Integer> zeroIndexes = new ArrayList<>();

        int index = 1;

        System.out.println("Products in basket left: " + productsInBasket.size());

        for (int i = 0; i < productsInBasket.size(); i++) {

            Product p = productsInBasket.get(i);

            if (p.getQuantity() > 0) {

                String basketLine = index++ + ". " + p.getId() + " " + p.getBrand() + " " + p.getName() +
                        " " + p.getColor() + " " + p.getAdditionalInfo() + " " + p.getPrice() + "$ ";

                if (p.getQuantity() > 1) {
                    basketLine += " (" + p.getQuantity() + ") ";
                }

                if (p.getQuantity() >= 1) {
                    list.add(basketLine);
                    System.out.println(basketLine);
                }

            } else {
                zeroIndexes.add(i);
            }
        }

        cleanUpBasket(zeroIndexes);
        calculateTotalPrice();
        basketPage.setBasketListData(list);

    }

    private void cleanUpBasket(List<Integer> zeroIndexes) {

        for (int i : zeroIndexes) {
            productsInBasket.remove(i);
        }

    }

    private void calculateTotalPrice() {

        double totalPrice = 0;

        for (Product p : productsInBasket) {
            totalPrice += p.getPrice() * p.getQuantity();
        }

        basketPage.setTotalPrice(totalPrice);
    }

    public int getQuantityFromProductLine(String productLine) {

        if (productLine.indexOf('(') < 0) {
            return 1;
        } else if (productLine.equals("product is not available")) {
            return 0;
        }

        productLine = productLine.substring(productLine.indexOf('(') + 1, productLine.indexOf(')'));

        return Integer.valueOf(productLine);
    }


    public void addProductToStore(Product p) {
        shop.addProduct(p);
        fullDataBasePage.fillTheTable(shop.getFullDataBase());
        createCarChopPage();
    }

    public void removeProduct(Product p) {
        shop.removeProduct(p);
        fullDataBasePage.removeProductFromTable(p);
    }

    public void createReportData() {
        reportPage.createReportData(shop.getTransactions());
    }

    public void updateReportData() {
        reportPage.addTransactionToReport(shop.getLastTransaction());
    }

    private Vector[] productsForJList() {

        Vector<String>[] productLists = new Vector[carshopPage.getNumberOfTypes()];

        for (int i = 0; i < carshopPage.getNumberOfTypes(); i++) {

            productLists[i] = new Vector<String>();
            String brand = carshopPage.getProductBrands()[i].toString();
            List<String> productsByBrand = shop.getProductsByBrand(brand);

            for (String s : productsByBrand) {
                productLists[i].add(s);
            }

        }
        return productLists;
    }

    public Vector<String> productListByBrand(String brand) {

        Vector<String> products = new Vector<>();

        List<String> productsByBrand = shop.getProductsByBrand(brand);

        for (String s : productsByBrand) {
            products.add(s);
        }
        return products;
    }

    public void repaintMainPage() {
        startPage.frame.setVisible(true);
    }

    private void switchPanelToShopPage() {
        carshopPage.setItemsInBasket(productsInBasket.size());
        startPage.frame.setContentPane(carshopPage);
        startPage.frame.setLayout(new GridLayout());
        startPage.frame.add("North", carshopPage.getMainPanel());
        startPage.frame.pack();

    }

    private void switchPanelToBasketPage() {

        if (carshopPage.checkNameField()) {
            basketPage.setCustomerName(carshopPage.getCustomerName() + " " + carshopPage.getCustomerSecondName());
            generateListData();
            startPage.frame.setContentPane(basketPage);
            startPage.frame.setLayout(new GridLayout());
            startPage.frame.add("North", basketPage.getMainPanel());
            startPage.frame.pack();

        } else if (version.equals("customer")) {
            JOptionPane.showMessageDialog(carshopPage, "Please enter your name", "Enter your name", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(carshopPage, "Please enter customer`s name", "Enter customer`s name", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void switchPanelToReportPage() {
        startPage.frame.setContentPane(reportPage);
        startPage.frame.setLayout(new GridLayout());
        startPage.frame.add("North", reportPage.getMainPanel());
        startPage.frame.pack();
    }

    private void switchPanelToEditRemove() {
        startPage.frame.setContentPane(editRemoveUI);
        startPage.frame.setVisible(true);
    }

    private void switchPanelToAddProductPage() {
        startPage.frame.setContentPane(addProductUI);
        startPage.frame.setVisible(true);
    }

    private void switchPanelToFullDbPage() {
        startPage.frame.setContentPane(fullDataBasePage);
        startPage.frame.setLayout(new GridLayout());
        startPage.frame.add("North", fullDataBasePage.getMainPanel());
        startPage.frame.pack();
    }

    private void switchPanelToCustomersPage() {
        startPage.frame.setContentPane(customersPage);
        startPage.frame.setLayout(new GridLayout());
        startPage.frame.add("North", customersPage.getMainPanel());
        startPage.frame.pack();
    }

    public void updateProductList(Product product) {
        carshopPage.updateProductList(productListByBrand(product.getBrand()), product.getBrand());
        carshopPage.updateCarShopUI(product);
    }

    private void checkIfStoreEmpty() {

        if (shop.getFullDataBase().size() == 0) {
            System.out.println("Shop is empty. Filling database with test data...");
            SetupForDemo demo = new SetupForDemo(shop);
            demo.fillDataBase();
        }
    }

}
