import java.util.*;

public abstract class Player {

    int score;

    public Player() {
        score = 0;
    }

    public abstract void makeMove(Board board);

    public abstract void paraDrop(Board board);

    public abstract void blitz(Board board);

    public int getScore() {
        return score;
    }

}
