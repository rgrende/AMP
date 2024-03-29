package ampcc.com;
import org.h2.tools.RunScript;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.*;

import static java.sql.ResultSet.CONCUR_READ_ONLY;
import static java.sql.ResultSet.TYPE_SCROLL_INSENSITIVE;

public class DBTools {

    final String DB_URL="jdbc:h2:file:../data/db";

    public static void main(String[] args) {
        initialize();
        modMethods();
        test();
    }

    public static void initialize() {
        DBTools t = new DBTools();
        t.runScript("scripts/AMPddl.txt");
        t.runScript("scripts/testdata.txt");
    }

    public static void modMethods() {
        DBTools t = new DBTools();
        t.addArtist("Billy Joel");
        t.addPlaylist("Intermission");
        t.addSong("Billy Joel","Piano Man","123","1980","songs/Piano Man.mp3");
        System.out.println(t.getSongPath("Piano Man"));
    }

    public static void test() {
        DBTools t = new DBTools();

        System.out.println("All Songs:");
        t.readSongs(1);
        System.out.println();

        System.out.println("All Artists:");
        t.readArtists(1);
        System.out.println();

        System.out.println("All Playlist Names (Array print):");
        t.readPlaylistNames(2);
        System.out.println();

        System.out.println("All Playlist Names (Array get):");
        String[] r = t.getPlaylistNames();
        for (String s: r) {
            System.out.println(s);
        }
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

    public String[] getPlaylistNames() {
        String line = "SELECT playlist_name FROM Playlist";
        String[][] result = arrayLine(line);
        String[] playlists = new String[result.length];
        int count = 0;
        for (String[] row : result) {
            for (String s : row) {
                playlists[count] = s;
            }
            count++;
        }
        return playlists;
    }


    public String[] getSongNames() {
        String line = "SELECT song_name FROM Song";
        String[][] result = arrayLine(line);
        String[] songs = new String[result.length];
        int count = 0;
        for (String[] row : result) {
            for (String s : row) {
                songs[count] = s;
            }
            count++;
        }
        return songs;
    }


    public void readSongTag(String t_id, int flag) {
        String line = "SELECT * FROM Song s, SongTag st WHERE s.song_id = st.song_id " +
                " AND st.Tag_id = " + t_id + ";";
        run(flag, line);
    }

    public String getSongID(String s_name) {
        String line = "SELECT id FROM Song WHERE Song.song_name = '" + s_name + "';";
        String[][] result =  arrayLine(line);
        String sid = "";
        for (String[] row : result) {
            for (String s : row) {
                sid = s;
            }
        }
        return sid;
    }

    public void addSongToPlaylist(String s_id, String p_id){
        String line = "INSERT INTO SongPlaylist(song_id,playlist_id) VALUES (" + s_id + "," + p_id + ");";
        execLine(line);
    }

    public void removeSongFromPlaylist(String s_id, String p_id){
        String line = "DELETE FROM SongPlaylist WHERE song_id=" + s_id + " AND playlist_id=" + p_id + ";";
        execLine(line);
    }

    public String[] getSongPlaylist(String p_id) {
        String line = "SELECT DISTINCT s.song_name FROM Song s, SongPlaylist sp, Playlist p WHERE s.id = sp.song_id " +
                " AND sp.playlist_id = " + p_id + ";";
        String[][] result = arrayLine(line);
        String[] songs = new String[result.length];
        int count = 0;
        for (String[] row : result) {
            for (String s : row) {
                songs[count] = s;
            }
            count++;
        }
        return songs;
    }

    public String getPlaylistID(String p_name) {
        String line = "SELECT id FROM Playlist WHERE Playlist.playlist_name = '" + p_name + "';";
        String[][] result =  arrayLine(line);
        String pid = "";
        for (String[] row : result) {
            for (String s : row) {
                pid = s;
            }
        }
        return pid;
    }

    public String getSongPath(String s_name) {
        String line = "SELECT file_path FROM Song WHERE Song.song_name = '" + s_name + "';";
        String[][] result =  arrayLine(line);
        String path = "";
        for (String[] row : result) {
            for (String s : row) {
                path = s;
            }
        }
        return path;
    }

    public void addPlaylist(String p_name) {
        String line = "INSERT INTO Playlist(id,playlist_name) VALUES " +
                "(NEXT VALUE FOR playlist_seq, '" + p_name + "');";
        execLine(line);
    }

    public void addArtist(String a_name) {
        String line = "INSERT INTO Artist(id,artist_name) VALUES " +
                "(NEXT VALUE FOR artist_seq, '" + a_name + "');";
        execLine(line);
    }

    public void addSong(String artist_name, String s_name, String s_length, String release_year, String file_path) {
        String line = "INSERT INTO Song(id,artist_id,song_name,song_length,release_year,file_path) VALUES " +
                "(NEXT VALUE FOR song_seq, (SELECT id FROM Artist WHERE artist_name='" + artist_name + "'), '" +
                s_name + "', " + s_length + ", " + release_year + ", '" + file_path + "');";
        execLine(line);
        //add a song to the library not to the interface
        //select song, song name given, no id or file path needed.
    }

    private void run(int flag, String line) {
        if (flag == 1) {
            runLine(line);
        } else if (flag == 2) {
            String[][] a = arrayLine(line);
            for (String[] strings : a) {
                for (String s : strings) {
                    System.out.println(s);
                }
            }
        }
    }

    private String[][] arrayResults(ResultSet r) {
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
                    if (rmd.getColumnType(i) != 12) {
                        values[i - 1] = r.getObject(i).toString();
                    } else {
                        values[i - 1] = r.getString(i);
                    }
                }
                for (int item = 0; item < values.length; item++) {
                    data[r.getRow()-1][item] = values[item].toString();
                }
            }
            return data;
        } catch (Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
        return new String[0][0];
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

    public void execLine(String line) {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY);
            stat.execute(line);
            stat.close();
            conn.close();
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

    public String[][] arrayLine(String line) {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            Statement stat = conn.createStatement(TYPE_SCROLL_INSENSITIVE, CONCUR_READ_ONLY);
            ResultSet results = stat.executeQuery(line);
            String[][] ret = arrayResults(results);
            stat.close();
            conn.close();
            return ret;
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
        return new String[0][0];
    }

    private void runScript(String fn) {
        try {
            Connection conn = DriverManager.getConnection(this.DB_URL);
            RunScript.execute(conn, new FileReader(fn));
            conn.close();
        } catch(Exception ex){
            System.out.println("ERROR: " + ex.getMessage());
        }
    }

}
