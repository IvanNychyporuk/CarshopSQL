package model.ui.listeners;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Ivan on 19.11.2015.
 */
public class PriceInputListener extends KeyAdapter {

    @Override
    public void keyTyped(KeyEvent e) {

        String priceChars = "0123456789.";

        char typedChar = e.getKeyChar();

        if (priceChars.indexOf(typedChar) >= 0) {
            super.keyTyped(e);
        } else {
            e.consume();
        }

    }

}
