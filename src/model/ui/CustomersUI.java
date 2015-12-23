package model.ui;


import model.ui.products.Customer;
import model.ui.products.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;


/**
 * Created by Ivan on 20.12.2015.
 */
public class CustomersUI extends JPanel {

    protected JFrame frame;
    private JTable table;
    private JLabel customersLabel;
    protected JScrollPane scrollPane;
    private JPanel panel;
    private DefaultTableModel tableModel;

    public CustomersUI() {

        frame = new JFrame("Full db");
        frame.setLayout(new BorderLayout());
        frame.setMaximumSize(new Dimension(700, 420));
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);

        customersLabel = new JLabel("Customers");

        tableModel = new DefaultTableModel();
        generateColumnNames();

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(5);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(400, 420));

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(customersLabel, c);
        c.gridy = 1;
        panel.add(scrollPane, c);

        frame.add(panel);

    }

    private void generateColumnNames() {
        tableModel.addColumn("id");
        tableModel.addColumn("Customer Name");
        tableModel.addColumn("Transactions committed");
        tableModel.addColumn("Cash amount,$");
    }

    public JPanel getMainPanel() {
        return panel;
    }

    protected void fillTheTable(ArrayList<Customer> customers, ArrayList<Transaction> transactions) {

        Object[] rowData;

        for (Customer c : customers) {
            rowData = generateDataForCustomer(c, transactions);
            tableModel.addRow(rowData);
        }

    }

    protected void updateTable(Transaction transaction) {

        int rowNumber = getRowNumberById(transaction.getCustomer().getId());

        if (rowNumber < 0) {
            insertNewCustomer(transaction);
        } else {
            tableModel.setValueAt((int) tableModel.getValueAt(rowNumber, 2) + 1, rowNumber, 2);
            tableModel.setValueAt(Double.parseDouble(tableModel.getValueAt(rowNumber, 3).toString())
                    + transaction.getPrice(), rowNumber, 3);
        }
    }

    private void insertNewCustomer(Transaction transaction) {

        Customer c = transaction.getCustomer();
        String name = c.getName() + " " + c.getSecondName();

        Object[] rowData = {
                c.getId(),
                name,
                1,
                transaction.getPrice()
        };

        int insertRowNumber = 0;
        int compareIndex;

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            compareIndex = name.compareTo(tableModel.getValueAt(i,1).toString());
            if (compareIndex < 0) {
                insertRowNumber = i;
                break;
            }
        }

        tableModel.insertRow(insertRowNumber, rowData);

    }

    private Object[] generateDataForCustomer(Customer customer, ArrayList<Transaction> transactions) {

        Object[] rowData = new Object[4];
        rowData[0] = customer.getId();
        rowData[1] = customer.getName() + " " + customer.getSecondName();

        int transactionsCount = 0;
        double cashAmount = 0;

        for (Transaction t : transactions) {

            if (t.getCustomer().getId() == customer.getId()) {
                transactionsCount++;
                cashAmount += t.getPrice();
            }

        }

        rowData[2] = transactionsCount;
        rowData[3] = cashAmount;

        return rowData;
    }

    private int getRowNumberById(int id) {

        int rowNumber = -1;

        for (int i = 0; i < tableModel.getRowCount(); i++) {

            int currentId = (int) tableModel.getValueAt(i, 0);
            if (currentId == id) {
                rowNumber = i;
            }
        }
        return rowNumber;
    }
}
