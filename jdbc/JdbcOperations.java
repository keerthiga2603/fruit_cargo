import java.sql.*;
import java.util.Scanner;

public class JdbcOperations {
    // ✅ Corrected MySQL connection details
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc"; 
    private static final String USER = "root";  
    private static final String PASSWORD = "Mysql26";  

    public static void main(String[] args) {
        try {
            // ✅ Step 1: Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ✅ Step 2: Establish Connection
            try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                System.out.println("✅ Connected to database!");

                // ✅ Ensure 'users' table exists
                createUsersTable(conn);

                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.println("\n===== CRUD Operations Menu =====");
                    System.out.println("1. Insert User");
                    System.out.println("2. Read Users");
                    System.out.println("3. Update User");
                    System.out.println("4. Delete User");
                    System.out.println("5. Exit");
                    System.out.print("Enter your choice: ");
                    int choice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (choice) {
                        case 1 -> insertRecord(conn, scanner);
                        case 2 -> readRecords(conn);
                        case 3 -> updateRecord(conn, scanner);
                        case 4 -> deleteRecord(conn, scanner);
                        case 5 -> {
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

    // ✅ Ensures 'users' table exists before performing CRUD operations
    private static void createUsersTable(Connection conn) throws SQLException {
        String sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                email VARCHAR(255) NOT NULL UNIQUE
            );
            """;
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        }
    }

    // ✅ Insert Operation
    private static void insertRecord(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Inserted " + rows + " row(s) successfully.");
        }
    }

    // ✅ Read Operation
    private static void readRecords(Connection conn) throws SQLException {
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("\n===== Users List =====");
            while (rs.next()) {
                System.out.println("🆔 ID: " + rs.getInt("id") + 
                                   " | 👤 Name: " + rs.getString("name") +
                                   " | ✉ Email: " + rs.getString("email"));
            }
        }
    }

    // ✅ Update Operation
    private static void updateRecord(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter User ID to update: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter New Email: ");
        String newEmail = scanner.nextLine();

        String sql = "UPDATE users SET email = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newEmail);
            pstmt.setInt(2, id);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Updated " + rows + " row(s) successfully.");
        }
    }

    // ✅ Delete Operation
    private static void deleteRecord(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter User ID to delete: ");
        int id = scanner.nextInt();

        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println("✅ Deleted " + rows + " row(s) successfully.");
        }
    }
}