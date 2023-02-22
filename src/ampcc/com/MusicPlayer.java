package ampcc.com;
//import global packages from jlayer-1.0.1.jar
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

//import the necessary packages provided by java and javax.
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
    JFrame frame;
    JLabel songNameLbl = new JLabel();
    ImageIcon frameIcon = new ImageIcon(getClass().getResource("/resources/images/musicIcon.png"));
    //ImageIcon playBtnIcon = new ImageIcon(getClass().getResource("/resources/images/playButton.png"));
    JButton selectBtn = new JButton("Select Music");
    //JButton playBtn = new JButton("Play");
    JButton playBtn = new JButton(new ImageIcon(getClass().getResource("/resources/images/playButton.png")));
    JButton pauseBtn = new JButton(new ImageIcon(getClass().getResource("/resources/images/pauseButton.png")));
    JButton resumeBtn = new JButton("Resume");
    JButton stopBtn = new JButton(new ImageIcon(getClass().getResource("/resources/images/stopIcon.png")));
/*    JButton shuffleBtn = new JButton(new ImageIcon(getClass().getResource("/resources/images/shuffleIcon.png")));*/
    JFileChooser fileChooser;
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;
    File myFile = null;
    String filename;
    String filePath;
    long totalLength;//keep this individual
    long pause;//keep this individual
    Player player;//from jlayer-1.0.1.jar
    Thread playThread;//keep this individual
    Thread resumeThread;//keep this individual

    //The Constructor of class MusicPlayer
    // Must add in the Voids and Runnables
    public MusicPlayer(){
        prepareGUI();
        addActionEvents();
        playThread = new Thread(runnablePlay);
        resumeThread = new Thread(runnableResume);

    }

    //This sets up the GUI for the MusicPlayer
    public void prepareGUI(){
        frame = new JFrame();

        frame.setTitle("AMP - Amplified Music Player");
        frame.setIconImage(frameIcon.getImage()); //adds icon in JFrame
        frame.getContentPane().setLayout(null); // the Layout is null for now
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setSize(440,200);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //This places the buttons into position and added into the JFrame.
        selectBtn.setBounds(160,10,110,30);
        frame.add(selectBtn);

        songNameLbl.setBounds(100,50,300,30);
        frame.add(songNameLbl);

        playBtn.setBounds(30,110,100,30);
        frame.add(playBtn);

        pauseBtn.setBounds(120,110,100,30);
        frame.add(pauseBtn);

        resumeBtn.setBounds(210,110,100,30);
        frame.add(resumeBtn);

/*        stopBtn.setBounds(210,110,100,30);
        frame.add(stopBtn);*/

        stopBtn.setBounds(300,110,100,30);
        frame.add(stopBtn);

/*        shuffleBtn.setBounds(300,110,100,30);
        frame.add(shuffleBtn);*/

    }
    //The added Action Listener to each button
    public void addActionEvents(){
        selectBtn.addActionListener(this);
        playBtn.addActionListener(this);
        pauseBtn.addActionListener(this);
        resumeBtn.addActionListener(this);
        stopBtn.addActionListener(this);
    }

    //This Action Event selects mp3 and wav files from a Dialog Window
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == selectBtn){
            fileChooser = new JFileChooser();
            //fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Music"));
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home") + System.getProperty("file.separator")+ "Music"));
            fileChooser.setDialogTitle("Select Music");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Wav files","wav"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files","mp3"));
            if(fileChooser.showOpenDialog(selectBtn)==JFileChooser.APPROVE_OPTION){
                myFile=fileChooser.getSelectedFile();
                filename=fileChooser.getSelectedFile().getName();
                filePath=fileChooser.getSelectedFile().getPath();
            }
        }
        //If click Play Button than this starts the Play Thread
        if(e.getSource() == playBtn){
            playThread.start();
            songNameLbl.setText("Now playing : " + filename);
        }
        //If click Pause Button than this pauses the Player's loaded file
        if(e.getSource() == pauseBtn){
            //code for pause button
            if(player != null){
                try {
                    pause = fileInputStream.available();
                    player.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }

        //If click Resume Button than this starts the Resume Thread
        // hence allows the current music file to continue
        if(e.getSource() == resumeBtn){
            //starting resume thread
            resumeThread.start();
        }
        //If click Stop Button than this stops the current music file
        if(e.getSource() == stopBtn){
            //code for stop button
            if(player != null){
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
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                totalLength = fileInputStream.available();
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

    //This starts Playing the selected file music
    // For the Resume Button
    Runnable runnableResume = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(myFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                fileInputStream.skip(totalLength-pause);
                player.play();
            } catch (FileNotFoundException evt) {
                evt.printStackTrace();
            } catch (JavaLayerException evt) {
                evt.printStackTrace();
            } catch (IOException evt) {
                evt.printStackTrace();
            }
        }
    };
}