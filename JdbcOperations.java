// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class JdbcOperations {
   private static final String URL = "jdbc:mysql://localhost:3306/jdbc";
   private static final String USER = "root";
   private static final String PASSWORD = "PSWD";

   public JdbcOperations() {
   }

   public static void main(String[] var0) {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection var1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc", "root", "Mysql26");

         try {
            System.out.println("✅ Connected to database!");
            createUsersTable(var1);
            Scanner var2 = new Scanner(System.in);

            label45:
            while(true) {
               while(true) {
                  System.out.println("\n===== CRUD Operations Menu =====");
                  System.out.println("1. Insert User");
                  System.out.println("2. Read Users");
                  System.out.println("3. Update User");
                  System.out.println("4. Delete User");
                  System.out.println("5. Exit");
                  System.out.print("Enter your choice: ");
                  int var3 = var2.nextInt();
                  var2.nextLine();
                  switch (var3) {
                     case 1:
                        insertRecord(var1, var2);
                        break;
                     case 2:
                        readRecords(var1);
                        break;
                     case 3:
                        updateRecord(var1, var2);
                        break;
                     case 4:
                        deleteRecord(var1, var2);
                        break;
                     case 5:
                        System.out.println("\ud83d\ude80 Exiting program...");
                        var2.close();
                        break label45;
                     default:
                        System.out.println("❌ Invalid choice. Try again.");
                  }
               }
            }
         } catch (Throwable var5) {
            if (var1 != null) {
               try {
                  var1.close();
               } catch (Throwable var4) {
                  var5.addSuppressed(var4);
               }
            }

            throw var5;
         }

         if (var1 != null) {
            var1.close();
         }

      } catch (Exception var6) {
         System.err.println("❌ Error: " + var6.getMessage());
         var6.printStackTrace();
      }
   }

   private static void createUsersTable(Connection var0) throws SQLException {
      String var1 = "CREATE TABLE IF NOT EXISTS users (\n    id INT AUTO_INCREMENT PRIMARY KEY,\n    name VARCHAR(255) NOT NULL,\n    email VARCHAR(255) NOT NULL UNIQUE\n);\n";
      Statement var2 = var0.createStatement();

      try {
         var2.executeUpdate(var1);
      } catch (Throwable var6) {
         if (var2 != null) {
            try {
               var2.close();
            } catch (Throwable var5) {
               var6.addSuppressed(var5);
            }
         }

         throw var6;
      }

      if (var2 != null) {
         var2.close();
      }

   }

   private static void insertRecord(Connection var0, Scanner var1) throws SQLException {
      System.out.print("Enter Name: ");
      String var2 = var1.nextLine();
      System.out.print("Enter Email: ");
      String var3 = var1.nextLine();
      String var4 = "INSERT INTO users (name, email) VALUES (?, ?)";
      PreparedStatement var5 = var0.prepareStatement(var4);

      try {
         var5.setString(1, var2);
         var5.setString(2, var3);
         int var6 = var5.executeUpdate();
         System.out.println("✅ Inserted " + var6 + " row(s) successfully.");
      } catch (Throwable var9) {
         if (var5 != null) {
            try {
               var5.close();
            } catch (Throwable var8) {
               var9.addSuppressed(var8);
            }
         }

         throw var9;
      }

      if (var5 != null) {
         var5.close();
      }

   }

   private static void readRecords(Connection var0) throws SQLException {
      String var1 = "SELECT * FROM users";
      Statement var2 = var0.createStatement();

      try {
         ResultSet var3 = var2.executeQuery(var1);

         try {
            System.out.println("\n===== Users List =====");

            while(var3.next()) {
               PrintStream var10000 = System.out;
               int var10001 = var3.getInt("id");
               var10000.println("\ud83c\udd94 ID: " + var10001 + " | \ud83d\udc64 Name: " + var3.getString("name") + " | ✉ Email: " + var3.getString("email"));
            }
         } catch (Throwable var8) {
            if (var3 != null) {
               try {
                  var3.close();
               } catch (Throwable var7) {
                  var8.addSuppressed(var7);
               }
            }

            throw var8;
         }

         if (var3 != null) {
            var3.close();
         }
      } catch (Throwable var9) {
         if (var2 != null) {
            try {
               var2.close();
            } catch (Throwable var6) {
               var9.addSuppressed(var6);
            }
         }

         throw var9;
      }

      if (var2 != null) {
         var2.close();
      }

   }

   private static void updateRecord(Connection var0, Scanner var1) throws SQLException {
      System.out.print("Enter User ID to update: ");
      int var2 = var1.nextInt();
      var1.nextLine();
      System.out.print("Enter New Email: ");
      String var3 = var1.nextLine();
      String var4 = "UPDATE users SET email = ? WHERE id = ?";
      PreparedStatement var5 = var0.prepareStatement(var4);

      try {
         var5.setString(1, var3);
         var5.setInt(2, var2);
         int var6 = var5.executeUpdate();
         System.out.println("✅ Updated " + var6 + " row(s) successfully.");
      } catch (Throwable var9) {
         if (var5 != null) {
            try {
               var5.close();
            } catch (Throwable var8) {
               var9.addSuppressed(var8);
            }
         }

         throw var9;
      }

      if (var5 != null) {
         var5.close();
      }

   }

   private static void deleteRecord(Connection var0, Scanner var1) throws SQLException {
      System.out.print("Enter User ID to delete: ");
      int var2 = var1.nextInt();
      String var3 = "DELETE FROM users WHERE id = ?";
      PreparedStatement var4 = var0.prepareStatement(var3);

      try {
         var4.setInt(1, var2);
         int var5 = var4.executeUpdate();
         System.out.println("✅ Deleted " + var5 + " row(s) successfully.");
      } catch (Throwable var8) {
         if (var4 != null) {
            try {
               var4.close();
            } catch (Throwable var7) {
               var8.addSuppressed(var7);
            }
         }

         throw var8;
      }

      if (var4 != null) {
         var4.close();
      }

   }
}
