package model.ui.listeners;

import model.ui.BasketPageUI;
import model.ui.ControllerUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ivan on 08.12.2015.
 */
public class PurchaseButtonActionListener implements ActionListener {

    private BasketPageUI view;
    private ControllerUI controller;

    public PurchaseButtonActionListener(BasketPageUI view, ControllerUI controller) {
        this.view = view;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        controller.sellProducts(view.getCustomerName());

    }
}
