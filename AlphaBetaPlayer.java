/*
David Shull
CSC 380: Artificial Intelligence
Project 2: WarGame

This subclass of Player makes its moves using the minimax adversarial search
algorithm with alpha beta pruning.
*/

import java.util.*;

public class AlphaBetaPlayer extends Player {

    /*
    Invokes the superclass constructor on the given String.
    */
    public AlphaBetaPlayer(String name) {
        super(name);
    }

    /*
    Invokes the superclass copy constructor on the given Player.
    */
    public AlphaBetaPlayer(Player player) {
        super(player);
    }

    /*
    Takes in a Board object and executes a move onto it. The square to move into
    is chosen by the minimax algorithm with alpha-beta pruning. If the square
    is blitzable, then the square is blitzed, otherwise, it is paradropped into.
    */
    public void makeMove(Board board) {
        long start = System.nanoTime()/1000;
        int[] m = alphabeta(board, 3, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
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

    /*
    Executes a paradrop move onto the given Square of the given Board.
    */
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

    /*
    Executes a death blitz into the given Square of the given Board. If the
    Square blitzed into has neighbors occupied by the enemy, then those Squares
    are taken as well.
    */
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

    /*
    If the Board is full and the current player has won, then an otherwise
    impossibly-high score is returned. If the player has lost, then an otherwise
    impossibly-low score is returned. Otherwise, the difference between the
    current player's score and the opponent's score is returned.
    */
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

    /*
    Uses the minimax adversarial search algorithm with alpha-beta pruning to
    determine the best next move to make. The move is determined by repeatedly
    making copies of the Board input and acting out the following steps in the
    game on it. The return is an integer array that stores the best score as
    its first element (so that the method can be used recursively) and the X
    and Y coordinate of the best move as the 2nd and 3rd elements.
    */
    public int[] alphabeta(Board board, int depth, boolean maxPlayer, int alpha, int beta) {
        int currentScore;
        int bestX = -1;
        int bestY = -1;

        if(depth == 0 || board.getUnoccupiedSquares().size() == 0) {
            currentScore = evaluate(board);
            return new int[] {currentScore, bestX, bestY};
        }
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
                currentScore = alphabeta(b, depth-1, false, alpha, beta)[0];
                if(currentScore > alpha) {
                    alpha = currentScore;
                    if(alpha >= beta)
                        break;
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
                currentScore = alphabeta(b, depth-1, false, alpha, beta)[0];
                if(currentScore < beta) {
                    beta = currentScore;
                    if(beta <= alpha)
                        break;
                    bestX = sq.getX();
                    bestY = sq.getY();
                }
            }
        }
        int bestScore = (maxPlayer) ? alpha : beta;
        return new int[] {bestScore, bestX, bestY};
    }
}
