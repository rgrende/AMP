package ampcc.com;
//import global packages from jlayer-1.0.1.jar

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/*
This is a basic Java music player that uses Swing, Awt, io, and JLayer.
There has been minor edification. It will need boolean(s)
and a loop. Since this application only can run one song at a time
must close and open it again.

Sources to help start this project:
https://www.tutorialsfield.com/how-to-play-mp3-file-in-java/

 */
//From MusicPlayer lets implement an ActionListener for the GUI
public class MusicPlayer implements ActionListener {
    //change image on the fly
    private static final String playImage = "/resources/images/playButton.png";
    private static final String pauseImage = "/resources/images/pauseButton.png";
    private static final ImageIcon playIcon = new ImageIcon(MusicPlayer.class.getResource(playImage));
    private static final ImageIcon pauseIcon = new ImageIcon(MusicPlayer.class.getResource(pauseImage));

    JFrame frame;
    JLabel songNameLbl = new JLabel();
    ImageIcon frameIcon = new ImageIcon(getClass().getResource("/resources/images/musicIcon.png"));
    JButton selectBtn = new JButton(new ImageIcon(getClass().getResource("/resources/images/musicFolderIcon.png")));
    JButton playBtn = new JButton(playIcon);
    JButton stopBtn = new JButton(new ImageIcon(getClass().getResource("/resources/images/stopIcon.png")));
    /*    JButton shuffleBtn = new JButton(new ImageIcon(getClass().getResource("/resources/images/shuffleIcon.png")));*/
    JFileChooser fileChooser;
    FileInputStream fileInputStream;
    File myFile = null;
    String filename;
    String filePath;
    long totalLength;//keep this individual
    long skip;//keep this individual
    Player player;//from jlayer-1.0.1.jar
    Thread playThread;//keep this individual

    //The Constructor of class MusicPlayer
    // Must add in the Voids and Runnables
    public MusicPlayer() {
        prepareGUI();
        addActionEvents();
    }

    //This sets up the GUI for the MusicPlayer
    public void prepareGUI() {
        frame = new JFrame();

        frame.setTitle("AMP - Amplified Music Player");
        frame.setIconImage(frameIcon.getImage()); //adds icon in JFrame
        frame.getContentPane().setLayout(null); // the Layout is null for now
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setSize(440, 200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //This places the buttons into position and added into the JFrame.
        selectBtn.setBounds(150, 10, 110, 30);
        frame.add(selectBtn);

        songNameLbl.setBounds(100, 50, 300, 30);
        frame.add(songNameLbl);

        playBtn.setBounds(30, 110, 100, 30);
        frame.add(playBtn);

        stopBtn.setBounds(300, 110, 100, 30);
        frame.add(stopBtn);

/*        shuffleBtn.setBounds(300,110,100,30);
        frame.add(shuffleBtn);*/

    }

    //The added Action Listener to each button
    public void addActionEvents() {
        selectBtn.addActionListener(this);
        playBtn.addActionListener(this);
        stopBtn.addActionListener(this);
    }

    //This Action Event selects mp3 and wav files from a Dialog Window
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == selectBtn) {
            fileChooser = new JFileChooser();
            //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Music"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Music"));
            fileChooser.setDialogTitle("Select Music");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("AAC files", "aac"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Wav files", "wav"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
            if (fileChooser.showOpenDialog(selectBtn) == JFileChooser.APPROVE_OPTION) {
                myFile = fileChooser.getSelectedFile();
                filename = fileChooser.getSelectedFile().getName();
                filePath = fileChooser.getSelectedFile().getPath();
                skip = 0;
                songNameLbl.setText("Now playing : " + filename);
            }
        }
        //If click Play Button than this starts the Play Thread
        if (e.getSource() == playBtn) {
            if (playBtn.getIcon() == playIcon) {
                playBtn.setIcon(pauseIcon);
                playThread = new Thread(runnablePlay);
                playThread.start();
            } else {
                playBtn.setIcon(playIcon);
                player.isComplete();
                if (player != null) {
                    try {
                        skip = totalLength - fileInputStream.available();
                        player.close();
                        player = null;
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }

        //If click Stop Button than this stops the current music file
        if (e.getSource() == stopBtn) {
            //code for stop button
            if (player != null) {
                player.close();
                songNameLbl.setText("");
            }

        }

    }

    //This starts Playing the selected file music
    // For the Play Button
    Runnable runnablePlay = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(myFile);
                totalLength = fileInputStream.available();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                fileInputStream.skip(skip);
                player.play();//This starts playing the selected music file
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
}