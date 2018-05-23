import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
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
    int prevDir = RIGHT; // for operating self-killing
    List<Integer> body = new ArrayList<Integer>();
    private GameField gameField;
    public class Point{
        int x, y;
        public Point(){
            this.x = 0;
            this.y = 0;
        }
    }
    int GetDir(){
        return this.dir;
    }
    int GetPrevDir(){
        return this.prevDir;
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
    public Point GetDxDyDir(int dir){
        Point dPoint = new Point();
        if (dir == this.RIGHT) {
            dPoint.x = GameField.DOT_SIZE;
            dPoint.y = 0;

        }
        if (dir == this.LEFT) {
            dPoint.x = -GameField.DOT_SIZE;
            dPoint.y = 0;
        }
        if (dir == this.UP) {
            dPoint.x = 0;
            dPoint.y = -GameField.DOT_SIZE;
        }
        if (dir == this.DOWN) {
            dPoint.x = 0;
            dPoint.y = GameField.DOT_SIZE;
        }
        return dPoint;

    }
    Point GetBodyElCoords(int num){
        Point res = new Point();
        if (num >= this.GetBodyLen()) {
            VerifyError e = new VerifyError();
            try {
                throw e;
            } finally {
                System.err.println("GetBodyElCoords(): 'num' >= body.size()");
                e.printStackTrace();

            }
        }
        else{
            int currX = this.GetHeadX();
            int currY = this.GetHeadY();
            for (int j = this.GetBodyLen() - 1; j > num ; j--)
            {
                int direction = this.GetBodyEl(j);
                int dx = 0;
                int dy = 0;
                dx = this.GetDxDyDir(direction).x;
                dy = this.GetDxDyDir(direction).y;
                currX += dx;
                currY += dy;
            }
            res.x = currX;
            res.y = currY;
        }

        return res;
    }

    void PrintBody(){
        for (int i = 0; i < this.GetBodyLen(); i++){
            System.out.print(this.GetBodyEl(i) +" ");
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
    void SetPrevDir(int prevDir){
        if ((prevDir != this.RIGHT) && (prevDir != this.LEFT) && (prevDir != this.UP) && (prevDir != this.DOWN)) {
            VerifyError e = new VerifyError();
            try{
                throw e;
            }finally {
                System.err.println("SetPrevDir: invalid 'prevDir' value");
                e.printStackTrace();

            }
        }
        this.prevDir = prevDir;
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
        this.SetPrevDir(dir);
        this.SetHead(xHead, yHead);
        this.SetInGame(true);
    }



    //int HEAD = 3666;
// check for collision there
    public void move() {
        this.SetFed(true);
        int dx = 0, dy = 0;
        dx = this.GetDxDyDir(dir).x;
        dy = this.GetDxDyDir(dir).y;
        Point tmp = this.GetBodyElCoords(0);
        //this.SetDir(dir);
        //next cell operating
        if (((this.GetHeadX() - dx) > GameField.MAX_X) || ((this.GetHeadX() - dx) < 0) || ((this.GetHeadY() - dy) > GameField.MAX_Y) || ((this.GetHeadY() - dy) < 0)){
            this.SetInGame(false);
            this.gameField.SetWinnerScore(this.GetBodyLen());
            return;
        }

        if (this.gameField.GetCellValue(this.GetHeadX() - dx, this.GetHeadY() - dy) == GameField.CELL_FILLED) {
            //this.SetHead(this.GetHeadX() - dx, this.GetHeadY() - dy);
            this.SetInGame(false);
            this.gameField.SetWinnerScore(this.GetBodyLen());
        }

        if (this.gameField.GetCellValue(this.GetHeadX() - dx, this.GetHeadY() - dy) == GameField.CELL_FOOD){
            this.gameField.SetCell(this.GetHeadX() - dx, this.GetHeadY() - dy, GameField.CELL_EMPTY);
            //x -= dx;
            //y -= dy;
            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11 when technique consumes xnx he jumps

            body.add(0, this.GetBodyEl(0));
            //System.out.println("I AM GOING TO DECREASE NUMBER OF POISON AND VODKA");
            int currFPNum = this.gameField.GetFoodPoisonNum();
            this.gameField.SetFoodPoisonNum(currFPNum - 1);
            //this.SetInGame(true);
            this.SetFed(false);
            //this.SetHead(this.GetHeadX() - dx, this.GetHeadY() - dy);
        }
        if (gameField.GetCellValue(this.GetHeadX() - dx, this.GetHeadY() - dy) == GameField.CELL_POISON){
            gameField.SetCell(this.GetHeadX() - dx, this.GetHeadY() - dy, GameField.CELL_EMPTY);
            //x -= dx;
            //y -= dy;
            if (this.GetBodyLen() == 1){
                this.SetInGame(false);
                this.gameField.SetWinnerScore(this.GetBodyLen());
            }
            else{
                this.gameField.SetCell(tmp.x, tmp.y, GameField.CELL_EMPTY);
                body.remove(0);
                tmp = this.GetBodyElCoords(0);
                this.gameField.SetCell(tmp.x, tmp.y, GameField.CELL_EMPTY);
                body.remove(0);
                if (body.size() > 0) {
                    body.add(0, this.GetBodyEl(0));
                }else
                {
                    body.add(dir);
                }
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
            this.gameField.SetCell(tmp.x, tmp.y, GameField.CELL_EMPTY);
            body.remove(0);
            body.add(dir);
            this.SetHead(this.GetHeadX() - dx, this.GetHeadY() - dy);
            //this.SetInGame(true);
        }

        this.gameField.SetSnakes(this.gameField.snakes, true);
        this.SetPrevDir(dir);
        //this.PrintBody();
    }

    /* get dir, set new head position, according to input value dir
     * for i in range(body.len() , 1):
     *      body[i] = body[i+1]*/
    /* corresponding to next head position we will check if it is a food or wall or other snake.
    * cut the end, paste new dir to first element
    * */
}
