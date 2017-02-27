/*
David Shull
CSC 380: Artificial Intelligence
Project 2: WarGame

Player is an abstract superclass used so that all of the different types of
players can be treated equally while the game is running.
*/

import java.util.*;

public abstract class Player {

    int score;
    int moves;
    String name;
    ArrayList<Square> ownedSquares;
    long moveTime;

    /*
    Default constructor, instantiates a Player object.
    */
    public Player(String name) {
        this.name = name;
        score = 0;
        ownedSquares = new ArrayList<>();
        moveTime = 0;
    }

    /*
    Copy constructor, creates an exact copy of all the input Player's fields
    without using the references to those fields.
    */
    public Player(Player player) {
        this.score = player.getScore();
        this.moves = player.getMoves();
        this.name = player.toString();
        this.ownedSquares = new ArrayList<>();
        for(Square sq: player.getOwnedSquares()) {
            this.ownedSquares.add(new Square(sq, this));
        }
        this.moveTime = player.getMoveTime();
    }

    public abstract void makeMove(Board board);

    /*
    If the names of the players are the same, then they are the same.
    */
    public boolean equals(Player p) {
        return p.toString().equals(this.name);
    }

    /*
    Returns the type of the Player object input.
    */
    public static String getPlayerType(Player player) {
        if(player instanceof RandomPlayer)
            return "Random";
        else if(player instanceof MinimaxPlayer)
            return "Minimax";
        else if(player instanceof AlphaBetaPlayer)
            return "AlphaBeta";
        else
            return null;
    }

    /*
    Returns the name of the Player.
    */
    public String toString() {
        return name;
    }

    /*
    Adds the input long to the moveTime variable.
    */
    public void addToMoveTime(long nanoseconds) {
        moveTime+=nanoseconds;
    }

    /*
    Returns the current total moveTime divided by the number of moves.
    */
    public long getAverageMoveTime() {
        return (moveTime/moves);
    }

    /*
    Returns the ArrayList of Squares that this Player owns.
    */
    public ArrayList<Square> getOwnedSquares() {
        return ownedSquares;
    }

    /*
    Returns an ArrayList of Squares that this Player can currently blitz.
    */
    public ArrayList<Square> getBlitzAbleSquares() {
        ArrayList<Square> blitzAbleSquares = new ArrayList<Square>();
        for(Square sq: ownedSquares) {
            if(sq.getUnoccupiedNeighbors().size() > 0)
                blitzAbleSquares.add(sq);
        }
        return blitzAbleSquares;
    }

    /*
    Returns an ArrayList of the Squares that this Player can currently paradrop.
    */
    public ArrayList<Square> getParadropAbleSquares(Board board) {
        return board.getUnoccupiedSquares();
    }

    /*
    Adds the given Square to the collection of owned Squares.
    */
    public void addOwnedSquare(Square square) {
        ownedSquares.add(square);
    }

    /*
    Removes the given Square from the collection of owned Squares.
    */
    public void removeOwnedSquare(Square square) {
        ownedSquares.remove(square);
    }

    /*
    Returns the Player's score.
    */
    public int getScore() {
        return score;
    }

    /*
    Adds the given int value to the current score.
    */
    public void addToScore(int value) {
        score += value;
    }

    /*
    Subtracts the given int value from the current score.
    */
    public void subtractFromScore(int value) {
        score -= value;
    }

    /*
    Increments the number of moves this player has taken.
    */
    public void incrementMoves() {
        moves++;
    }

    /*
    Returns the total movetime.
    */
    public long getMoveTime() {
        return moveTime;
    }

    /*
    Returns the number of moves taken.
    */
    public int getMoves() {
        return moves;
    }

    /*
    Prints out the end-of-game statistics for this Player.
    */
    public void printStats() {
        System.out.println("Player " + name + " Score: " + score);
        System.out.println("Number of moves: " + moves);
        System.out.println("Average movetime: " + getAverageMoveTime() + " microseconds");
        System.out.print("\n");
    }

}
