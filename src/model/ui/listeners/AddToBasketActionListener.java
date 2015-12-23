package model.ui.listeners;

import model.ui.CarShopUI;
import model.ui.ControllerUI;
import model.ui.products.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Ivan on 29.11.2015.
 */
public class AddToBasketActionListener implements ActionListener {

    private CarShopUI view;
    private ControllerUI controller;

    public AddToBasketActionListener(CarShopUI view, ControllerUI controller) {
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String name = view.getCustomerName() + " " + view.getCustomerSecondName();
        int requiredQuantity = 1;
        if (!view.getQuantityOfItems().equals("")) {           // checking correct value of "quantity" field
            requiredQuantity = Integer.parseInt(view.getQuantityOfItems());
        }

        if (view.getProductModelsList().getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(null, "Please select available product", "Product is not selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String selectedCar = view.getProductModelsList().getSelectedValue().toString();

        if (selectedCar.equals("product is not available")) {
            JOptionPane.showMessageDialog(null, "Please select available product", "Product is not selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = Integer.valueOf(selectedCar.substring(selectedCar.indexOf("#") + 2,
                selectedCar.indexOf("#") + 6));

        Product p = controller.getProductFromShop(id);

        if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter your name", "Enter your name", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (p.getQuantity() < requiredQuantity) {
            JOptionPane.showMessageDialog(null, "Sorry, we don't have enough quantity of "
                            + p.getClass().getSimpleName() + " " + p.getName()
                            + ". Try another product, or set the quantity less than "
                            + (p.getQuantity() + 1), "Not enough quantity",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        p.setQuantity(requiredQuantity);
        controller.addProductToBasket(p);
        updateView(p);

    }

    private void updateView(Product p) {

        int typeIndex = view.getTypeIndex(p.getBrand());
        Vector<String> productList = view.productLists[typeIndex];
        Vector<String> newList = new Vector<>();

        for (String s : productList) {

            int id = Integer.valueOf(s.substring(s.indexOf("#") + 2,
                    s.indexOf("#") + 6));

            if (id == p.getId()) {

                int storeQuantity = getQuantityFromProductLine(s);
                int remainQuantity = storeQuantity - p.getQuantity();
                s = p.getName() + ", Color: " + p.getColor() + ", " + p.getAdditionalInfo() + ", "
                        + p.getPrice() + "$ ID# " + p.getId();

                if (remainQuantity > 1) {
                    s += " (" + remainQuantity + ") ";
                }
                if (remainQuantity < 1) {
                    s = "";
                }
            }

            if (!s.equals("")) {
                newList.add(s);
            }
        }

        view.updateProductList(newList, p.getBrand());

    }

    private int getQuantityFromProductLine(String productLine) {

        if (productLine.indexOf('(') < 0) {
            return 1;
        } else if (productLine.equals("product is not available")) {
            return 0;
        }

        productLine = productLine.substring(productLine.indexOf('(') + 1, productLine.indexOf(')'));

        return Integer.valueOf(productLine);
    }
}
