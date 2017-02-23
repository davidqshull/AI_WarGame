import java.util.*;

public class Move {

    String type;
    Square start;
    Square end;
    int score;

    public Move(String type, Square start, Square end) {
        this.type = type;
        this.start = start;
        this.end = end;
        this.score = 0;
    }

    public Move(String type, Square start) {
        this.type = type;
        this.start = start;
        this.score = 0;
    }

    public String getType() {
        return type;
    }

    public Square getStart() {
        return start;
    }

    public Square getEnd() {
        return end;
    }

    public int getScore() {

    }
}
