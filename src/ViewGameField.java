import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class ViewGameField extends JPanel implements ActionListener {
    GameField gameField = new GameField();
    final int timeTick = 350;
    private int SNAKES_NUM = 1;
    private final int SIZE = 960;
    private final int DOT_SIZE = 48;
    private final int ALL_DOTS = 400;
    private Image dot;
    private Image apple;
    private Image poison;
    private Image gameOver;
    private int StartFoodPoisonNum = 0;
    //private int[] x = new int[ALL_DOTS];
    //private int[] y = new int[ALL_DOTS];
    private Timer timer;

    private void SetSnakes_Num(int num) {
        this.SNAKES_NUM = num;
    }
    private int GetStartFoodPoisonNum(){
        return this.StartFoodPoisonNum;
    }
    private void SetStartFoodPoisonNum(int num){
        this.StartFoodPoisonNum = num;
    }

    public ViewGameField(){
        setBackground(Color.white);
        loadImages();
        InitGame(1, 2);
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

   public void InitGame(int snakesNum, int FPNum) {
       gameField.GenSnakes(snakesNum);
       gameField.SetSnakes(gameField.snakes);
       timer = new Timer(timeTick, this);
       timer.start();
       this.SetStartFoodPoisonNum(FPNum);
       for (int i = 0; i < this.GetStartFoodPoisonNum(); i ++){
           gameField.MakeFoodPoison();
       }
       this.SetStartFoodPoisonNum(FPNum);
   }

    public void loadImages(){
        ImageIcon iia = new ImageIcon("xnx.png");
        apple = iia.getImage();
        ImageIcon iid = new ImageIcon("head_technique.png");
        dot = iid.getImage();
        ImageIcon iip = new ImageIcon( "vodka.png");
        poison = iip.getImage();
        ImageIcon iigO = new ImageIcon("game_over.png");
        gameOver = iigO.getImage();
    }
    public int countInGameSnakes(){
        int countInGameSnakes = 0;
        for (int i = 0; i < this.gameField.snakes.size(); i ++) {
            if (this.gameField.snakes.get(i).inGame){
                countInGameSnakes ++;
            }
        }
        //System.out.print(countInGameSnakes);
        return countInGameSnakes;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.countInGameSnakes() >= 1){
            for (int i = 0; i < gameField.MAX_X; i++){
                for (int j = 0; j < gameField.MAX_Y; j ++){
                    if ((gameField.map_cells[i][j] == gameField.CELL_FILLED) ){
                        g.drawImage(dot, i , j , this);
                    }
                    if (gameField.map_cells[i][j] == gameField.CELL_FOOD){
                        g.drawImage(apple, i , j , this);
                    }
                    if (gameField.map_cells[i][j] == gameField.CELL_POISON){
                        g.drawImage(poison, i , j , this);
                    }
                }
            }
        } else{
            /*String str = "GAME OVER";
            Font f = new Font("Arial", 35, Font.BOLD);
            g.setColor(Color.black);
            g.setFont(f);
            g.drawString(str, 125, SIZE / 2);*/
            setBackground(Color.black);
            g.drawImage(gameOver, 0, 240, this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(this.countInGameSnakes() >= 1){
            for (int i = 0; i < this.gameField.snakes.size(); i++){
                if (this.gameField.snakes.get(i).inGame) {
                    //System.out.println(i+"th is going to make its move");
                    //System.out.println("Head location: x = " + gameField.snakes.get(i).GetHeadX() + ", y = " + gameField.snakes.get(i).GetHeadY());
                    this.gameField.snakes.get(i).move();
                    if (this.gameField.snakes.get(i).GetFed() == false){
                       int tmp = this.GetStartFoodPoisonNum();
                       this.SetStartFoodPoisonNum(tmp + 1);
                    }
                    while (this.gameField.GetFoodPoisonNum() <= this.GetStartFoodPoisonNum()){
                        System.out.println("AM I IN WHILE WTF");
                        this.gameField.MakeFoodPoison();
                    }
                    System.out.println(i+"th snake has made its move");
                    System.out.println("Head location: x = " + gameField.snakes.get(i).GetHeadX() / DOT_SIZE + ", y = " + gameField.snakes.get(i).GetHeadY() / DOT_SIZE);
                    System.out.println(i+"th snake,iGame = "+gameField.snakes.get(i).inGame);
                    /*System.out.println("body elements locations:");
                    for (int j = 0; j < this.gameField.snakes.get(i).GetBodyLen(); j++){
                        System.out.println(j+"th has direction"+this.gameField.snakes.get(i).GetBodyEl(j));
                    }*/
                    System.out.println("Num of food or poison = " + gameField.GetFoodPoisonNum());
                    System.out.println("LEN OF "+i+"th SNAKE = " + this.gameField.snakes.get(i).GetBodyLen());
                }
            }
        }
        repaint();
    }

    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && (gameField.snakes.get(0).GetDir() != gameField.snakes.get(0).LEFT)){
                gameField.snakes.get(0).SetDir(gameField.snakes.get(0).RIGHT);
            }
            if(key == KeyEvent.VK_RIGHT && (gameField.snakes.get(0).GetDir() != gameField.snakes.get(0).RIGHT)){
                gameField.snakes.get(0).SetDir(gameField.snakes.get(0).LEFT);
            }
            if(key == KeyEvent.VK_UP && (gameField.snakes.get(0).GetDir() != gameField.snakes.get(0).UP)){
                gameField.snakes.get(0).SetDir(gameField.snakes.get(0).DOWN);
            }
            if(key == KeyEvent.VK_DOWN && (gameField.snakes.get(0).GetDir() != gameField.snakes.get(0).DOWN)){
                gameField.snakes.get(0).SetDir(gameField.snakes.get(0).UP);
            }

        }
    }

}


