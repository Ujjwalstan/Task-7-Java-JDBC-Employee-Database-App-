/*
 * EmployeeDatabaseApp.java
 * Student-style, simple menu-driven JDBC CRUD app for MySQL (username: root, empty password)
 *
 * Note:
 * - Add MySQL JDBC driver to classpath (e.g., mysql-connector-java.jar)
 * - Create the database and table using the provided SQL script before running.
 *
 * Author: Student (simple style)
 */
import java.sql.*;
import java.util.Scanner;

public class EmployeeDatabaseApp {
    private static final String URL = "jdbc:mysql://localhost:3306/college";
    private static final String USER = "root";
    private static final String PASS = "";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            System.out.println("Connected to DB.");
            createTableIfNotExists(conn); // safety
            while (true) {
                showMenu();
                String choice = sc.nextLine().trim();
                if (choice.equals("1")) addEmployee(conn, sc);
                else if (choice.equals("2")) viewEmployees(conn);
                else if (choice.equals("3")) updateEmployee(conn, sc);
                else if (choice.equals("4")) deleteEmployee(conn, sc);
                else if (choice.equals("5")) { System.out.println("Bye!"); break; }
                else System.out.println("Invalid choice, try again.");
            }
        } catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
        }
        sc.close();
    }

    private static void showMenu() {
        System.out.println("\n=== Employee DB Menu ===");
        System.out.println("1. Add employee");
        System.out.println("2. View all employees");
        System.out.println("3. Update employee");
        System.out.println("4. Delete employee");
        System.out.println("5. Exit");
        System.out.print("Choose: ");
    }

    private static void createTableIfNotExists(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS employees ("
                   + "id INT AUTO_INCREMENT PRIMARY KEY, "
                   + "name VARCHAR(100) NOT NULL, "
                   + "role VARCHAR(100), "
                   + "salary DOUBLE)";
        try (Statement st = conn.createStatement()) {
            st.execute(sql);
        }
    }

    private static void addEmployee(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter name: "); String name = sc.nextLine();
            System.out.print("Enter role: "); String role = sc.nextLine();
            System.out.print("Enter salary: "); double salary = Double.parseDouble(sc.nextLine());
            String sql = "INSERT INTO employees (name, role, salary) VALUES (?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, name);
                pst.setString(2, role);
                pst.setDouble(3, salary);
                int r = pst.executeUpdate();
                System.out.println(r + " row(s) inserted.");
            }
        } catch (SQLException e) {
            System.out.println("Insert error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid salary input.");
        }
    }

    private static void viewEmployees(Connection conn) {
        String sql = "SELECT id, name, role, salary FROM employees";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\nID | Name | Role | Salary");
            System.out.println("-------------------------------");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %.2f%n",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("role"),
                    rs.getDouble("salary"));
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
    }

    private static void updateEmployee(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter employee id to update: "); int id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter new name: "); String name = sc.nextLine();
            System.out.print("Enter new role: "); String role = sc.nextLine();
            System.out.print("Enter new salary: "); double salary = Double.parseDouble(sc.nextLine());
            String sql = "UPDATE employees SET name=?, role=?, salary=? WHERE id=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, name);
                pst.setString(2, role);
                pst.setDouble(3, salary);
                pst.setInt(4, id);
                int r = pst.executeUpdate();
                System.out.println(r + " row(s) updated.");
            }
        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input.");
        }
    }

    private static void deleteEmployee(Connection conn, Scanner sc) {
        try {
            System.out.print("Enter employee id to delete: "); int id = Integer.parseInt(sc.nextLine());
            String sql = "DELETE FROM employees WHERE id=?";
            try (PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, id);
                int r = pst.executeUpdate();
                System.out.println(r + " row(s) deleted.");
            }
        } catch (SQLException e) {
            System.out.println("Delete error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid id.");
        }
    }
}
