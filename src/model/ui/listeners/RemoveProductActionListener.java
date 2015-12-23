package model.ui.listeners;

import model.ui.ControllerUI;
import model.ui.EditRemoveProductUI;
import model.ui.products.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ivan on 13.11.2015.
 */
public class RemoveProductActionListener implements ActionListener {

    private ControllerUI controller;
    private EditRemoveProductUI view;
    private Product product;

    public RemoveProductActionListener(EditRemoveProductUI view, ControllerUI controller) {
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int productId = Integer.parseInt(view.getProductId());

        product = controller.getProductFromShop(productId);

        int x = JOptionPane.showConfirmDialog(view,"Product " + product.getBrand() +
                " " + product.getName() + " ID# " + product.getId() + " will be removed from store and from transactions history. " +
                "Please, confirm removement.", "Confirm removement", JOptionPane.YES_NO_OPTION);

        if (x == 0) {
            controller.removeProduct(product);
            controller.updateProductList(product);

            JOptionPane.showMessageDialog(view, "Product " + product.getBrand() +
                    " " + product.getName() + " ID# " + product.getId() + " removed from store. ", "Product removed", JOptionPane.INFORMATION_MESSAGE );
        }

        view.clearAllFields();
    }
}
