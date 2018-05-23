import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class ViewWindow {
    JFrame frame;
    JPanel pl1Panel;
    JButton bttn;
    JTextField nameField;
    ViewGameField VGF;
    public class ActionListenerForButton implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event) {
            int snakesInGameCnt = 0;
            for (int i = 0; i < VGF.gameField.GetSnakesNum(); i++) {
                if (VGF.gameField.GetSnake(i).inGame == true){
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


                    out.print(tmp);
                    out.close();
                    sock.getOutputStream().write(VGF.gameField.GetWinnerScore());
                    //System.out.println(sock.getInputStream().read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try{
                    sock.close();
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
