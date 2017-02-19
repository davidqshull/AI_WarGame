import java.util.*;

public abstract class Player {

    int score;
    int moves;
    String name;
    ArrayList<Square> ownedSquares;
    int moveTime;

    public Player(String name) {
        this.name = name;
        score = 0;
        ownedSquares = new ArrayList<>();
        moveTime = 0;
    }

    public abstract void makeMove(Board board);

    public abstract void paraDrop(Board board);

    public abstract void blitz(Board board);

    public String toString() {
        return name;
    }

    public void addToMoveTime(int microseconds) {
        moveTime+=microseconds;
    }

    public int getAverageMoveTime() {
        return (moveTime/moves);
    }

    public ArrayList<Square> getOwnedSquares() {
        return ownedSquares;
    }

    public ArrayList<Square> getBlitzAbleSquares() {
        ArrayList<Square> blitzAbleSquares = new ArrayList<Square>();
        for(Square sq: ownedSquares) {
            if(sq.getUnoccupiedNeighbors().size() > 0)
                blitzAbleSquares.add(sq);
        }
        return blitzAbleSquares;
    }

    public void addOwnedSquare(Square square) {
        ownedSquares.add(square);
    }

    public void removeOwnedSquare(Square square) {
        ownedSquares.remove(square);
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int value) {
        score += value;
    }

    public void subtractFromScore(int value) {
        score -= value;
    }

    public void incrementMoves() {
        moves++;
    }

    public int getMoves() {
        return moves;
    }

    public void printStats() {
        System.out.println("Player " + name + " Score: " + score);
        System.out.println("Number of moves: " + moves);
        System.out.println("Average movetime: " + getAverageMoveTime() + " microseconds");
        System.out.print("\n");
    }

}
