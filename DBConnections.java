package KlaraProject;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnections {

    public static Connection getConnection() {
        String username = "melenevasili";
        String pass = "12345";
        String url = "jdbc:mysql://localhost:3306/databasedoctor";
        try {
            return DriverManager.getConnection(url, username, pass);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        Connection conn = getConnection();
        if (conn != null) {
            System.out.println("Συνδέθηκε στη βάση!");
        } else {
            System.out.println("Δεν έγινε σύνδεση.");
        }
    }
}
