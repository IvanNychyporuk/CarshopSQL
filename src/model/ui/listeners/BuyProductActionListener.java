package model.ui.listeners;

import model.ui.CarShopUI;
import model.ui.ControllerUI;
import model.ui.products.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ivan on 04.11.2015.
 */
public class BuyProductActionListener implements ActionListener {

    private ControllerUI controller;
    private CarShopUI view;
    private Product product;

    public BuyProductActionListener(CarShopUI view, ControllerUI controller) {

        this.controller = controller;
        this.view = view;
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
        Product product = controller.getProductFromShop(id);

        if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "Please enter your name", "Enter your name", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (product.getQuantity() < requiredQuantity) {
            JOptionPane.showMessageDialog(null, "Sorry, we don't have enough quantity of "
                            + product.getClass().getSimpleName() + " " + product.getName()
                            + ". Try another product, or set the quantity less than "
                            + (product.getQuantity() + 1), "Not enough quantity",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(null, "Congratulations, " + name + "! You purchased " + requiredQuantity +
                " " + product.getClass().getSimpleName() + " " + product.getName() + " "
                + product.getAdditionalInfo() + "!", "Successful!", JOptionPane.INFORMATION_MESSAGE);

//        controller.sellProduct(product, requiredQuantity, name);
        view.updateProductList(controller.productListByBrand(product.getBrand()), product.getBrand());
        view.updateCarShopUI(product);
        controller.updateReportData();
//        reportUI.frame.setVisible(true);
//        reportUI.repaint();

    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
