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

            stat.execute("CREATE TABLE Song(id NUMERIC (31) PRIMARY KEY ,\n" +
                    "    artist_id NUMERIC (31) NOT NULL ,\n" +
                    "    name VARCHAR NOT NULL ,\n" +
                    "    length NUMERIC NOT NULL ,\n" +
                    "    year NUMERIC (4) ,\n" +
                    "    file VARCHAR NOT NULL)");
            stat.execute("CREATE TABLE Artist\n" +
                    "(id NUMERIC (31) PRIMARY KEY ,\n" +
                    "name VARCHAR)");

            stat.execute("INSERT INTO Artist(id,\"name\") VALUES\n" +
                    "    (1, 'I DONT KNOW HOW BUT THEY FOUND ME')");
            stat.execute("INSERT INTO Song(id,artist_id,\"name\",length,\"year\",\"file\") VALUES\n" +
                    "    (1,1,'DO IT ALL THE TIME',167,2018,'songfilepath')");

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