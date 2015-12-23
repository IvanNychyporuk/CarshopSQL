package model.ui;

import model.ui.products.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Ivan on 28.11.2015.
 */
public class CarShopUI extends JPanel {

    protected JFrame frame;
    protected JPanel p;
    private JLabel yourName;
    private JTextField customerName;
    private JLabel yourSecondName;
    private JTextField customerSecondName;
    private JLabel selectProduct;
    private JPanel products;
    private JLabel quantity;
    private JLabel itemsInBasket;
    private JTextField quantityOfItems;
    private ButtonGroup group;
    private int numberOfTypes;

    public Vector<String>[] productLists;
    private Object[] productBrands;

    private JList productModelsList;
    private JPanel p2;

    private JButton addToBasket;
    private JButton toBasket;


    public CarShopUI() {

        frame = new JFrame("Car shop");
        frame.setMinimumSize(new Dimension(700, 480));
        frame.setLocation(425, 225);
        frame.setLayout(new GridLayout());

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        p = new JPanel();
        yourName = new JLabel("Customer name: ");
        customerName = new JTextField(15);
        yourSecondName = new JLabel("Customer second name: ");
        customerSecondName = new JTextField(15);
        selectProduct = new JLabel("Select brand: ");

        products = new JPanel();
        products.setLayout(new GridLayout(5, 1));
        group = new ButtonGroup();

        productBrands = new Object[1];

        products.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        quantity = new JLabel("Quantity: ");
        quantityOfItems = new JFormattedTextField(new Integer(1));
        quantityOfItems.setColumns(3);

        addToBasket = new JButton("Add to basket");
        toBasket = new JButton("Go to basket");
        itemsInBasket = new JLabel("Items in basket: 0");


        p.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 2, 2);
        c.fill = GridBagConstraints.CENTER;
        c.anchor = GridBagConstraints.EAST;
        c.gridx = 0;
        c.gridy = 0;
        p.add(yourName, c);
        c.gridy = 1;
        p.add(yourSecondName, c);
        c.gridy = 2;
        p.add(selectProduct, c);
        c.gridy = 3;
        p.add(quantity, c);

        c.anchor = GridBagConstraints.WEST;
        c.gridx = 1;
        c.gridy = 0;
        p.add(customerName, c);
        c.gridy = 1;
        p.add(customerSecondName, c);
        c.gridy = 2;
        p.add(products, c);
        c.gridy = 3;
        p.add(quantityOfItems, c);
        c.gridy = 4;
        c.insets = new Insets(10, 10, 10, 10);
        c.anchor = GridBagConstraints.CENTER;
        p.add(addToBasket, c);
        c.gridx = 2;
        p.add(itemsInBasket,c);
        c.gridx = 1;
        c.gridy = 5;
        p.add(toBasket, c);

        productModelsList = new JList();
        productModelsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productModelsList.setLayoutOrientation(JList.VERTICAL);
        productModelsList.setVisibleRowCount(6);

        JScrollPane listScroller = new JScrollPane(productModelsList);
        listScroller.setPreferredSize(new Dimension(310, 120));

        p2 = new JPanel();
        p2.add(listScroller);

        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(0, 0, 0, 0);
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 2;
        p.add(p2, c);

        frame.add("North", p);
        frame.pack();
    }

    public void setItemsInBasket(int quantity) {

        itemsInBasket.setText("Items in basket: " + quantity);

    }

    protected void createArraysForJList(Vector[] productLists) {
        this.productLists = productLists;
    }

    public void setJList(Object[] list) {
        productModelsList.setListData(list);
    }

    public void setJList(Vector list) {
        productModelsList.setListData(list);
    }

    public void setAddToBasketActionListener(ActionListener listener) {
        addToBasket.addActionListener(listener);
    }

    public void setToBasketActionListener(ActionListener listener) {
        toBasket.addActionListener(listener);
    }

    public JList getProductModelsList() {
        return productModelsList;
    }

    public String getCustomerName() {
        return customerName.getText();
    }

    public String getCustomerSecondName() {
        return customerSecondName.getText();
    }

    public String getQuantityOfItems() {
        return quantityOfItems.getText();
    }

    public int getNumberOfTypes() {
        return numberOfTypes;
    }

    public Object[] getProductBrands() {
        return productBrands;
    }

    protected void generateRadiobuttons(Object[] productBrands) {

        this.productBrands = productBrands;

        numberOfTypes = productBrands.length;

        String buttonName = productBrands[0].toString();
        JRadioButton newButton = new JRadioButton(buttonName);
        group.add(newButton);
        products.add(newButton);
        newButton.setSelected(true);

        for (int i = 1; i < numberOfTypes; i++) {

            buttonName = productBrands[i].toString();
            newButton = new JRadioButton(buttonName);
            group.add(newButton);
            products.add(newButton);

        }

    }

    protected void setListenerToRadioButtons(ActionListener listener) {

        Enumeration<AbstractButton> radioButtons = group.getElements();

        while (radioButtons.hasMoreElements()) {
            radioButtons.nextElement().addActionListener(listener);
        }

    }

    public boolean checkNameField() {

        String name = getCustomerName() + " " + getCustomerSecondName();

        if (name.equals(" ")) {
            return false;
        }

        return true;
    }

    public void updateProductList(Vector<String> productList, String brand) {
        int typeIndex = getTypeIndex(brand);
        productLists[typeIndex] = productList;
        setJList(productLists[typeIndex]);
    }

    public int getTypeIndex(String brand) {

        int index = 0;

        for (int i = 0; i < productBrands.length; i++) {
            if (productBrands[i].toString().equals(brand)) {
                index = i;
            }
        }

        return index;
    }

    public JPanel getMainPanel() {
        return p;
    }

    public void updateCarShopUI(Product product) {

        int typeIndex = getTypeIndex(product.getBrand());

        if (productLists[typeIndex].size() > 0) {
            setJList(productLists[typeIndex]);
            Enumeration<AbstractButton> radioButtons = group.getElements();

            while (radioButtons.hasMoreElements()) {
                AbstractButton button = radioButtons.nextElement();
                if (button.getText().equals(product.getBrand())) {
                    button.setSelected(true);
                }
            }
        } else {
            setJList(new String[]{"product is not available"});
        }
        p.repaint();
    }


}
