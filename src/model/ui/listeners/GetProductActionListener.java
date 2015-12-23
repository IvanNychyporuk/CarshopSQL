package model.ui.listeners;

import model.ui.AddProductUI;
import model.ui.ControllerUI;
import model.ui.EditRemoveProductUI;
import model.ui.products.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ivan on 11.11.2015.
 */
public class GetProductActionListener implements ActionListener {

    private ControllerUI controller;
    private EditRemoveProductUI viewEdit;
    private AddProductUI viewAdd;
    private Product product;
    private String buttonUI;

    public GetProductActionListener (EditRemoveProductUI viewEdit, ControllerUI controller) {
        this.viewEdit = viewEdit;
        this.controller = controller;
        buttonUI = "EditRemove";
    }

    public GetProductActionListener(AddProductUI viewAdd, ControllerUI controller) {
        this.viewAdd = viewAdd;
        this.controller = controller;
        buttonUI = "Add";
    }


    @Override
    public void actionPerformed(ActionEvent e) {

        switch (buttonUI) {
            case "EditRemove": performActionForEditFrame();
                break;
            case "Add": performActionForAddFrame();
        }

    }

    private void performActionForAddFrame() {

        viewAdd.lockProductIdField();

        int productId = Integer.parseInt(viewAdd.getProductId());

        product = controller.getProductFromShop(productId);

        if (product.getId() != 0) {
            viewAdd.setProductBrand(product.getBrand());
            viewAdd.setProductName(product.getName());
            viewAdd.setProductColor(product.getColor());
            viewAdd.setProductInfo(product.getAdditionalInfo());
            viewAdd.setProductPrice(String.valueOf(product.getPrice()));
            viewAdd.setProductQuantity(product.getQuantity());
            controller.repaintMainPage();
        } else {
            JOptionPane.showMessageDialog(null, "Product with id#" + productId + " is not found", "Product is not found",
                    JOptionPane.WARNING_MESSAGE);
            viewAdd.unlockProductIdField();
            return;
        }
    }

    private void performActionForEditFrame() {

        viewEdit.lockProductIdField();

        int productId = Integer.parseInt(viewEdit.getProductId());

        product = controller.getProductFromShop(productId);

        if (product.getId() != 0) {
            viewEdit.setProductBrand(product.getBrand());
            viewEdit.setProductName(product.getName());
            viewEdit.setProductColor(product.getColor());
            viewEdit.setProductInfo(product.getAdditionalInfo());
            viewEdit.setProductPrice(String.valueOf(product.getPrice()));
            viewEdit.unlockParametersPanel();
//            viewEdit.setParametersPanelVisible();
            controller.repaintMainPage();
        } else {
            JOptionPane.showMessageDialog(null, "Product with id#" + productId + " is not found", "Product is not found",
                    JOptionPane.WARNING_MESSAGE);
            viewEdit.unlockProductIdField();
            return;
        }
    }
}
