import java.util.*;

public class MinimaxPlayer extends Player {

    public MinimaxPlayer(String name) {
        super(name);
    }

    public MinimaxPlayer(Player player) {
        super(player);
    }

    public void makeMove(Board board) {
        long start = System.nanoTime()/1000;
        int[] m = minimax(board, 3, true);
        Square sq = board.getSquare(m[1], m[2]);
        boolean blitzable = false;
        if(sq.getOccupiedNeighbors().size() > 0) {
            for(String k: sq.getOccupiedNeighbors().keySet()) {
                if(sq.getOccupiedNeighbors().get(k).getOwner().equals(this)) {
                    blitzable = true;
                }
            }
        }
        if(blitzable)
            blitz(board, sq);
        else
            paraDrop(board, sq);
        incrementMoves();
        long end = System.nanoTime()/1000;
        super.addToMoveTime((end-start));
    }

    public void paraDrop(Board board) {

    }

    public void paraDrop(Board board, Square sq) {
        sq.setOwner(this);
        board.incrementOccupiedCount();
        board.addOccupiedSquare(sq);
        super.addToScore(sq.getValue());
        super.addOwnedSquare(sq);
        for(String neighborKey: sq.getNeighbors().keySet()) {
            sq.getNeighbors().get(neighborKey).removeUnoccupiedNeighbor(sq);
        }
    }

    public void blitz(Board board) {

    }

    public int blitz(Board board, Square sq) {
        for(String k: sq.getOccupiedNeighbors().keySet()) {
            if(sq.getOccupiedNeighbors().get(k).getOwner().equals(this)) {
                sq.setOwner(this);
                board.addOccupiedSquare(sq);
                board.incrementOccupiedCount();
                addOwnedSquare(sq);
                addToScore(sq.getValue());
                for(String neighborKey: sq.getNeighbors().keySet()) {
                    sq.getNeighbors().get(neighborKey).removeUnoccupiedNeighbor(sq);
                }
                if(sq.getOccupiedNeighbors().size() > 0) {
                    for(String key: sq.getOccupiedNeighbors().keySet()) {
                        Square temp = sq.getOccupiedNeighbors().get(key);
                        if(!temp.getOwner().equals(this)) {
                            Player opponent = temp.getOwner();
                            opponent.subtractFromScore(temp.getValue());
                            addToScore(temp.getValue());
                            temp.setOwner(this);
                            addOwnedSquare(temp);
                            opponent.removeOwnedSquare(temp);
                            //board.addOccupiedSquare(temp);
                        }
                    }
                }
                return 1;
            }
        }
        return 0;
    }

    public int evaluate(Board board) {
        if (board.getUnoccupiedSquares().size() == 0) {
            Player curPlayer = board.getPlayer1().equals(this) ? board.getPlayer1() : board.getPlayer2();
            if(curPlayer.getScore() > board.getOpponent(curPlayer).getScore())
                return 1000;
            else if(curPlayer.getScore() < board.getOpponent(curPlayer).getScore())
                return -1000;
            else
                return 0;
        }
        if(board.getPlayer1().equals(this))
            return board.getPlayer1().getScore() - board.getPlayer2().getScore();
        return board.getPlayer2().getScore() - board.getPlayer1().getScore();
    }

    public int[] minimax(Board board, int depth, boolean maxPlayer) {
        int bestScore = (maxPlayer) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        int currentScore;
        int bestX = -1;
        int bestY = -1;

        if(depth == 0 || board.getUnoccupiedSquares().size() == 0)
            bestScore = evaluate(board);
        else if(maxPlayer) {
            for(Square square: board.getUnoccupiedSquares()) {
                Square sq = new Square(square);
                Board b = new Board(board);
                Player curPlayer = b.getPlayer1().equals(this) ? b.getPlayer1() : b.getPlayer2();
                sq.setOwner(curPlayer);
                b.incrementOccupiedCount();
                b.setSquare(sq, sq.getX(), sq.getY());
                b.addOccupiedSquare(sq);
                curPlayer.addToScore(sq.getValue());
                curPlayer.addOwnedSquare(sq);
                curPlayer.incrementMoves();
                currentScore = minimax(b, depth-1, false)[0];
                if(currentScore > bestScore) {
                    bestScore = currentScore;
                    bestX = sq.getX();
                    bestY = sq.getY();
                }
            }
        }
        else {
            for(Square square: board.getUnoccupiedSquares()) {
                Square sq = new Square(square);
                Board b = new Board(board);
                Player curPlayer = b.getPlayer1().equals(this) ? b.getPlayer1() : b.getPlayer2();
                sq.setOwner(curPlayer);
                b.incrementOccupiedCount();
                b.addOccupiedSquare(sq);
                curPlayer.addToScore(sq.getValue());
                curPlayer.addOwnedSquare(sq);
                curPlayer.incrementMoves();
                currentScore = minimax(b, depth-1, false)[0];
                if(currentScore < bestScore) {
                    bestScore = currentScore;
                    bestX = sq.getX();
                    bestY = sq.getY();
                }
            }
        }
        return new int[] {bestScore, bestX, bestY};
    }
}
