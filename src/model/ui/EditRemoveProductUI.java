package model.ui;

import model.ui.listeners.PriceInputListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ivan on 07.11.2015.
 */
public class EditRemoveProductUI extends JPanel {

    private JFrame frame;
    private JPanel idPanel;
    private JPanel parametersPanel;

    private JLabel inputId;
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

    private JButton getProduct;
    private JButton submitProduct;
    private JButton removeProduct;
    private JButton clearFields;

    public EditRemoveProductUI() {

        frame = new JFrame("Add Product");
        frame.setMinimumSize(new Dimension(700, 480));
        frame.setLocation(425, 225);
        frame.setLayout(new BorderLayout());
        frame.setContentPane(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        inputId = new JLabel("Input product ID# from store");
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
        getProduct = new JButton("Get product");
        submitProduct = new JButton("Submit changes");
        removeProduct = new JButton("Remove product from store");
        clearFields = new JButton("Clear fields");

        clearFields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearAllFields();
            }
        });

        idPanel = new JPanel();
        idPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(70, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        idPanel.add(inputId, c);
        c.insets = new Insets(2, 2, 2, 2);
        c.anchor = GridBagConstraints.EAST;
        c.gridy = 1;
        c.gridwidth = 1;
        idPanel.add(id, c);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;
        idPanel.add(productId, c);
        c.gridx = 2;
        idPanel.add(getProduct, c);

        parametersPanel = new JPanel();
        parametersPanel.setLayout(new GridBagLayout());

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.insets = new Insets(10, 2, 2, 2);
        c.anchor = GridBagConstraints.EAST;

        parametersPanel.add(brand, c);
        c.gridy = 1;
        c.insets = new Insets(2, 2, 2, 2);
        parametersPanel.add(name, c);
        c.gridy = 2;
        parametersPanel.add(color, c);
        c.gridy = 3;
        parametersPanel.add(additionalInfo, c);
        c.gridy = 4;
        parametersPanel.add(price, c);

        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(10, 2, 2, 2);

        parametersPanel.add(productBrand, c);
        c.gridy = 1;
        c.insets = new Insets(2, 2, 2, 2);
        parametersPanel.add(productName, c);
        c.gridy = 2;
        parametersPanel.add(productColor, c);
        c.gridy = 3;
        parametersPanel.add(productInfo, c);
        c.gridy = 4;
        parametersPanel.add(productPrice, c);

        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(30, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 5;
        parametersPanel.add(submitProduct, c);
        c.gridx = 1;
        parametersPanel.add(removeProduct, c);
        c.gridx = 2;
        parametersPanel.add(clearFields, c);

        lockParametersPanel();

        frame.add("North", idPanel);
        frame.add("Center", parametersPanel);

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

    public void clearAllFields () {
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

    public void setGetProductButtonListener(ActionListener listener) {
        getProduct.addActionListener(listener);
    }

    public void setSubmitProductButtonListener(ActionListener listener) {
        submitProduct.addActionListener(listener);
    }

    public void setRemoveProductButtonListener(ActionListener listener) {
        removeProduct.addActionListener(listener);
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
}
