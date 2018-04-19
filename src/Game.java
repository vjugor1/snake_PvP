public class Game {
    GameField gameField;
    int time;
    int state;

    /* Pack of methods for game operating and monitoring*/
    public void startGame(){};
    public void PauseGame(){};
    public void EndGame(){}; // private
    public int GetState(){
        return state;
    };
    public int GetTime(){
        return time;
    };

    /* Cultural int constants to operate with game state
     *  1 at the beginning stands for game state */
    int GAME_MENU = 10; // initial, menu for view
    int GAME_EXECUTING = 11;
    int GAME_PAUSED = 12;

}
