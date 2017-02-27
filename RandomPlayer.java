/*
David Shull
CSC 380: Artificial Intelligence
Project 2: WarGame

This subclass of Player makes its moves by randomly moving into squares on the
board.
*/

import java.util.*;

public class RandomPlayer extends Player {

    /*
    Invokes the superclass constructor on the given String.
    */
    public RandomPlayer(String name) {
        super(name);
    }

    /*
    Invokes the superclass copy constructor on the given Player.
    */
    public RandomPlayer(Player player) {
        super(player);
    }

    /*
    Takes in a Board object and executes a move onto it. The move to be taken
    is chosen randomly by randomly deciding whether to blitz or paradrop.
    */
    public void makeMove(Board board) {
        int start = (int) System.nanoTime()/1000;
        int moveType;
        if(super.getMoves() == 0 || super.getBlitzAbleSquares().size() == 0)
            moveType = 0;
        else
            moveType = new Random().nextInt(2);

        if(moveType == 0) {
            paraDrop(board);
        }
        else {
            blitz(board);
        }
        incrementMoves();
        int end = (int) System.nanoTime()/1000;
        super.addToMoveTime((end-start));
    }

    /*
    Paradrops a Square on the input Board by randomly choosing a Square and then
    taking it.
    */
    public void paraDrop(Board board) {
        Square sq = randomSquare(board, "paradrop");
        sq.setOwner(this);
        board.incrementOccupiedCount();
        board.addOccupiedSquare(sq);
        super.addToScore(sq.getValue());
        super.addOwnedSquare(sq);
    }

    /*
    Blitzes a Square on the input Board by randomly choosing a Square, randomly
    choosing one of its unoccupied neighbors, and moving into it. Any
    enemy-occupied Squares neighboring the Square moved into are taken as well.
    */
    public void blitz(Board board) {
        Square sq = randomSquare(board, "blitz");
        Square neighbor = getRandomUnoccupiedNeighbor(sq);
        if(neighbor.getOwner() != null) {
            System.out.println("Neighbor isn't unoccupied!!");
            System.exit(0);
        }
        neighbor.setOwner(this);
        for(String neighborKey: neighbor.getNeighbors().keySet()) {
            neighbor.getNeighbors().get(neighborKey).removeUnoccupiedNeighbor(neighbor);
        }
        board.addOccupiedSquare(neighbor);
        super.addOwnedSquare(neighbor);
        board.incrementOccupiedCount();
        super.addToScore(neighbor.getValue());
        if(neighbor.getOccupiedNeighbors().size() > 0) {
            for(String key: neighbor.getOccupiedNeighbors().keySet()) {
                Square temp = neighbor.getOccupiedNeighbors().get(key);
                if(!temp.getOwner().equals(this)) {
                    Player opponent = temp.getOwner();
                    opponent.subtractFromScore(temp.getValue());
                    super.addToScore(temp.getValue());
                    temp.setOwner(this);
                    super.addOwnedSquare(temp);
                    opponent.removeOwnedSquare(temp);
                    //board.addOccupiedSquare(temp);
                }
            }
        }
    }

    /*
    If the caller is the blitz method, then finds a Square that can be
    can be blitzed from. If the caller is the paraDrop method, then finds a
    Square that can paradropped.
    */
    private Square randomSquare(Board board, String caller) {
        if(caller.equals("blitz")) {
            if(getOwnedSquares().size() == 0)
                paraDrop(board);
            int randomIndex = new Random().nextInt(getOwnedSquares().size());
            Square sq = getOwnedSquares().get(randomIndex);
            if(sq.getUnoccupiedNeighbors().size() > 0)
                return sq;
            if(sq.getOccupiedNeighbors().size() == 0)
                paraDrop(board);
            return randomSquare(board, caller);
        }
        else {
            int randomIndex = new Random().nextInt(board.getUnoccupiedSquares().size());
            Square sq = board.getUnoccupiedSquares().get(randomIndex);
            for(String neighborKey: sq.getNeighbors().keySet()) {
                sq.getNeighbors().get(neighborKey).removeUnoccupiedNeighbor(sq);
            }
            return sq;
        }
    }

    /*
    Randomly chooses one of the unoccupied neighbors of the given Square.
    */
    private Square getRandomUnoccupiedNeighbor(Square sq) {
        HashMap<String, Square> unoccupiedNeighbors = sq.getUnoccupiedNeighbors();
        int randomKey = new Random().nextInt(unoccupiedNeighbors.size());
        List<String> keys = new ArrayList<String>(unoccupiedNeighbors.keySet());
        String mapKey = keys.get(randomKey);
        return sq.removeUnoccupiedNeighbor(mapKey);
    }
}
