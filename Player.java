import java.util.*;

public abstract class Player {

    int score;
    int moves;
    String name;
    ArrayList<Square> ownedSquares;
    long moveTime;

    public Player(String name) {
        this.name = name;
        score = 0;
        ownedSquares = new ArrayList<>();
        moveTime = 0;
    }

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

    public abstract void paraDrop(Board board);

    public abstract void blitz(Board board);

    public boolean equals(Player p) {
        return p.toString().equals(this.name);
    }

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

    public String toString() {
        return name;
    }

    public void addToMoveTime(long nanoseconds) {
        moveTime+=nanoseconds;
    }

    public long getAverageMoveTime() {
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

    public ArrayList<Square> getParadropAbleSquares(Board board) {
        return board.getUnoccupiedSquares();
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

    public long getMoveTime() {
        return moveTime;
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
