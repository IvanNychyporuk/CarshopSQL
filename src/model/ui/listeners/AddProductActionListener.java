package model.ui.listeners;

import model.ui.AddProductUI;
import model.ui.ControllerUI;
import model.ui.products.Car;
import model.ui.products.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ivan on 19.11.2015.
 */
public class AddProductActionListener implements ActionListener {

    private ControllerUI controller;
    private AddProductUI view;
    private Product product;

    public AddProductActionListener(AddProductUI view, ControllerUI controller) {
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (view.isNewProduct()) {

            addNewProduct();

        } else {

            int productId = Integer.parseInt(view.getProductId());
            product = controller.getProductFromShop(productId);
            addOldProduct(view.getProductQuantity());
        }

    }

    private void addNewProduct() {

        if (checkFields()) {

            product = new Car();
            product.setBrand(view.getProductBrand());
            product.setName(view.getProductName());
            product.setColor(view.getProductColor());
            product.setAdditionalInfo(view.getProductInfo());
            product.setPrice(view.getProductPrice());
            product.setQuantity(view.getProductQuantity());

            controller.addProductToStore(product);

            JOptionPane.showMessageDialog(view, "Product " + product.getBrand() + " " + product.getName() +
                    " color:" + product.getColor() + " added to store", "Product added", JOptionPane.INFORMATION_MESSAGE);


//            controller.updateProductList(product);

        } else {
            JOptionPane.showMessageDialog(view, "Input data is incorrect", "Input data error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void addOldProduct(int quantity) {

        controller.updateProductInStore(product, "quantity", quantity);
        controller.updateProductList(product);
        controller.updateProductOnFullDbPage(product, quantity);

        JOptionPane.showMessageDialog(view, "Product " + product.getBrand() + " " + product.getName() +
                " color:" + product.getColor() + " quantity updated", "Product added", JOptionPane.INFORMATION_MESSAGE);

    }

    private boolean checkFields() {

        if (view.getProductBrand().equals("")) {
            return false;
        }
        if (view.getProductName().equals("")) {
            return false;
        }
        if (view.getProductPrice() <= 0) {
            return false;
        }
        if (view.getProductQuantity() < 0) {
            return false;
        }
        if (view.getProductColor().equals("")) {
            return false;
        }
        if (view.getProductInfo().equals("")) {
            return false;
        }
        return true;
    }
}
