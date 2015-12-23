package model.ui;

import model.ui.listeners.PriceInputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ivan on 03.11.2015.
 */
public class AddProductUI extends JPanel {

    protected JFrame frame;

    private JLabel id;
    private JTextField productId;
    private JLabel brand;
    private JTextField productBrand;
    private JLabel name;
    private JTextField productName;
    private JLabel color;
    private JTextField productColor;
    private JLabel additionalInfo;
    private JTextField productInfo;
    private JLabel price;
    private JTextField productPrice;
    private JLabel quantity;
    private JTextField productQuantity;

    private JButton addProduct;
    private JButton getProduct;
    private JButton clearFields;
    private JButton newProduct;
    private JButton oldProduct;

    private boolean isNewProduct = false;

    public AddProductUI() {

        frame = new JFrame("Add new Product");
        frame.setMinimumSize(new Dimension(700, 480));
        frame.setLocation(425, 225);
        frame.setLayout(new GridLayout());
        frame.setContentPane(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        id = new JLabel("ID#: ");
        productId = new JTextField(6);
        brand = new JLabel("product brand: ");
        productBrand = new JTextField(30);
        name = new JLabel("product name: ");
        productName = new JTextField(30);
        color = new JLabel("color: ");
        productColor = new JTextField(30);
        additionalInfo = new JLabel("additional info: ");
        productInfo = new JTextField(30);
        price = new JLabel("price: ");

        productPrice = new JTextField(15);
        productPrice.addKeyListener(new PriceInputListener());

        quantity = new JLabel("quantity: ");

        productQuantity = new JFormattedTextField(new Integer(1));
        productQuantity.setColumns(10);

        addProduct = new JButton("Add product to store");
        getProduct = new JButton("Get product");
        clearFields = new JButton("Clear fields");
        newProduct = new JButton("New product");
        oldProduct = new JButton("Old product");

        newProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lockProductIdField();
                productId.setText("");
                getProduct.setEnabled(false);
                unlockParametersPanel();
                isNewProduct = true;
            }
        });

        oldProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unlockProductIdField();
                lockParametersPanel();
                getProduct.setEnabled(true);
                isNewProduct = false;
            }
        });

        clearFields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllFields();
            }
        });

        JPanel oldNewPanel = new JPanel();
        oldNewPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(20, 120, 2, 20);
        c.gridx = 1;
        c.gridy = 0;
        oldNewPanel.add(oldProduct, c);
        c.insets = new Insets(20, 2, 2, 120);
        c.gridx = 2;
        oldNewPanel.add(newProduct, c);

        JPanel idPanel = new JPanel();
        idPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.insets = new Insets(40, 20, 2, 2);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        c.gridy = 0;
        idPanel.add(id, c);
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(40, 2, 2, 2);
        c.gridx = 1;
        idPanel.add(productId, c);
        c.insets = new Insets(40, 2, 2, 100);
        c.gridx = 2;
        idPanel.add(getProduct, c);


        JPanel fieldsPanel = new JPanel();
        fieldsPanel.setLayout(new GridBagLayout());
        c = new GridBagConstraints();

        c.fill = GridBagConstraints.CENTER;
        c.insets = new Insets(2, 2, 2, 2);
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        c.gridy = 0;
        fieldsPanel.add(brand, c);
        c.gridy = 1;
        fieldsPanel.add(name, c);
        c.gridy = 2;
        fieldsPanel.add(color, c);
        c.gridy = 3;
        fieldsPanel.add(additionalInfo, c);
        c.gridy = 4;
        fieldsPanel.add(price, c);
        c.gridy = 5;
        fieldsPanel.add(quantity, c);


        c.anchor = GridBagConstraints.WEST;
        c.gridy = 0;
        c.gridx = 1;
        fieldsPanel.add(productBrand, c);
        c.gridy = 1;
        fieldsPanel.add(productName, c);
        c.gridy = 2;
        fieldsPanel.add(productColor, c);
        c.gridy = 3;
        fieldsPanel.add(productInfo, c);
        c.gridy = 4;
        fieldsPanel.add(productPrice, c);
        c.gridy = 5;
        fieldsPanel.add(productQuantity, c);

        JPanel clearAddPanel = new JPanel();
        clearAddPanel.setLayout(new GridBagLayout());
        c.insets = new Insets(80,50,10,50);
        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        clearAddPanel.add(addProduct, c);
        c.anchor = GridBagConstraints.WEST;
        c.gridwidth = 1;
        c.gridx = 3;
        clearAddPanel.add(clearFields, c);

        lockParametersPanel();

        frame.add("North", oldNewPanel);
        frame.add("Center", idPanel);
        frame.add("Center", fieldsPanel);
        frame.add("South", clearAddPanel);
//        frame.pack();
    }

    public void lockParametersPanel() {

        productBrand.setEditable(false);
        productName.setEditable(false);
        productColor.setEditable(false);
        productInfo.setEditable(false);
        productPrice.setEditable(false);

    }

    public void unlockParametersPanel() {

        productBrand.setEditable(true);
        productName.setEditable(true);
        productColor.setEditable(true);
        productInfo.setEditable(true);
        productPrice.setEditable(true);
    }

    public void clearAllFields() {
        productId.setText("");
        productBrand.setText("");
        productName.setText("");
        productColor.setText("");
        productInfo.setText("");
        productPrice.setText("");
        lockParametersPanel();
        unlockProductIdField();
    }

    public void lockProductIdField() {
        productId.setEditable(false);
    }

    public void unlockProductIdField() {
        productId.setEditable(true);
    }

    protected void setAddButtonListener(ActionListener listener) {
        addProduct.addActionListener(listener);
    }

    protected void setGetProductButtonListener(ActionListener listener) {
        getProduct.addActionListener(listener);
    }

    public String getProductId() {
        return productId.getText();
    }

    public String getProductBrand() {
        return productBrand.getText();
    }

    public void setProductBrand(String productBrand) {
        this.productBrand.setText(productBrand);
    }

    public String getProductName() {
        return productName.getText();
    }

    public void setProductName(String productName) {
        this.productName.setText(productName);
    }

    public String getProductColor() {
        return productColor.getText();
    }

    public void setProductColor(String productColor) {
        this.productColor.setText(productColor);
    }

    public String getProductInfo() {
        return productInfo.getText();
    }

    public void setProductInfo(String productInfo) {
        this.productInfo.setText(productInfo);
    }

    public Double getProductPrice() {

        String price = productPrice.getText();

        int dotCount = 0;

        for (int i = 0; i < price.length(); i++) {
            if (price.charAt(i) == '.') {
                dotCount++;
            }
        }

        if (dotCount > 1) {
            return -1.0;
        }

        return Double.parseDouble(price);
    }

    public void setProductPrice(String productPrice) {
        this.productPrice.setText(productPrice);
    }

    public int getProductQuantity() {
        return Integer.parseInt(productQuantity.getText());
    }

    public void setProductQuantity(int quantity) {
        productQuantity.setText(String.valueOf(quantity));
    }

    public boolean isNewProduct() {
        return isNewProduct;
    }
}
