import java.util.*;
//CHANGE DIR ONLY ON TIMER!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
// you can make right left and smth STATIC because it s general for all game fields
public class GameField {
    /* Map sizes must be entered there - as array sizes */
    final static int MAX_X = 960;
    final static int MAX_Y = 960;
    final static int CELLS_NUM = 20;
    final static int DOT_SIZE = MAX_X / CELLS_NUM;
    final static int INIT_SNAKE_SIZE = 3;
    int[][] map_cells = new int[MAX_X][MAX_Y];
    /* Cultural constant for modelling battle ground
    * 2 at the beginning stand for the cells*/
    final static int CELL_FILLED = 20;
    final static int CELL_EMPTY = 21;
    final static int CELL_FOOD = 22;
    final static int CELL_POISON = 23;
    /* nothing at the beginning stands for the CHECK results */
    final int CHECK_RES_CONTINUE = 0;
    final int CHECK_RES_END = 1;
    final int CHECK_RES_PAUSED_OR_MENU = 2;
    private int winnerScore = 0;
    /* Snake list for several players */
    int FOOD_POISON_NUM = 1;
    List<Snake> snakes = new ArrayList<Snake>();
    Snake snakeWinner;
    //List<Boolean> inGameSnakes;
    Random random = new Random();
    public class Point{
        int x, y;
        public Point(){
            this.x = 0;
            this.y = 0;
        }
    }

    void SetWinnerScore(int score){
        this.winnerScore = score;
    }
    int GetWinnerScore(){
        return this.winnerScore;
    }

    public GameField(){
        for (int i = 0; i < MAX_X; i++){
            for (int j = 0; j < MAX_Y; j ++){
                this.SetCell(i, j, CELL_EMPTY);
            }
        }
    }
    public int GetSnakesNum(){
        return this.snakes.size();
    }
    public Snake GetSnake(int num){
        return this.snakes.get(num);
    }

    public void GenSnakes(int snakesNum){
        for (int i = 0; i < snakesNum; i ++) {
            int xInit = (i+2) * DOT_SIZE;//(i * 15 +3 ) * DOT_SIZE % MAX_X;
            int yInit = (i+10) * DOT_SIZE;//(i * 15 +3 ) * DOT_SIZE % MAX_Y;
            int dirInit = new Random().nextInt(4);
            //if (xInit == 0 && yInit == 0){
            dirInit = 3;
            //}
            Snake snake = new Snake(xInit, yInit, INIT_SNAKE_SIZE, dirInit, this);
            snakes.add(snake);
            //inGameSnakes.add(true);
        }
        //System.out.println("I am in GF.GenSnakes. I've got " + this.snakes.size() + " snakes" );
    }

    public int countInGameSnakes(){
        int countInGameSnakes = 0;
        for (int i = 0; i < this.snakes.size(); i ++) {
            if (this.GetSnake(i).inGame){
                countInGameSnakes ++;
            }
        }
        //System.out.print(countInGameSnakes);
        return countInGameSnakes;
    }

    int GetCellValue(int x, int y){
        //System.out.println("hey. at "+x + y + " there is "+ map_cells[x][y]);
       return map_cells[x][y];
    }

    void SetFoodPoisonNum(int nums){
        this.FOOD_POISON_NUM = nums;
    }
    int GetFoodPoisonNum(){
        return this.FOOD_POISON_NUM;
    }
    void SetCell(int x, int y, int value){
        if ((value != this.CELL_EMPTY) && (value != this.CELL_FILLED) && (value != this.CELL_FOOD) && (value != this.CELL_POISON)) {
            VerifyError e = new VerifyError();
            try{
                throw e;
            }finally {
                System.err.println("setCell: invalid 'value' value");
                e.printStackTrace();

            }
        }

        this.map_cells[x][y] = value;
    }




    void SetSnakes(List<Snake> snakes, boolean move){
        if (!move) {
            for (int i = 0; i < MAX_X; i += DOT_SIZE) {
                for (int j = 0; j < MAX_Y; j += DOT_SIZE) {
                    if (this.GetCellValue(i, j) != CELL_POISON && this.GetCellValue(i, j) != CELL_FOOD) {
                        this.SetCell(i, j, CELL_EMPTY);
                    }
                    if (((i >= 0) && (j == 0)) || (j >= 0 && (i == 0)) || ((i >= 0) && (j == (MAX_Y - DOT_SIZE))) || ((i == (MAX_X - DOT_SIZE)) && (j >= 0))) {
                        this.SetCell(i, j, CELL_FILLED);
                    }
                }
            }
        } else{
            for (int i = 0; i < snakes.size(); i++){
                int currX = snakes.get(i).GetHeadX();
                int currY = snakes.get(i).GetHeadY();
                SetCell(currX, currY, CELL_FILLED);
                for (int j = snakes.get(i).body.size() - 1; j > 0 ; j--)
                {
                    //System.out.println("I'm in GameField.SetSnakes. i = " + i + " j = " + j);
                    int direction = snakes.get(i).body.get(j);
                    int dx = 0;
                    int dy = 0;
                    //System.out.println("oh j = " + j);
                    //System.out.println("but bodysize is "+ snakes.get(i).body.size());
                    dx = this.GetSnake(i).GetDxDyDir(direction).x;
                    dy = this.GetSnake(i).GetDxDyDir(direction).y;
                    currX += dx;
                    currY += dy;
                    SetCell(currX, currY, CELL_FILLED);
                }
            }
        }

    }



    public void MakeFoodPoison(){
        int xRand = random.nextInt(MAX_X / DOT_SIZE - 4) + 2;
        int yRand = random.nextInt(MAX_Y / DOT_SIZE - 4) + 2;
        while ((GetCellValue(xRand, yRand) == CELL_FILLED) || (GetCellValue(xRand, yRand) == CELL_POISON) || (GetCellValue(xRand, yRand) == CELL_FOOD)) {
            xRand = random.nextInt(MAX_X / DOT_SIZE - 4) + 2;
            yRand = random.nextInt(MAX_Y / DOT_SIZE - 4) + 2;
        }
        //System.out.println("FIELD IS GOINT TO PLACE");
        int foodOrPoison = random.nextInt(4);
        if (foodOrPoison != 1){
            //System.out.println("FIELD IS GOINT TO PLACE FOOD");
            this.SetCell(xRand * DOT_SIZE, yRand * DOT_SIZE, CELL_FOOD);
        }
        else{
            //System.out.println("FIELD IS GOING TO PLACE POISON");
            this.SetCell(xRand * DOT_SIZE, yRand * DOT_SIZE, CELL_POISON);
        }
        this.SetFoodPoisonNum(this.GetFoodPoisonNum() + 1);

    };
    //public int CheckSnakeState(){}; //*************8

}