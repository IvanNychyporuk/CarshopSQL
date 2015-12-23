package model.ui.listeners;

import model.ui.BasketPageUI;
import model.ui.CarShopUI;
import model.ui.ControllerUI;
import model.ui.products.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Vector;

/**
 * Created by Ivan on 05.12.2015.
 */
public class RemoveFromBasketActionListener implements ActionListener {

    private BasketPageUI view;
    private CarShopUI shopView;
    private ControllerUI controller;

    public RemoveFromBasketActionListener(BasketPageUI view, CarShopUI shopView, ControllerUI controller) {
        this.view = view;
        this.shopView = shopView;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int i = view.getBasketList().getSelectedIndex();

        if (i == -1) {
            JOptionPane.showMessageDialog(view, "No selected products to remove", "Product is not selected",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product p = controller.getProductsInBasket().get(i);
        updateShopView(p);
        controller.getProductsInBasket().remove(i);
        controller.generateListData();

    }

    private void updateShopView(Product p) {

        int typeIndex = shopView.getTypeIndex(p.getBrand());
        Vector<String> productList = shopView.productLists[typeIndex];
        Vector<String> newList = new Vector<>();
        boolean isProductFound = false;

        for (String s : productList) {

            int id = Integer.valueOf(s.substring(s.indexOf("#") + 2,
                    s.indexOf("#") + 6));

            if (id == p.getId()) {

                int storeQuantity = controller.getQuantityFromProductLine(s);
                int remainQuantity = storeQuantity + p.getQuantity();
                s = p.getName() + ", Color: " + p.getColor() + ", " + p.getAdditionalInfo() + ", "
                        + p.getPrice() + "$ ID# " + p.getId();

                if (remainQuantity > 1) {
                    s += " (" + remainQuantity + ") ";
                }
                isProductFound = true;
            }

            if (!s.equals("")) {
                newList.add(s);
            }
        }

        if (!isProductFound) {
            String storeLine = p.getName() + ", Color: " + p.getColor() + ", " + p.getAdditionalInfo() + ", "
                    + p.getPrice() + "$ ID# " + p.getId();

            if (p.getQuantity() > 1) {
                storeLine += " (" + p.getQuantity() + ") ";
            }
            newList.add(storeLine);
            Collections.sort(newList);
        }

        shopView.updateProductList(newList, p.getBrand());

    }
}
