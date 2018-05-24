import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

public class ViewWindow {
    private JFrame frame;
    private JPanel pl1Panel;
    private JButton bttn;
    private JTextField nameField;
    private ViewGameField VGF;
    private JTextArea ratingArea;
    private final static int TOP_NUM = 3;
    public class ActionListenerForButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
            int snakesInGameCnt = 0;
            for (int i = 0; i < VGF.gameField.GetSnakesNum(); i++) {
                if (VGF.gameField.GetSnake(i).inGame){
                    snakesInGameCnt ++;
                }
            }
            if (snakesInGameCnt == 0){
                String tmp = nameField.getText();
                Socket sock = null;
                try {
                    sock = new Socket(InetAddress.getLocalHost(), 11111);
                    OutputStream outStream = sock.getOutputStream();
                    PrintWriter out = new PrintWriter(outStream);
                    BufferedReader in = new BufferedReader(new InputStreamReader(sock.getInputStream()));


                    ArrayList<String> rating = new ArrayList<>();
                    String line;



                    //System.out.println("view is going to send players rating");
                    out.print(tmp + ": " + VGF.gameField.GetWinnerScore() + "\n" );
                    out.flush();
                    //System.out.println("view has sent players rating");
                    //System.out.println("goig to recieve num " );
                    //int numRating = in.read();
                    //System.out.println("Got " + numRating);
                    int cnt = 0;
                    System.out.println("BEFORE WHILE");
                    //System.out.println(in.readLine());
                    while (((line = in.readLine()) != null) && (cnt <=5)){
                        cnt ++;
                        rating.add(line);
                        System.out.println("I AM IJ WHIEL");
                    }
                    System.out.println("AFTER WHILE");
                    System.out.println(rating);
                    in.close();
                    out.close();
                    ratingArea.setBounds(480, 980, 450, 230);
                    for (int i = 0; (i < cnt) && (i < TOP_NUM); i++) {
                        if (i > 0) {
                            ratingArea.setText(ratingArea.getText() + "\n" + rating.get(i));
                        }else
                        {
                            ratingArea.setText(ratingArea.getText() + rating.get(i));

                        }
                    }
                    ratingArea.setVisible(true);
                    pl1Panel.add(ratingArea);
                    //System.out.println(sock.getInputStream().read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try{
                    if (sock != null){
                        sock.close();
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    public ViewWindow(){
        frame = new JFrame("Змейка");
        //setTitle("Змейка");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(960,975 + 240);
        //frame.setLocation(200,50);


        VGF = new ViewGameField();
        pl1Panel= new JPanel();
        bttn = new JButton("Submit!");
        nameField = new JTextField("Write your name here");
        ratingArea = new JTextArea();
        VGF.setLayout(null);
        VGF.setBounds(0, 0, 960, 960);
        pl1Panel.setLayout(null);
        pl1Panel.setBounds(5, 975, 480, 220);
        pl1Panel.setBackground(Color.red);
        nameField.setBounds(5, 980, 280, 20);
        bttn.setBounds(290, 980, 100, 20);
        bttn.addActionListener(new ActionListenerForButton());
        pl1Panel.add(bttn);
        pl1Panel.add(nameField);
        frame.add(VGF);
        frame.add(pl1Panel);
        frame.setVisible(true);
    }
}
