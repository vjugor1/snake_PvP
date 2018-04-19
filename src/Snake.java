public class Snake {
    int x,y; //head's coordinates
    int body[];
    /* body[] explanation proceeded below
     * body[0] = HEAD = 3666 - it is head, head is the leader
     * let us consider body[i], where i != 0. let, for instance, body[i] = RIGHT.
     * body[i] = RIGHT -- it means that previous piece of body is located RIGHT
     * with respect to ith one.
     * such approach provides us with an opportunity to easily model move(DIR) method*/




    /* cultural constants for body[] elements */
    int RIGHT = 30;
    int LEFT = 31;
    int UP = 32;
    int DOUWN = 33;
    int HEAD = 3666;

    public void move(int dir){};
    /* get dir, set new head position, according to input value dir
     * for i in range(body.len() , 1):
     *      body[i] = body[i+1]*/
    /* corresponding to next head position we will check if it is a food or wall or other snake.
    * cut the end, paste new dir to first element
    * */
}
