package model.ui;


import model.ui.products.Product;
import model.ui.products.Transaction;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

public class ReportUI extends JPanel {

    protected JFrame frame;
    private JTable table;
    private JLabel transactionsLabel;
    protected JScrollPane scrollPane;
    private JPanel panel;
    private DefaultTableModel tableModel;

    public ReportUI() {

        frame = new JFrame("Report");
        frame.setLayout(new BorderLayout());
        frame.setMaximumSize(new Dimension(700, 420));
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setOpaque(true);
        frame.getContentPane().add(this);

        transactionsLabel = new JLabel("Transactions");

        tableModel = new DefaultTableModel();
        generateColumnNames();

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setAutoResizeMode(5);

        scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(670, 420));

        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.gridx = 0;
        c.gridy = 0;
        panel.add(transactionsLabel, c);
        c.gridy = 1;
        panel.add(scrollPane, c);

        frame.add(panel);
    }

    public JPanel getMainPanel() {
        return panel;
    }

    private void generateColumnNames() {

        tableModel.addColumn("#");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Color");
        tableModel.addColumn("Characteristics");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Discount");
        tableModel.addColumn("Total price, $");
        tableModel.addColumn("Customer");
        tableModel.addColumn("Date");

    }

    protected void addTransactionToReport(Transaction t) {

        Object[] reportLine;

        for (Product p : t.getProducts()) {

            reportLine = new Object[]{t.getId(),
                    p.getBrand() + " " + p.getName(),
                    p.getColor(),
                    p.getAdditionalInfo(),
                    p.getQuantity(),
                    t.getDiscount() + "%",
                    (p.getPrice() * p.getQuantity()),
                    t.getCustomer().getName() + " " + t.getCustomer().getSecondName(),
                    t.getDay() + "." + t.getMounth() + "." + t.getYear()
            };
            tableModel.addRow(reportLine);
        }

        table.repaint();
    }

    protected void createReportData(ArrayList<Transaction> transactions) {

        Transaction t;
        Object[] reportLine;

        for (int i = 0; i < transactions.size(); i++) {

            t = transactions.get(i);

            for (Product p : t.getProducts()) {

                reportLine = new Object[]{t.getId(),
                        p.getBrand() + " " + p.getName(),
                        p.getColor(),
                        p.getAdditionalInfo(),
                        p.getQuantity(),
                        t.getDiscount() + "%",
                        (p.getPrice() * p.getQuantity()),
                        t.getCustomer().getName() + " " + t.getCustomer().getSecondName(),
                        t.getDay() + "." + t.getMounth() + "." + t.getYear()
                };
                tableModel.addRow(reportLine);
            }
        }
        table.repaint();
    }

}

