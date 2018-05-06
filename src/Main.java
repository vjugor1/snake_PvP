import javax.swing.*;

/**
 * Created by infuntis on 15/01/17.
 */
public class Main extends JFrame {

    public Main(){
        setTitle("Змейка");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(960,975);
        setLocation(400,100);
        add(new ViewGameField());
        setVisible(true);
    }

    public static void main(String[] args) {
        Main mw = new Main();
    }
}

