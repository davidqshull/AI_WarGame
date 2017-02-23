import java.util.*;

public class MinimaxPlayer extends Player {

    public MinimaxPlayer(String name) {
        super(name);
    }

    public void makeMove(Board board) {
        int start = (int) System.nanoTime()/1000;
        Move m = minimax(board, 3);
        incrementMoves();
        int end = (int) System.nanoTime()/1000;
        super.addToMoveTime((end-start));
    }

    public void paraDrop(Board board) {

    }

    public void blitz(Board board) {

    }

    public int evaluate(Player opponent, Board board) {
        return super.getScore() / opponent.getScore();
    }

    public Move minimax(Board board, int depth) {
        
        ArrayList<Move> moves = createMoves(getBlitzAbleSquares(), getParadropAbleSquares(board));
        Board tempBoard = new Board(board);
    }

    public ArrayList<Move> createMoves(ArrayList<Square> blitzes, ArrayList<Square> paradrops) {
        ArrayList<Move> moves = new ArrayList<>();
        for(Square sq: blitzes) {
            HashMap<String, Square> unoccupiedNeighbors = sq.getUnoccupiedNeighbors();
            List<String> keys = new ArrayList<String>(unoccupiedNeighbors.keySet());
            for(String key: keys) {
                Square temp = unoccupiedNeighbors.get(key);

            }
        }
    }
}
