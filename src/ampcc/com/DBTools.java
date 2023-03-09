package ampcc.com;
import java.sql.*;

public class DBTools {

    final String DB_URL="jdbc:h2:file:..\\data\\db";

    public static void main(String[] args) {
        DBTools t = new DBTools();

        System.out.println("All Songs:");
        t.readSongs();
        System.out.println();

        System.out.println("All Artists:");
        t.readArtists();
        System.out.println();
    }

    public void readSongs() {
        String line = "SELECT * FROM Song";
        runLine(line);
    }

    public void readArtists() {
        String line = "SELECT * FROM Artist";
        runLine(line);
    }

    public void readTagSongs(String t_id) {
        String line = "SELECT * FROM Song s, SongTag st WHERE s.id = st.song_id " +
                " AND st.Tag_id = " + t_id + ";";
        runLine(line);

    }

    private Object[] arrayResults(ResultSet r) {
        try {
            ResultSetMetaData rmd = r.getMetaData();
            int columnCount = rmd.getColumnCount();

            String[] colNames = new String[columnCount];
            String[][] data = new String[r.getRow()][columnCount];

            for (int i = 1; i <= columnCount; i++) {
                colNames[i-1] = rmd.getColumnName(i);
            }
            int rowCount = 0;
            while (r.next()) {
                Object[] values = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    values[i - 1] = r.getObject(i);
                }
                for (int item = 0; item < values.length; item++) {
                    data[rowCount][item] = values[item].toString();
                }
                rowCount++;
            }
            return new Object[]{colNames, data};
        } catch (Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
        return null;
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

    private void runLine(String line) {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement();
            ResultSet results = stat.executeQuery(line);
            printResults(results);
            stat.close();
            conn.close();
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
