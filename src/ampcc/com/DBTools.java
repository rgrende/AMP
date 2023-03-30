package ampcc.com;
import java.sql.*;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

public class DBTools {

    final String DB_URL="jdbc:h2:file:..\\data\\db";

    public static void main(String[] args) {
        test();
    }

    public static void test() {
        DBTools t = new DBTools();

        System.out.println("All Songs:");
        t.readSongs(1);
        System.out.println();

        System.out.println("All Artists:");
        t.readArtists(1);
        System.out.println();

        System.out.println("All Playlist Names:");
        t.readPlaylistNames(2);
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

    public void readPlaylistNames(int flag) {
        String line = "SELECT playlist_name FROM Playlist";
        run(flag, line);
    }

    public void readSongTag(String t_id, int flag) {
        String line = "SELECT * FROM Song s, SongTag st WHERE s.song_id = st.song_id " +
                " AND st.Tag_id = " + t_id + ";";
        run(flag, line);
    }

    public void readSongPlaylist(String p_id, int flag) {
        String line = "SELECT * FROM Song s, SongPlaylist sp WHERE s.song_id = sp.song_id " +
                " AND sp.playlist_id = " + p_id + ";";
        run(flag, line);
    }

    private void run(int flag, String line) {
        if (flag == 1) {
            runLine(line);
        } else if (flag == 2) {
            Object[][] a = arrayLine(line);
            for (Object[] objects : a) {
                for (Object object : objects) {
                    System.out.println(object);
                }
            }
        }
    }

    private Object[][] arrayResults(ResultSet r) { //TODO: return array in more accessible way
        try {
            ResultSetMetaData rmd = r.getMetaData();
            int columnCount = rmd.getColumnCount();

            r.last();
            int rowCount = r.getRow();
            r.beforeFirst();

            String[] colNames = new String[columnCount];
            String[][] data = new String[rowCount][columnCount];

            for (int i = 1; i <= columnCount; i++) {
                colNames[i-1] = rmd.getColumnName(i);
            }
            while (r.next()) {
                Object[] values = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    values[i - 1] = r.getObject(i);
                }
                for (int item = 0; item < values.length; item++) {
                    data[r.getRow()-1][item] = values[item].toString();
                }
            }
            return data;
        } catch (Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
        return new Object[0][0];
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

    public void runLine(String line) {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY);
            ResultSet results = stat.executeQuery(line);
            printResults(results);
            stat.close();
            conn.close();
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public Object[][] arrayLine(String line) { //TODO: fix and make it return array
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY);
            ResultSet results = stat.executeQuery(line);
            Object[][] ret = arrayResults(results);
            stat.close();
            conn.close();
            return ret;
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
        return new Object[0][0];
    }

}
