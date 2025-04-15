import javax.swing.*;
import java.awt.*;
import java.io.*;

public class RandProductMaker extends JFrame {
    private JTextField nameField, descField, idField, costField, countField;
    private int recordCount = 0;
    private static final String FILE_NAME = "products.dat";

    public RandProductMaker() {
        setTitle("Rand Product Maker");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 2));

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Description:"));
        descField = new JTextField();
        add(descField);

        add(new JLabel("ID:"));
        idField = new JTextField();
        add(idField);

        add(new JLabel("Cost:"));
        costField = new JTextField();
        add(costField);

        add(new JLabel("Record Count:"));
        countField = new JTextField("0");
        countField.setEditable(false);
        add(countField);

        JButton addButton = new JButton("Add");
        addButton.addActionListener(e -> addRecord());
        add(addButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton);

        setVisible(true);
    }

    private void addRecord() {
        try {
            String name = nameField.getText();
            String desc = descField.getText();
            String id = idField.getText();
            double cost = Double.parseDouble(costField.getText());

            if (name.isEmpty() || desc.isEmpty() || id.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields must be filled.");
                return;
            }

            Product product = new Product(name, desc, id, cost);
            try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "rw")) {
                raf.seek(raf.length());
                product.writeToFile(raf);
            }

            recordCount++;
            countField.setText(String.valueOf(recordCount));

            nameField.setText("");
            descField.setText("");
            idField.setText("");
            costField.setText("");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductMaker::new);
    }
}
