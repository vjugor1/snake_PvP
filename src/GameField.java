public class GameField {
    /* Map sizes must be entered there - as array sizes */
    int map_cells[66][67];

    /* Snake array for several players */
    Snake snake[];


    /* Cultural constant for modelling battle ground
    * 2 at the beginning stand for the cells*/
    int CELL_FILLED = 20;
    int CELL_EMPTY = 21;
    int CELL_FRESH_MEAT = 22;
    /* nothing at the beginning stands for the CHECK results */
    int CHECK_RES_CONTINUE = 0;
    int CHECK_RES_END = 1;
    int CHECK_RES_PAUSED_OR_MENU = 2;


    public GameField Move(){};
    public int CheckSnakeState(){}; //*************8

}
