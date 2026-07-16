import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DataProcessor {
    public static void main(String[] args) {
        // Creates a local SQLite file named hospitals.db in your project folder
        String url = "jdbc:sqlite:hospitals.db";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS hospitals (" +
                                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                "name TEXT NOT NULL, " +
                                "type TEXT NOT NULL, " +
                                "latitude REAL NOT NULL, " +
                                "longitude REAL NOT NULL, " +
                                "address TEXT, " +
                                "phone TEXT, " +
                                "facilities TEXT, " +
                                "reviews TEXT" +
                                ");";

        String clearTableSQL = "DELETE FROM hospitals;";
        String insertSQL = "INSERT INTO hospitals (name, type, latitude, longitude, address, phone, facilities, reviews) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            stmt.execute(createTableSQL);
            stmt.execute(clearTableSQL);

            // 1. Government Hospital (Green Pointer)
            pstmt.setString(1, "District Headquarters Government Hospital");
            pstmt.setString(2, "GOV");
            pstmt.setDouble(3, 20.8444);
            pstmt.setDouble(4, 85.1511);
            pstmt.setString(5, "Main Town Road, near Daily Market, Angul, Odisha");
            pstmt.setString(6, "06764-230001");
            pstmt.setString(7, "24/7 Emergency, General Surgery, Pediatric Ward, Free Medicine Distribution");
            pstmt.setString(8, "Review 1: Crowded but medical care is reliable. Review 2: Cooperative staff.");
            pstmt.addBatch();

            // 2. Private Hospital (Red Pointer)
            pstmt.setString(1, "Apollo Clinic & Private Medical Center");
            pstmt.setString(2, "PVT");
            pstmt.setDouble(3, 20.8490);
            pstmt.setDouble(4, 85.1560);
            pstmt.setString(5, "Bazar Chhak, NH-55 Intersection, Angul, Odisha");
            pstmt.setString(6, "06764-235678");
            pstmt.setString(7, "Cardiology Consultation, Orthopedics, Advanced Diagnostics Laboratory");
            pstmt.setString(8, "Review 1: Very clean and fast process. Review 2: Quality treatment but costly.");
            pstmt.addBatch();

            pstmt.executeBatch();
            System.out.println(">>> Local SQL database file 'hospitals.db' created successfully via Java!");

        } catch (Exception e) {
            System.err.println("Java Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}