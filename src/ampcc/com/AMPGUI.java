/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ampcc.com;

//imports
//import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.FloatControl;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.FloatControl;
/*
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Arrays;

 */


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
    private int numSongs;

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

        DBTools t = new DBTools();

        backPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        playlistList = new javax.swing.JList<>();
        //for (String pl : db.getPlaylistNames()) {playlistList.add(new javax.swing.JLabel(pl));}
        playlists = new javax.swing.JLabel();
        fadeButton = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        //songQueue = new javax.swing.JList<Song>();
        shuffleButton = new javax.swing.JToggleButton();
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
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
                                .addContainerGap())
        );

        playlists.setFont(new java.awt.Font("Helvetica", 0, 24)); // NOI18N
        playlists.setText("Playlists");

        fadeButton.setFont(new java.awt.Font("Helvetica", 0, 18)); // NOI18N
        fadeButton.setText("Fade");
        fadeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fadeButtonActionPerformed(evt);
            }
        });

        scrollPane.setBackground(new java.awt.Color(51, 51, 51));
        scrollPane.setForeground(new java.awt.Color(51, 51, 51));

        /*
        songQueue.setModel(new javax.swing.AbstractListModel<String>() {
            final String[] strings = {};

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });


        scrollPane.setViewportView(songQueue);

         */

        shuffleButton.setFont(new java.awt.Font("Helvetica", 0, 18)); // NOI18N
        shuffleButton.setText("Shuffle");

        queueLabel.setFont(new java.awt.Font("Helvetica", 0, 24)); // NOI18N
        queueLabel.setText("  Current Queue");

        playlist.setBackground(new java.awt.Color(51, 0, 153));
        playlist.setBorder(null);
        playlist.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N
        playlist.setForeground(new java.awt.Color(255, 255, 255));
        playlist.setModel(new javax.swing.AbstractListModel<String>() {
            final String[] strings = t.getPlaylistNames();

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
        playlist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                playlistMouseClicked(evt);
            }
        });
        backPanel.setViewportView(playlist);

        nowPlaying.setFont(new java.awt.Font("Helvetica", 0, 24)); // NOI18N
        nowPlaying.setForeground(new java.awt.Color(0, 0, 0));
        //nowPlaying.setName(""); // NOI18N
        nowPlaying.setText("Now Playing:");

        playButton.setIcon(playIcon);
        playButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
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

        stopButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/stopIcon.png"))); // NOI18N
        stopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopButtonActionPerformed();
            }
        });

        library.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/images/musicFolderIcon.png"))); // NOI18N
        library.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                libraryActionPerformed(evt);
            }
        });

        file.setText("File");
        file.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        create.setText("Create");
        create.setToolTipText("");
        create.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createActionPerformed(evt);
            }
        });
        file.add(create);

        importSong.setText("Import");
        file.add(importSong);

        menuBar.add(file);

        edit.setText("Edit");
        edit.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        clip.setText("Clip");
        edit.add(clip);

        tags.setText("Tags");
        edit.add(tags);

        menuBar.add(edit);

        modify.setText("Modify");
        modify.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        add.setText("Add");
        modify.add(add);

        remove.setText("Remove");
        modify.add(remove);

        search.setText("Search");
        modify.add(search);

        menuBar.add(modify);

        playMenu.setText("Play");
        playMenu.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

        selectAll.setText("Select All");
        playMenu.add(selectAll);

        menuBar.add(playMenu);

        help.setText("Help");
        help.setFont(new java.awt.Font("Helvetica", 0, 14)); // NOI18N

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
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(13, 13, 13)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(playlists, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(backPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(backPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(164, 164, 164)
                                                .addComponent(nowPlaying, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(songName, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 6, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(song, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(layout.createSequentialGroup()
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                        .addGap(38, 38, 38)
                                                                        .addComponent(playButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                        .addComponent(stopButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 106, Short.MAX_VALUE)
                                                                        .addComponent(fadeButton)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(shuffleButton)
                                                                        .addGap(10, 10, 10)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGap(128, 128, 128)
                                                                .addComponent(queueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addContainerGap(0, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(volume, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(78, 78, 78))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                                .addComponent(library, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(33, 33, 33))))))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(library, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(songName, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(nowPlaying, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(song, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(volume, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(playlists, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(playButton, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                                                        .addComponent(stopButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                .addComponent(fadeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addComponent(shuffleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                                                .addComponent(queueLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(scrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap(59, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(40, 40, 40)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(backPanel)
                                                        .addComponent(backPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>

    //songQueue = new Song[1];
    //numSongs = 0;

    private void stopPlaying() {
        if (player != null) {
            player.close();
            playButton.setIcon(playIcon);
            player = null;
        }
    }

    private void createActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void playlistMouseClicked(java.awt.event.MouseEvent evt) {
        // TODO add your handling code here:
        // get playlist id and its songs
        // populate song list with songs (playlistList?)
        // update screen
    }

    private void libraryActionPerformed(java.awt.event.ActionEvent evt){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Music"));
        fileChooser.setDialogTitle("Select Music");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileNameExtensionFilter("AAC files", "aac"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Wav files", "wav"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
        if (fileChooser.showOpenDialog(library) == JFileChooser.APPROVE_OPTION) {
            stopPlaying();
            myFile = fileChooser.getSelectedFile();
            filename = fileChooser.getSelectedFile().getName();
            filePath = fileChooser.getSelectedFile().getPath();
            skip = 0;
            int index = filename.lastIndexOf(".");
            String displayName = filename;
            if (index > 0) {
                displayName = displayName.substring(0,index);
            }
            songName.setText(displayName);
        }
    }

    private void playButtonActionPerformed(java.awt.event.ActionEvent evt) {
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
            stopPlaying();
        }
    }

    private void stopButtonActionPerformed(){
        //code for stop button
        songName.setText("");
        stopPlaying();
    }

    private void fadeButtonActionPerformed(java.awt.event.ActionEvent evt) {
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



    public void volumeControl(float volume) {
        currentVolume = volume;
        if (player != null) {
            float value = volume/100.0f;
            float dB = (float)(Math.log(value)/Math.log(10.0)*20.0);
            player.setVolume(dB);
        }

    }

    /*
    public void addSong(Song newSong) {
        //if (songQueue.length)
    }

     */

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
                new AMPGUI().setVisible(true);
            }
        });
    }
    //</editor-fold>
    //</editor-fold>


    Runnable runnablePlay = new Runnable() {
         @Override
         public void run() {
             if (myFile == null || !myFile.canRead()) {
                 return;
             }
             //while there are songs in the queue, play
             java.util.List<File> songQueue = Arrays.asList(myFile);
             for (File nextSong : songQueue) {
                 myFile = nextSong;
                 playNextSong();
             }
         }
     };

     private void playNextSong() {
         try {
             fileInputStream = new FileInputStream(myFile);
             totalLength = fileInputStream.available();
             BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
             player = new Player(bufferedInputStream);
             fileInputStream.skip(skip);
             playButton.setIcon(pauseIcon);
             player.play();//This starts playing the selected music file
             //songName.setText("");
             stopPlaying();

         } catch (FileNotFoundException e) {
             e.printStackTrace();
         } catch (JavaLayerException e) {
             e.printStackTrace();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }

    // Variables declaration - do not modify
    private javax.swing.JMenuItem add;
    private javax.swing.JScrollPane backPanel;
    private javax.swing.JPanel backPanel2;
    private javax.swing.JMenuItem clip;
    private javax.swing.JMenuItem create;
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
    // End of variables declaration
}


