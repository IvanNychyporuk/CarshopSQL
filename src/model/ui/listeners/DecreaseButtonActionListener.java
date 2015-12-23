package model.ui.listeners;

import model.ui.BasketPageUI;
import model.ui.CarShopUI;
import model.ui.ControllerUI;
import model.ui.products.Product;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Ivan on 07.12.2015.
 */
public class DecreaseButtonActionListener implements ActionListener {

    private BasketPageUI view;
    private CarShopUI shopView;
    private ControllerUI controller;

    public DecreaseButtonActionListener(BasketPageUI view, CarShopUI shopView, ControllerUI controller) {
        this.view = view;
        this.shopView = shopView;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int i = view.getBasketList().getSelectedIndex();

        if (i == -1) {
            return;
        }

        Product p = controller.getProductsInBasket().get(i);
        controller.getProductsInBasket().get(i).setQuantity(p.getQuantity() - 1);

        updateShopView(p);
        controller.generateListData();
        view.setLastSelection();

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
                int remainQuantity = storeQuantity + 1;
                s = p.getName() + ", Color: " + p.getColor() + ", " + p.getAdditionalInfo() + ", "
                        + p.getPrice() + "$ ID# " + p.getId();
                isProductFound = true;

                if (remainQuantity > 1) {
                    s += " (" + remainQuantity + ") ";
                }

            }

            newList.add(s);

        }

        if (!isProductFound) {
            String s = p.getName() + ", Color: " + p.getColor() + ", " + p.getAdditionalInfo() + ", "
                    + p.getPrice() + "$ ID# " + p.getId();
            newList.add(s);
            controller.getProductsInBasket().remove(p);
        }
        shopView.updateProductList(newList, p.getBrand());
    }

}
