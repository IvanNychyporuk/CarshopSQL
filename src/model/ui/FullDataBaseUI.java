package model.ui;

import model.ui.products.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Ivan on 09.12.2015.
 */
public class FullDataBaseUI extends JPanel {

    protected JFrame frame;
    private JTable table;
    private JLabel shopDbLabel;
    protected JScrollPane scrollPane;
    private JPanel panel;
    private DefaultTableModel tableModel;

    public FullDataBaseUI() {

        frame = new JFrame("Full db");
        frame.setLayout(new BorderLayout());
        frame.setMaximumSize(new Dimension(700, 420));
        frame.setLocation(400, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(this);

        shopDbLabel = new JLabel("Product database");

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
        panel.add(shopDbLabel, c);
        c.gridy = 1;
        panel.add(scrollPane, c);

        frame.add(panel);
    }

    private void generateColumnNames() {
        tableModel.addColumn("id");
        tableModel.addColumn("brand");
        tableModel.addColumn("name");
        tableModel.addColumn("color");
        tableModel.addColumn("additional info");
        tableModel.addColumn("price");
        tableModel.addColumn("quantity");
    }

    public JPanel getMainPanel() {
        return panel;
    }

    protected void fillTheTable(ArrayList<Product> fullDataBase) {

        if (tableModel.getRowCount() > 0) {
            clearTable();
        }

        Object[] rowData;

        for (Product p : fullDataBase) {

            rowData = new Object[]{
                    p.getId(),
                    p.getBrand(),
                    p.getName(),
                    p.getColor(),
                    p.getAdditionalInfo(),
                    p.getPrice(),
                    p.getQuantity()
            };
            tableModel.addRow(rowData);
        }
    }

    protected void updateTable(ArrayList<Product> productsInBasket) {

        for (Product p : productsInBasket) {

            int rowNumber = getRowNumberById(p.getId());
            int oldQuantity = (int) tableModel.getValueAt(rowNumber, 6);
            tableModel.setValueAt((oldQuantity - p.getQuantity()), rowNumber, 6);

        }

    }

    protected void updateTable(Product product, int quantity) {

        int rowNumber = getRowNumberById(product.getId());
        tableModel.setValueAt(quantity, rowNumber, 6);

    }

    protected void removeProductFromTable(Product product) {

        int rowNumber = getRowNumberById(product.getId());

        if (rowNumber != -1) {
            tableModel.removeRow(rowNumber);
        }

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

    private void clearTable() {

        int rowCount = tableModel.getRowCount();
        int counter = 0;

        while (counter < rowCount) {
            tableModel.removeRow(0);
            counter++;
        }

    }
}
