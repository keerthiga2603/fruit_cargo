import java.sql.*;
import java.util.Scanner;

public class DB9 {
    // ✅ Corrected MySQL connection details
    private static final String URL = "jdbc:mysql://localhost:3306/farmer"; 
    private static final String USER = "root";  
    private static final String PASSWORD = "pswd";  

    public static void main(String[] args) {
        try {
            // ✅ Step 1: Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ✅ Step 2: Establish Connection
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                System.out.println("✅ Connected to database!");

                // ✅ Ensure 'Farmer' and 'Product' tables exist
                createTables(conn);

                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("\n===== CRUD Operations Menu =====");
                    System.out.println("1. Insert Farmer");
                    System.out.println("2. Insert Product");
                    System.out.println("3. Read Farmers");
                    System.out.println("4. Read Products");
                    System.out.println("5. Update Farmer");
                    System.out.println("6. Update Product Rate");
                    System.out.println("7. Delete Product");
                    System.out.println("8. Delete Farmer");
                    System.out.println("9. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1 -> insertFarmer(conn, scanner);
                        case 2 -> insertProduct(conn, scanner);
                        case 3 -> readFarmers(conn);
                        case 4 -> readProducts(conn);
                        case 5 -> updateFarmer(conn, scanner);
                        case 6 -> updateProductRate(conn, scanner);
                        case 7 -> deleteProduct(conn, scanner);
                        case 8 -> deleteFarmer(conn, scanner);
                        case 9 -> {
                            System.out.println("🚀 Exiting program...");
                            scanner.close();
                            return;
                        }
                        default -> System.out.println("❌ Invalid choice. Try again.");
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ✅ Ensures 'Farmer' and 'Product' tables exist
    private static void createTables(Connection conn) throws SQLException {
        String createFarmerTable = """
            CREATE TABLE IF NOT EXISTS Farmer (
                FarmerID INT AUTO_INCREMENT PRIMARY KEY,
                FarmerName VARCHAR(255) NOT NULL,
                Location VARCHAR(255) DEFAULT 'Unknown'
            );
            """;

        String createProductTable = """
            CREATE TABLE IF NOT EXISTS Product (
                ProductID INT AUTO_INCREMENT PRIMARY KEY,
                FarmerID INT,
                Rate FLOAT NOT NULL,
                Quantity INT NOT NULL,
                FOREIGN KEY (FarmerID) REFERENCES Farmer(FarmerID) ON DELETE CASCADE
            );
            """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(createFarmerTable);
            stmt.executeUpdate(createProductTable);
        }
    }

    // ✅ Insert Farmer
    private static void insertFarmer(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Farmer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Farmer Location: ");
        String location = scanner.nextLine();

        String sql = "INSERT INTO Farmer (FarmerName, Location) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, location);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Inserted " + rows + " row(s) successfully.");
        }
    }

    // ✅ Insert Product
    private static void insertProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter FarmerID: ");
        int farmerId = scanner.nextInt();
        System.out.print("Enter Product Rate: ");
        float rate = scanner.nextFloat();
        System.out.print("Enter Product Quantity: ");
        int quantity = scanner.nextInt();

        String sql = "INSERT INTO Product (FarmerID, Rate, Quantity) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, farmerId);
            pstmt.setFloat(2, rate);
            pstmt.setInt(3, quantity);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Inserted " + rows + " row(s) successfully.");
        }
    }

    // ✅ Read Farmers
    private static void readFarmers(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Farmer";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n===== Farmers List =====");
            while (rs.next()) {
                System.out.println("🆔 FarmerID: " + rs.getInt("FarmerID") + " | Name: " + rs.getString("FarmerName") + " | Location: " + rs.getString("Location"));
            }
        }
    }

    // ✅ Read Products
    private static void readProducts(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Product";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n===== Products List =====");
            while (rs.next()) {
                System.out.println("🆔 ProductID: " + rs.getInt("ProductID") + " | FarmerID: " + rs.getInt("FarmerID") + " | Rate: " + rs.getFloat("Rate") + " | Quantity: " + rs.getInt("Quantity"));
            }
        }
    }

    // ✅ Update Farmer
    private static void updateFarmer(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter FarmerID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New Farmer Name: ");
        String newName = scanner.nextLine();
        System.out.print("Enter New Farmer Location: ");
        String newLocation = scanner.nextLine();

        String sql = "UPDATE Farmer SET FarmerName = ?, Location = ? WHERE FarmerID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newName);
            pstmt.setString(2, newLocation);
            pstmt.setInt(3, id);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Updated " + rows + " row(s) successfully.");
        }
    }

    // ✅ Update Product Rate
    private static void updateProductRate(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter ProductID to update: ");
        int id = scanner.nextInt();
        System.out.print("Enter New Product Rate: ");
        float newRate = scanner.nextFloat();

        String sql = "UPDATE Product SET Rate = ? WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setFloat(1, newRate);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Updated " + rows + " row(s) successfully.");
        }
    }

    // ✅ Delete Product
    private static void deleteProduct(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter ProductID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM Product WHERE ProductID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Deleted " + rows + " row(s) successfully.");
        }
    }

    // ✅ Delete Farmer
    private static void deleteFarmer(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter FarmerID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM Farmer WHERE FarmerID = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Deleted " + rows + " row(s) successfully.");
        }
    }
}
