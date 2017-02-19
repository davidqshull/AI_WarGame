import java.util.*;

public class MinimaxPlayer extends Player {

    public MinimaxPlayer(String name) {
        super(name);
    }

    public void makeMove(Board board) {
        int start = (int) System.nanoTime()/1000;

        // for() {
        //
        // }

        incrementMoves();
        int end = (int) System.nanoTime()/1000;
        super.addToMoveTime((end-start));
    }

    public void paraDrop(Board board) {

    }

    public void blitz(Board board) {

    }

    public int evaluate(Player opponent) {
        return super.getScore() - opponent.getScore();
    }
}
