import java.util.*;
public class Snake {
    /* cultural constants for body elements */
    final int RIGHT = 0;
    final int LEFT = 1;
    final int UP = 2;
    final int DOWN = 3;
    boolean inGame = true;
    boolean fed = true;
    int x,y; //head's coordinates
    int dir = RIGHT;
    List<Integer> body = new ArrayList<Integer>();
    private GameField gameField;
    int GetDir(){
        return this.dir;
    }
    int GetHeadX(){
        return this.x;
    }
    int GetHeadY(){
        return this.y;
    }
    int GetBodyEl(int i){
        return this.body.get(i);
    }
    int GetBodyLen(){
        return body.size();
    }
    boolean GetFed(){
        return this.fed;
    }
    void PrintBody(){
        for (int i = 0; i < body.size(); i++){
            System.out.print(body.get(i) +" ");
        }
        System.out.println(" ");
    }
    void SetDir(int direction){
        if ((direction != this.RIGHT) && (direction != this.LEFT) && (direction != this.UP) && (direction != this.DOWN)) {
            VerifyError e = new VerifyError();
            try{
                throw e;
            }finally {
                System.err.println("setDir: invalid 'direction' value");
                e.printStackTrace();

            }
        }
        this.dir = direction;
    }
    void SetFed(boolean b){
        this.fed = b;
    }
    void SetInGame(boolean bool){
        this.inGame = bool;
    }

    void SetHead(int newX, int newY){
        this.x = newX;
        this.y = newY;
    }


    /* body[] explanation proceeded below
     * let us consider body[i], where i != 0. let, for instance, body[i] = RIGHT.
     * body[i] = RIGHT -- it means that previous piece of body is located RIGHT
     * with respect to ith one.
     * such approach provides us with an opportunity to easily model move(DIR) method*/

    public Snake (int xHead, int yHead, int len, int dir, GameField gameField) {
        for (int i = 0; i < len; i++) {
            body.add(dir);
        }
        this.gameField = gameField;
        this.SetDir(dir);
        this.SetHead(xHead, yHead);
        this.SetInGame(true);
    }



    //int HEAD = 3666;
// check for collision there
    public void move() {
        this.SetFed(true);
        int dx = gameField.DOT_SIZE, dy = 0;
        dx = gameField.GetDxDyDir(dir).x;
        dy = gameField.GetDxDyDir(dir).y;
        //this.SetDir(dir);
        //next cell operating
        if (((this.GetHeadX() - dx) > this.gameField.MAX_X) || ((this.GetHeadX() - dx) < 0) || ((this.GetHeadY() - dy) > this.gameField.MAX_Y) || ((this.GetHeadY() - dy) < 0)){
            this.SetInGame(false);
            System.out.println("I am trying to end this game");
            return;
        }

        if (gameField.GetCellValue(this.GetHeadX() - dx, this.GetHeadY() - dy) == gameField.CELL_FILLED) {
            //this.SetHead(this.GetHeadX() - dx, this.GetHeadY() - dy);
            this.SetInGame(false);

        }

        if (gameField.GetCellValue(this.GetHeadX() - dx, this.GetHeadY() - dy) == gameField.CELL_FOOD){
            gameField.SetCell(this.GetHeadX() - dx, this.GetHeadY() - dy, gameField.CELL_EMPTY);
            //x -= dx;
            //y -= dy;
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11 when technique consumes xnx he jumps

            body.add(0, body.get(0));
            //System.out.println("I AM GOING TO DECREASE NUMBER OF POISON AND VODKA");
            int currFPNum = this.gameField.GetFoodPoisonNum();
            this.gameField.SetFoodPoisonNum(currFPNum - 1);
            //this.SetInGame(true);
            this.SetFed(false);
            //this.SetHead(this.GetHeadX() - dx, this.GetHeadY() - dy);
        }
        if (gameField.GetCellValue(this.GetHeadX() - dx, this.GetHeadY() - dy) == gameField.CELL_POISON){
            gameField.SetCell(this.GetHeadX() - dx, this.GetHeadY() - dy, gameField.CELL_EMPTY);
            //x -= dx;
            //y -= dy;
            if (body.size() == 1){
                this.SetInGame(false);
            }
            else{
                body.remove(0);
                body.remove(0);
                body.add(0, body.get(0));
                //this.SetInGame(true);
            }
            int currFPNum = this.gameField.GetFoodPoisonNum();
            this.gameField.SetFoodPoisonNum(currFPNum - 1);
            this.SetFed(false);
            //this.SetHead(this.GetHeadX() - dx, this.GetHeadY() - dy);
        }
        if (gameField.GetCellValue(this.GetHeadX() - dx, this.GetHeadY() - dy) == gameField.CELL_EMPTY){
            //x -= dx;
            //y -= dy;
            body.remove(0);
            body.add(dir);
            this.SetHead(this.GetHeadX() - dx, this.GetHeadY() - dy);
            //this.SetInGame(true);
        }

        this.gameField.SetSnakes(this.gameField.snakes);
        this.PrintBody();
    }

    /* get dir, set new head position, according to input value dir
     * for i in range(body.len() , 1):
     *      body[i] = body[i+1]*/
    /* corresponding to next head position we will check if it is a food or wall or other snake.
    * cut the end, paste new dir to first element
    * */
}
