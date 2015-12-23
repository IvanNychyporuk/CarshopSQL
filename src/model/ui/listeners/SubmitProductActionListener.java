package model.ui.listeners;

import model.ui.ControllerUI;
import model.ui.EditRemoveProductUI;
import model.ui.products.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 13.11.2015.
 */
public class SubmitProductActionListener implements ActionListener {

    private ControllerUI controller;
    private EditRemoveProductUI view;
    private Product product;
    private List<String> changedFields;

    public SubmitProductActionListener(EditRemoveProductUI view, ControllerUI controller) {
        this.view = view;
        this.controller = controller;
        changedFields = new ArrayList<>();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int productId = Integer.parseInt(view.getProductId());

        product = controller.getProductFromShop(productId);



        int x = JOptionPane.showConfirmDialog(view, "Are you shure, you want to update " + product.getBrand() +
                        " " + product.getName() + " ID# " + product.getId() + "? ",
                "Confirm changes", JOptionPane.YES_NO_OPTION);

        if (x == 0) {

            changedFields.clear();

            if (checkChangedFields() && checkIsCorrectPrice()) {
                for (String s : changedFields) {
                    controller.updateProductInStore(product, s, changedValue(s));
                }
                JOptionPane.showMessageDialog(view, "Product " + product.getBrand() + " " + product.getName() + " updated!", "Product data updated",
                        JOptionPane.INFORMATION_MESSAGE);
                controller.updateProductList(product);
            } else if (!checkIsCorrectPrice()) {

                JOptionPane.showMessageDialog(view,"Price format is not correct. Example: 12345.67", "Incorrect price format",JOptionPane.ERROR_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(view, "Product changes are not detected", "No changes found", JOptionPane.INFORMATION_MESSAGE);
            }

        }


    }

    private boolean checkChangedFields() {

        if (!product.getBrand().equals(view.getProductBrand())) {
            changedFields.add("brand");
        }
        if (!product.getName().equals(view.getProductName())) {
            changedFields.add("name");
        }
        if (!product.getColor().equals(view.getProductColor())) {
            changedFields.add("color");
        }
        if (!product.getAdditionalInfo().equals(view.getProductInfo())) {
            changedFields.add("characteristics");
        }
        if (product.getPrice() != view.getProductPrice()) {
            changedFields.add("price");
        }

        if (changedFields.size() > 0) {
            return true;
        }
        return false;
    }

    private boolean checkIsCorrectPrice () {

        if (view.getProductPrice() >= 0.0) {
            return true;
        }

        return false;
    }

    private Object changedValue(String fieldName) {

        Object value = null;

        switch (fieldName) {
            case "brand":
                value = view.getProductBrand();
                break;
            case "name":
                value = view.getProductName();
                break;
            case "color":
                value = view.getProductColor();
                break;
            case "characteristics":
                value = view.getProductInfo();
                break;
            case "price":
                value = view.getProductPrice();
                break;
        }

        return value;
    }
}
