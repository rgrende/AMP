package ampcc.com;
import java.sql.*;

public class DBTools {

    final String DB_URL="jdbc:h2:file:..\\data\\db";

    public static void main(String[] args) {
        DBTools t = new DBTools();

        System.out.println("All Songs:");
        t.readSongs(1);
        System.out.println();

        System.out.println("All Artists:");
        t.readArtists(2);
        System.out.println();

        System.out.println("All Playlists:");
        t.readPlaylists(2);
        System.out.println();
    }

    public void readSongs(int flag) { // flag = 1 = runLine, flag = 2 = arrayLine
        String line = "SELECT * FROM Song";
        run(flag, line);
    }

    public void readArtists(int flag) {
        String line = "SELECT * FROM Artist";
        run(flag, line);
    }

    public void readPlaylists(int flag) {
        String line = "SELECT * FROM Playlist";
        run(flag, line);
    }

    public void readTagSongs(String t_id, int flag) {
        String line = "SELECT * FROM Song s, SongTag st WHERE s.song_id = st.song_id " +
                " AND st.Tag_id = " + t_id + ";";
        run(flag, line);
    }

    private void run(int flag, String line) {
        if (flag == 1) {
            runLine(line);
        } else if (flag == 2) {
            Object[] a = arrayLine(line);
            for (Object o : a) {
                System.out.println(o);
            }
        }
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

    private void run(ResultSet r) {
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

    public void runLine(String line) {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement();
            ResultSet results = stat.executeQuery(line);
            run(results); // change to Object[] ret = arrayResults(results); when needed
            stat.close();
            conn.close();
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public Object[] arrayLine(String line) {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement();
            ResultSet results = stat.executeQuery(line);
            Object[] ret = arrayResults(results);
            stat.close();
            conn.close();
            return ret;
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
        return new Object[0];
    }

}
