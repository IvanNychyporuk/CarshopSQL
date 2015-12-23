package model.ui;

import model.ui.products.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Ivan on 29.11.2015.
 */
public class BasketPageUI extends JPanel {

    private JFrame frame;
    private JPanel mainPanel;

    private JPanel buttonsPanel;

    private JLabel customer;
    private JLabel basketEntry;
    private JLabel totalPrice;
    private JLabel price;

    private JButton removeProduct;
    private JButton increase;
    private JButton decrease;
    private JButton purchase;
    private JButton toShop;

    private JScrollPane listScroller;
    private JList basketList;

//    private List<Product> productsInBasket;

    public BasketPageUI() {

        frame = new JFrame("Basket");
        frame.setMinimumSize(new Dimension(700, 480));
        frame.setLocation(425, 225);
//        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        customer = new JLabel("Customer:");
        basketEntry = new JLabel("Basket entry");
        totalPrice = new JLabel("total price: ");
        price = new JLabel("000000$$");

        basketList = new JList();
        basketList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        basketList.setLayoutOrientation(JList.VERTICAL);

        listScroller = new JScrollPane(basketList);
        listScroller.setPreferredSize(new Dimension(510, 150));

        removeProduct = new JButton("Remove from basket");
        increase = new JButton("+");
        decrease = new JButton("-");
        purchase = new JButton("Purchase");
        toShop = new JButton("Back to shop..");

        buttonsPanel = new JPanel();
        buttonsPanel.add(removeProduct);
        buttonsPanel.add(toShop);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(2, 2, 2, 2);
        c.anchor = GridBagConstraints.WEST;
        c.gridx = 0;
        c.gridy = 0;
        mainPanel.add(customer, c);
        c.insets = new Insets(20, 2, 2, 2);
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 1;
        mainPanel.add(basketEntry, c);
        c.insets = new Insets(2, 2, 2, 2);
        c.gridy = 2;
        mainPanel.add(listScroller, c);
        c.gridwidth = 1;
        c.gridx = 2;
        mainPanel.add(increase, c);
        c.gridx = 3;
        mainPanel.add(decrease, c);
        c.gridx = 1;
        c.gridy = 3;
        c.anchor = GridBagConstraints.EAST;
        mainPanel.add(totalPrice, c);
        c.gridx = 2;
        c.anchor = GridBagConstraints.WEST;
        mainPanel.add(price, c);
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 4;
        mainPanel.add(buttonsPanel, c);
        c.gridx = 1;
        c.gridwidth = 2;
        mainPanel.add(purchase, c);

        frame.add(mainPanel);

    }

    public void setRemoveProductActionListener(ActionListener listener) {
        removeProduct.addActionListener(listener);
    }

    public void setPurchaseActionListener(ActionListener listener) {
        purchase.addActionListener(listener);
    }

    public void setToShopActionListener(ActionListener listener) {
        toShop.addActionListener(listener);
    }

    public void setIncreaseActionListener(ActionListener listener) {
        increase.addActionListener(listener);
    }

    public void setDecreaseActionListener(ActionListener listener) {
        decrease.addActionListener(listener);
    }

    public void setCustomerName(String customerName) {
        customer.setText("Customer: " + customerName);
    }

    public String getCustomerName () {
        String customerName = customer.getText();
        return customerName.substring(customerName.indexOf(':') + 2);
    }

    public void setTotalPrice(double totalPrice) {
        price.setText(String.valueOf(totalPrice) + "$");
    }

    protected JPanel getMainPanel() {
        return mainPanel;
    }

    public JList getBasketList() {
        return basketList;
    }

    public void setBasketListData (Vector list) {
        basketList.setListData(list);
    }

    public void setLastSelection() {
        basketList.setSelectedIndex(basketList.getLastVisibleIndex());
    }

}
