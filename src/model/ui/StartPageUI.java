package model.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * Created by Ivan on 28.11.2015.
 */
public class StartPageUI extends JPanel {

    public JFrame frame;

    private JPanel radiobuttons;
    private JPanel loginPanel;
    private ButtonGroup group;
    private JRadioButton derby;
    private JRadioButton mySQL;

    private JButton seller;
    private JButton customer;
    private JButton enter;

    private JLabel selectDb;
    private JLabel login;
    private JLabel password;

    private JTextField loginField;
    private JPasswordField passwordField;

    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem buyProduct;
    private JMenuItem addProduct;
    private JMenuItem editProduct;
    private JMenuItem report;
    private JMenuItem fullDataBase;
    private JMenuItem customers;
    private JMenuItem exit;


    public StartPageUI() {

        frame = new JFrame("Car shop");
        frame.setMinimumSize(new Dimension(700, 480));
        frame.setLocation(425, 225);
        frame.setLayout(new GridBagLayout());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        selectDb = new JLabel("Select database");
        group = new ButtonGroup();
        derby = new JRadioButton("Derby db");
        mySQL = new JRadioButton("mySQL");

        group.add(mySQL);
        group.add(derby);

        radiobuttons = new JPanel();
        radiobuttons.add(mySQL);
        radiobuttons.add(derby);
        mySQL.setSelected(true);
        radiobuttons.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        login = new JLabel("login");
        password = new JLabel("password");
        loginField = new JTextField(15);
        loginField.setText("root");
        passwordField = new JPasswordField(15);
        enter = new JButton("Enter");

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.EAST;
        c.insets = new Insets(2, 2, 2, 2);
        c.gridx = 0;
        c.gridy = 0;
        loginPanel.add(login, c);
        c.gridy = 1;
        loginPanel.add(password, c);
        c.gridx = 1;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        loginPanel.add(loginField, c);
        c.gridy = 1;
        loginPanel.add(passwordField, c);
        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 2;
        loginPanel.add(enter, c);


        customer = new JButton("Customer");
        seller = new JButton("Seller");
        seller.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableLoginPanel();
            }
        });

        c = new GridBagConstraints();
        c.anchor = GridBagConstraints.NORTH;
        c.insets = new Insets(2, 2, 2, 2);
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 0;
        frame.add(selectDb, c);
        c.gridy = 1;
        frame.add(radiobuttons, c);
        c.insets = new Insets(20, 20, 20, 20);
        c.gridwidth = 1;
        c.gridy = 2;
        frame.add(customer, c);
        c.gridx = 1;
        frame.add(seller, c);
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(10, 10, 10, 10);

        disableLoginPanel();
        frame.add(loginPanel, c);
        frame.setVisible(true);
    }

    private void enableLoginPanel() {
        seller.setEnabled(false);
        loginField.setEnabled(true);
        passwordField.setEnabled(true);
        enter.setEnabled(true);
    }

    private void disableLoginPanel() {
        loginField.setEnabled(false);
        passwordField.setEnabled(false);
        enter.setEnabled(false);
    }

    public String getDbType() {
        if (derby.isSelected()) {
            return "derby";
        }
        return "mySQL";
    }

    public String getLogin() {
        return loginField.getText();
    }

    public char[] getPassword() {
        return passwordField.getPassword();
    }

    public void addSellerMenu() {

        menuBar = new JMenuBar();
        menu = new JMenu("File");
        buyProduct = new JMenuItem("Sell product", KeyEvent.VK_S);
        addProduct = new JMenuItem("Add product", KeyEvent.VK_A);
        editProduct = new JMenuItem("Edit/remove product", KeyEvent.VK_E);
        report = new JMenuItem("Transactions report", KeyEvent.VK_T);
        fullDataBase = new JMenuItem("Full store", KeyEvent.VK_F);
        customers = new JMenuItem("Customers", KeyEvent.VK_C);
        exit = new JMenuItem("Exit", KeyEvent.VK_X);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(addProduct);
        menu.add(buyProduct);
        menu.add(editProduct);
        menu.add(fullDataBase);
        menu.add(customers);
        menu.add(report);
        menu.add(exit);
        menuBar.add(menu);

        frame.setJMenuBar(menuBar);
    }

    protected void setBuyButtonListener(ActionListener listener) {
        buyProduct.addActionListener(listener);
    }

    protected void setReportButtonListener(ActionListener listener) {
        report.addActionListener(listener);
    }

    protected void setEditRemoveButtonListener(ActionListener listener) {
        editProduct.addActionListener(listener);
    }

    protected void setAddButtonListener(ActionListener listener) {
        addProduct.addActionListener(listener);
    }

    protected void setFullDbButtonListener(ActionListener listener) {
        fullDataBase.addActionListener(listener);
    }

    protected void setCustomersButtonListener(ActionListener listener) {
        customers.addActionListener(listener);
    }

    public void addCustomerButtonListener(ActionListener listener) {
        customer.addActionListener(listener);
    }

    public void addEnterButtonListener(ActionListener listener) {
        enter.addActionListener(listener);
    }
}
