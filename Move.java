import java.util.*;

public class Move {

    Square square;
    int score;

    public Move(Square square, int score) {
        this.square = square;
        this.score = score;
    }

    public Move() {
        this.score = 0;
    }

    public Square getStart() {
        return square;
    }

    public int getScore() {
        return score;
    }
}
