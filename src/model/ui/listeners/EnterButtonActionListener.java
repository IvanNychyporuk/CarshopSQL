package model.ui.listeners;

import com.mysql.jdbc.exceptions.jdbc4.CommunicationsException;
import model.ui.ControllerUI;
import model.ui.StartPageUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Ivan on 05.12.2015.
 */
public class EnterButtonActionListener implements ActionListener {


    private final String LOGIN = "root";
    private final String PASS = "root";

    private ControllerUI controller;
    private StartPageUI view;

    public EnterButtonActionListener(StartPageUI view, ControllerUI controller) {
        this.controller = controller;
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (checkLogin() && checkPassword()) {
            try {

                controller.startSellerVersion();

            } catch (CommunicationsException e1) {

                JOptionPane.showMessageDialog(view, "MySQL server not found, please try Derby database version",
                        "MySQL server connection error", JOptionPane.ERROR_MESSAGE);

                controller.restartProgram();

            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(view, "Login and password are incorrect",
                    "Login and password error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private boolean checkLogin() {

        if (LOGIN.equals(view.getLogin())) {
            return true;
        }

        return false;
    }

    private boolean checkPassword() {

        char[] password = PASS.toCharArray();

        for (int i = 0; i < password.length; i++) {
            if (password[i] != view.getPassword()[i]) {
                return false;
            }
        }

        return true;
    }

}
