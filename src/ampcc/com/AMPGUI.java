/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ampcc.com;

//imports
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Arrays;




/**
 * @author rakiahgrende
 */
public class AMPGUI extends JFrame {
    private static final String playImage = "/resources/images/playButton.png";
    private static final String pauseImage = "/resources/images/pauseButton.png";
    private static final ImageIcon playIcon = new ImageIcon(MusicPlayer.class.getResource(playImage));
    private static final ImageIcon pauseIcon = new ImageIcon(MusicPlayer.class.getResource(pauseImage));


    private FileInputStream fileInputStream;
    private File myFile = null;
    private String filename;
    private String filePath;
    private long totalLength;//keep this individual
    private long skip;//keep this individual
    private Player player;//from jlayer-1.0.1.jar
    private Thread playThread;//keep this individual
    private float currentVolume = 0F;
    private List<File> musicFiles = new ArrayList<>();
    private int musicFileIndex = 0;
    private DefaultListModel songsToPlay;
    private DBTools db = new DBTools();

    /**
     * Creates new form m
     */
    public AMPGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        DBTools internalDB = new DBTools();
        backPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        playlistList = new javax.swing.JList<>();
        //for (String pl : db.getPlaylistNames()) {playlistList.add(new javax.swing.JLabel(pl));}
        playlists = new javax.swing.JLabel();
        fadeButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        songQueue = new javax.swing.JList();
        shuffleButton = new javax.swing.JToggleButton();
        nextButton = new javax.swing.JButton();
        backButton = new javax.swing.JButton();
        clearButton = new javax.swing.JButton();
        volume = new javax.swing.JSlider();
        queueLabel = new javax.swing.JLabel();
        backPanel = new javax.swing.JScrollPane();
        playlist = new javax.swing.JList<>();
        nowPlaying = new java.awt.Label();
        playButton = new javax.swing.JButton();
        stopButton = new javax.swing.JButton();
        songName = new javax.swing.JLabel();
        song = new javax.swing.JProgressBar();
        library = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        file = new javax.swing.JMenu();
        create = new javax.swing.JMenuItem();
        importSong = new javax.swing.JMenuItem();
        edit = new javax.swing.JMenu();
        clip = new javax.swing.JMenuItem();
        tags = new javax.swing.JMenuItem();
        modify = new javax.swing.JMenu();
        add = new javax.swing.JMenuItem();
        remove = new javax.swing.JMenuItem();
        search = new javax.swing.JMenuItem();
        playMenu = new javax.swing.JMenu();
        selectAll = new javax.swing.JMenuItem();
        help = new javax.swing.JMenu();
        documentation = new javax.swing.JMenuItem();
        volumeUp = new javax.swing.JLabel();
        volumeDown = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        backPanel2.setBackground(new java.awt.Color(51, 51, 51));

        playlistList.setBackground(new java.awt.Color(102, 102, 102));
        playlistList.setModel(new javax.swing.AbstractListModel<String>() {
            final String[] strings = {"Song 1", "Song 2", "Song 3"};

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPane2.setViewportView(playlistList);

        javax.swing.GroupLayout backPanel2Layout = new javax.swing.GroupLayout(backPanel2);
        backPanel2.setLayout(backPanel2Layout);
        backPanel2Layout.setHorizontalGroup(
                backPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(backPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2)
                                .addContainerGap())
        );
        backPanel2Layout.setVerticalGroup(
                backPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                                .addContainerGap())
        );

        playlists.setFont(new Font("Helvetica", 0, 24)); // NOI18N
        playlists.setText("Playlists");

