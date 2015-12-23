package model.ui.listeners;

import model.ui.CarShopUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Иван on 29.06.2015.
 */
public class ProductSelectActionListener implements ActionListener {

    private CarShopUI parent;

    public ProductSelectActionListener() {
    }

    public ProductSelectActionListener(CarShopUI parent) {
        this.parent = parent;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JRadioButton clickedButton = (JRadioButton) e.getSource();
        String clickedButtonLabel = clickedButton.getText();
        int typeIndex = parent.getTypeIndex(clickedButtonLabel);

        if (parent.productLists[typeIndex].size() > 0) {
            parent.setJList(parent.productLists[typeIndex]);
        } else {
            parent.setJList(new String[]{"product is not available"});
        }
    }
}
