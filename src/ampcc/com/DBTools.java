package ampcc.com;
import java.sql.*;

public class DBTools {

    final String DB_URL="jdbc:h2:file:..\\data\\db";

    public static void main(String[] args) {
        DBTools t = new DBTools();
        t.readSongs();
    }

    public void readSongs() {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement();
            String line = "SELECT * FROM Song";
            ResultSet results = stat.executeQuery(line);
            printResults(results);
            stat.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void readArtists() {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement();
            String line = "SELECT * FROM Artist";
            ResultSet results = stat.executeQuery(line);
            printResults(results);
            stat.close();
            conn.close();
        } catch (Exception ex) {
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public void readTagSongs(String t_id) {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement();
            String line = "SELECT * FROM Song s, SongTag st WHERE s.id = st.song_id " +
                    " AND st.Tag_id = " + t_id + ";";
            ResultSet results = stat.executeQuery(line);
            printResults(results);
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    private void printResults(ResultSet r) {
        try {
            ResultSetMetaData rmd = r.getMetaData();
            int columnCount = rmd.getColumnCount();

            System.out.print(rmd.getColumnName(1));
            for (int i = 2; i <= columnCount; i++) {
                System.out.print(" - " + rmd.getColumnName(i));
            }
            System.out.println();

            while (r.next()) {
                Object[] values = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    values[i - 1] = r.getObject(i);
                }
                System.out.print(values[0].toString());
                for (int item = 1; item < values.length; item++) {
                    System.out.print(" - " + values[item].toString());
                }
                System.out.println();
            }
        } catch (Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
