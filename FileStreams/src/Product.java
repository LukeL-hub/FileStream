import java.io.*;

public class Product implements Serializable {
    private String name;
    private String description;
    private String ID;
    private double cost;

    public static final int NAME_LENGTH = 35;
    public static final int DESC_LENGTH = 75;
    public static final int ID_LENGTH = 6;

    public Product(String name, String description, String ID, double cost) {
        this.name = padString(name, NAME_LENGTH);
        this.description = padString(description, DESC_LENGTH);
        this.ID = padString(ID, ID_LENGTH);
        this.cost = cost;
    }

    public String getName() { return name.trim(); }
    public String getDescription() { return description.trim(); }
    public String getID() { return ID.trim(); }
    public double getCost() { return cost; }

    private static String padString(String str, int length) {
        if (str.length() > length) return str.substring(0, length);
        return String.format("%-" + length + "s", str);
    }

    public void writeToFile(RandomAccessFile raf) throws IOException {
        raf.writeChars(name);
        raf.writeChars(description);
        raf.writeChars(ID);
        raf.writeDouble(cost);
    }

    public static Product readFromFile(RandomAccessFile raf) throws IOException {
        String name = readString(raf, NAME_LENGTH);
        String description = readString(raf, DESC_LENGTH);
        String ID = readString(raf, ID_LENGTH);
        double cost = raf.readDouble();
        return new Product(name, description, ID, cost);
    }

    private static String readString(RandomAccessFile raf, int length) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) sb.append(raf.readChar());
        return sb.toString();
    }

    public String toString() {
        return String.format("Name: %s\nDescription: %s\nID: %s\nCost: $%.2f",
                getName(), getDescription(), getID(), cost);
    }
}
