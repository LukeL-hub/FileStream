import javax.swing.*;
import java.awt.*;
import java.io.*;

public class RandProductSearch extends JFrame {
    private JTextField searchField;
    private JTextArea resultArea;
    private static final String FILE_NAME = "products.dat";

    public RandProductSearch() {
        setTitle("Rand Product Search");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter name to search:"));
        searchField = new JTextField(20);
        topPanel.add(searchField);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProducts());
        topPanel.add(searchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);

        setVisible(true);
    }

    private void searchProducts() {
        String keyword = searchField.getText().trim().toLowerCase();
        resultArea.setText("");

        try (RandomAccessFile raf = new RandomAccessFile(FILE_NAME, "r")) {
            long recordSize = 2 * (Product.NAME_LENGTH + Product.DESC_LENGTH + Product.ID_LENGTH) + 8;
            long numRecords = raf.length() / recordSize;

            for (int i = 0; i < numRecords; i++) {
                raf.seek(i * recordSize);
                Product product = Product.readFromFile(raf);
                if (product.getName().toLowerCase().contains(keyword)) {
                    resultArea.append(product.toString() + "\n\n");
                }
            }
        } catch (IOException e) {
            resultArea.setText("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RandProductSearch::new);
    }
}