        fadeButton.setFont(new Font("Helvetica", 0, 18)); // NOI18N
        fadeButton.setText("Fade");
        fadeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                fadeButtonActionPerformed(evt);
            }
        });

        scrollPane.setBackground(new Color(51, 51, 51));
        scrollPane.setForeground(new Color(51, 51, 51));


        songsToPlay = new DefaultListModel();
        songQueue.setModel(songsToPlay);
        songQueue.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                    stopPlaying(true);
                    skip = 0;
                    musicFileIndex = songQueue.getSelectedIndex();
                    playThread = new Thread(runnablePlay);
                    playThread.start();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }

        });
        scrollPane.setViewportView(songQueue);


        shuffleButton.setFont(new java.awt.Font("Helvetica", 0, 18)); // NOI18N
        shuffleButton.setText("Shuffle");
        shuffleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                shuffleButtonActionPerformed(evt);
            }
        });

        queueLabel.setFont(new java.awt.Font("Helvetica", 0, 24)); // NOI18N
        queueLabel.setText("  Current Queue");

        playlist.setBackground(new Color(153, 153, 153));
        playlist.setBorder(null);
        playlist.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        playlist.setForeground(new java.awt.Color(255, 255, 255));
        playlist.setModel(new javax.swing.AbstractListModel<String>() {
            final String[] strings = internalDB.getPlaylistNames();

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        playlist.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                playlistMouseClicked(evt);
            }
        });
        backPanel.setViewportView(playlist);

        nowPlaying.setFont(new Font("Helvetica", 0, 24)); // NOI18N
        nowPlaying.setForeground(new Color(0, 0, 0));
        //nowPlaying.setName(""); // NOI18N
        nowPlaying.setText("Now Playing:");

        playButton.setIcon(playIcon);
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                playButtonActionPerformed(evt);
            }
        });

        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                volumeControl(volume.getValue());
            }
        });

        volumeControl(volume.getValue());

        stopButton.setIcon(new ImageIcon(getClass().getResource("/resources/images/stopIcon.png"))); // NOI18N
        stopButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                stopButtonActionPerformed();
            }
        });

        library.setIcon(new ImageIcon(getClass().getResource("/resources/images/musicFolderIcon.png"))); // NOI18N
        library.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                libraryActionPerformed(evt);
            }
        });

        backButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/back.png"))); // NOI18N
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        nextButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/next.png"))); // NOI18N
        nextButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextButtonActionPerformed(evt);
            }
        });

        clearButton.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        volumeUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/volume.png"))); // NOI18N
        volumeDown.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/mute.png"))); // NOI18N

        file.setText("File");
        file.setFont(new Font("Helvetica", 0, 14)); // NOI18N

        create.setText("Create");
        create.setToolTipText("");
        create.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                createActionPerformed(evt);
            }
        });

        file.add(create);

        importSong.setText("Import");
        file.add(importSong);

        menuBar.add(file);

        edit.setText("Edit");
        edit.setFont(new Font("Helvetica", 0, 14)); // NOI18N

        clip.setText("Clip");
        edit.add(clip);

        tags.setText("Tags");
        edit.add(tags);

        menuBar.add(edit);

        modify.setText("Modify");
        modify.setFont(new Font("Helvetica", 0, 14)); // NOI18N

        add.setText("Add");
        modify.add(add);

        remove.setText("Remove");
        modify.add(remove);

        search.setText("Search");
        modify.add(search);

        menuBar.add(modify);

        playMenu.setText("Play");
        playMenu.setFont(new Font("Helvetica", 0, 14)); // NOI18N

        selectAll.setText("Select All");
        playMenu.add(selectAll);

        menuBar.add(playMenu);

        help.setText("Help");
        help.setFont(new Font("Helvetica", 0, 14)); // NOI18N

        documentation.setText("Documentation");
        help.add(documentation);

        menuBar.add(help);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap(85, Short.MAX_VALUE)
                                                .addComponent(song, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(backPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(backPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(79, 79, 79)
                                                .addComponent(nowPlaying, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(songName, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(24, 24, 24)
                                                .addComponent(playlists, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(122, 122, 122)
                                                .addComponent(queueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(170, 170, 170)
                                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(26, 26, 26)
                                                                .addComponent(volumeDown, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(0, 0, 0)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(volume, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addGap(12, 12, 12)
                                                                                .addComponent(volumeUp))
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(shuffleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(8, 8, 8)
                                                                                        .addComponent(fadeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(30, 30, 30)
                                                                                        .addComponent(library, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(nextButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                                                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(26, 26, 26)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(songName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(nowPlaying, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(16, 16, 16)
                                                .addComponent(song, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(16, 16, 16)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(volumeUp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(volume, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
                                                        .addComponent(volumeDown, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(nextButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(1, 1, 1)))))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(playlists, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(backPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(backPanel)))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(12, 12, 12)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(shuffleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(fadeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(library, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(33, 33, 33)
                                                .addComponent(queueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(clearButton, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(74, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>

    private void stopPlaying(boolean clearSong) {
        //stops audio line
        if (player != null) {
            Player p = player;
            player = null;
            p.close();
            playButton.setIcon(playIcon);
            if (clearSong) {
                songName.setText("");
                song.setValue(0);
            }
        }
    }

    private void createActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void playlistMouseClicked(MouseEvent evt) {
        // TODO add your handling code here:
        //if (evt.getButton() == 1) {
            String p_name = playlist.getModel().getElementAt(playlist.getSelectedIndex());
            DBTools db = new DBTools();
            String[][] pid = db.getPlaylistID(p_name);
            String id = "";
            for (String[] r : pid) { for (String s : r) {id = s;}}
            String[][] songs = db.getSongPlaylist(id);
            String[] songNames = new String[songs.length];
            for (int i = 0; i < songs.length; i++) {
                songNames[i] = songs[i][2];
            }
            for (String name : songNames) {System.out.println(name);} //TODO: Make show in GUI

        playlistList.setModel(new javax.swing.AbstractListModel<String>() {
            final String[] strings = songNames;

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        jScrollPane2.setViewportView(playlistList);

    }

    private void libraryActionPerformed(java.awt.event.ActionEvent evt){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "git/AMP/songs"));
        fileChooser.setDialogTitle("Select Music");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("AAC files", "aac"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Wav files", "wav"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
        fileChooser.setMultiSelectionEnabled(true);
        if (fileChooser.showOpenDialog(library) == JFileChooser.APPROVE_OPTION) {
            Collections.addAll(musicFiles, fileChooser.getSelectedFiles());
            updateQueue();
        }
        //update GUi

    }


    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //code for play button
        if (playButton.getIcon() == playIcon) {
            playThread = new Thread(runnablePlay);
            playThread.start();
        } else {
            player.isComplete();
            try {
                skip = totalLength - fileInputStream.available();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            stopPlaying(false);
        }
    }

    private void stopButtonActionPerformed(){
        //code for stop button
        stopPlaying(true);
    }


    private void shuffleButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //code for shuffle button
        Collections.shuffle(musicFiles, new Random());
        updateQueue();
    }

    private void fadeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        //code for fade button
        Runnable fadeRunnable = new Runnable() {
            @Override
            public void run() {
                float decrease = currentVolume / 20; //slow down every quarter second by 1/20 for five seconds
                float origVolume = currentVolume;
                while (currentVolume > 0) {
                    volumeControl(currentVolume - decrease);
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException ignored) {

                    }
                }
                stopButtonActionPerformed();
                volumeControl(origVolume);
            }
        };
        Thread t = new Thread(fadeRunnable);
        t.start();
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt){
        //code for back button
        if (player != null) {
            stopPlaying(true);
            skip = 0;
            musicFileIndex--;
            if (musicFileIndex < 0) {
                musicFileIndex = musicFiles.size()-1;
            }
            playThread = new Thread(runnablePlay);
            playThread.start();
        }
    }

    private void nextButtonActionPerformed(java.awt.event.ActionEvent evt){
        //code for next button
        if (player != null) {
            stopPlaying(true);
            skip = 0;
            musicFileIndex++;
            if (musicFileIndex >= musicFiles.size()) {
                musicFileIndex = 0;
            }
            playThread = new Thread(runnablePlay);
            playThread.start();
        }
    }

    private void clearButtonActionPerformed(ActionEvent evt) {
        //code for clear button
        musicFiles.clear();
        updateQueue();
    }


    public void volumeControl(float volume) {
        //code for volume
        currentVolume = volume;
        if (player != null) {
            float value = volume/100.0f;
            float dB = (float)(Math.log(value)/Math.log(10.0)*20.0);
            player.setVolume(dB);
        }

    }

    private void updateQueue() {
        //code that updates the current queue
        songsToPlay.clear();
        for (File f : musicFiles) {
            String filename = f.getName();
            int index = filename.lastIndexOf(".");
            String displayName = filename;
            if (index > 0) {
                displayName = displayName.substring(0, index);
            }
            songsToPlay.addElement(displayName);
        }
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold default state="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AMPGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AMPGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AMPGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AMPGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                AMPGUI amp = new AMPGUI();
                amp.setVisible(true);
                amp.setMinimumSize(new Dimension(1000, 400));
            }
        });
    }
    //</editor-fold>
    //</editor-fold>


    Runnable runnablePlay = new Runnable() {
         @Override
         public void run() {
             //while there are songs in the queue, play
             for (; musicFileIndex < musicFiles.size(); musicFileIndex++) {
                 if (!playNextSong())
                     break;
             }
         }
     };

     private boolean playNextSong() {
         if (musicFileIndex < 0 || musicFileIndex >= musicFiles.size())
             return false;
         myFile = musicFiles.get(musicFileIndex);
         boolean played = false;
         Timer timer = new Timer(1000, new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 try {
                     song.setValue((int) (totalLength-fileInputStream.available()));
                 } catch (IOException ex) {
                     ex.printStackTrace();
                 }
             }
         });
         timer.setRepeats(true);
         try {
             fileInputStream = new FileInputStream(myFile);
             totalLength = fileInputStream.available();
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             player = new Player(bufferedInputStream);
             fileInputStream.skip(skip);
             playButton.setIcon(pauseIcon);
             String displayName = myFile.getName();
             int index = displayName.lastIndexOf(".");
             if (index > 0) {
                 displayName = displayName.substring(0,index);
             }
             songName.setText(displayName);
             song.setMinimum(0);
             song.setMaximum((int) totalLength);
             timer.start();
             player.play();//This starts playing the selected music file
             played = player != null;
             stopPlaying(played);

         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (JavaLayerException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         } finally {
             timer.stop();
         }
         return played;
     }

    // Variables declaration - do not modify
    private javax.swing.JMenuItem add;
    private javax.swing.JButton backButton;
    private javax.swing.JScrollPane backPanel;
    private javax.swing.JPanel backPanel2;
    private javax.swing.JMenuItem clip;
    private javax.swing.JMenuItem create;
    private javax.swing.JButton clearButton;
    private javax.swing.JMenuItem documentation;
    private javax.swing.JMenu edit;
    private javax.swing.JButton fadeButton;
    private javax.swing.JMenu file;
    private javax.swing.JMenu help;
    private javax.swing.JMenuItem importSong;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton library;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu modify;
    private javax.swing.JButton nextButton;
    private java.awt.Label nowPlaying;
    private javax.swing.JButton playButton;
    private javax.swing.JMenu playMenu;
    private javax.swing.JList<String> playlist;
    private javax.swing.JList<String> playlistList;
    private javax.swing.JLabel playlists;
    private javax.swing.JLabel queueLabel;
    private javax.swing.JList<String> songQueue;
    private javax.swing.JMenuItem remove;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JMenuItem search;
    private javax.swing.JMenuItem selectAll;
    private javax.swing.JToggleButton shuffleButton;
    private javax.swing.JProgressBar song;
    private javax.swing.JLabel songName;
    private javax.swing.JButton stopButton;
    private javax.swing.JMenuItem tags;
    private javax.swing.JSlider volume;
    private javax.swing.JLabel volumeDown;
    private javax.swing.JLabel volumeUp;
    // End of variables declaration
}


