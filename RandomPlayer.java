import java.util.*;

public class RandomPlayer extends Player {

    public RandomPlayer() {

    }

    public void makeMove(Board board) {
        int moveType = new Random().nextInt(1);
        if(moveType == 0) {
            paraDrop(board);
        }
        else {

        }
    }

    public void paraDrop(Board board) {
        Square sq = randomSquare(board);
        sq.setOwner(this);
        board.setSquare(sq, sq.getX(), sq.getY());
    }

    public void blitz(Board board) {
        
    }

    private Square randomSquare(Board board) {
        int square = new Random().nextInt(25);
        int x = square / 5;
        int y = square % 5;
        Square sq = board.getGrid().get(x).get(y);
        if(sq.isOccupied())
            return randomSquare(board);
        return sq;
    }

}
