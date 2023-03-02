package ampcc.com;
import java.sql.*;

public class AMP_H2 {
    public static void main(String[] args) {
        test();
    }

    public static void test() {
        final String DB_URL="jdbc:h2:file:..\\data\\db";

        try {
            Connection conn = DriverManager.getConnection(DB_URL);
            Statement stat = conn.createStatement();
            ResultSet results = stat.executeQuery("SELECT * FROM Song");

            System.out.println("Reading Songs table...");
            while (results.next()) {
                System.out.println(results);
            }

            stat.close();
            conn.close();
            System.out.println("Done!");

        }

        catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }

    }
}