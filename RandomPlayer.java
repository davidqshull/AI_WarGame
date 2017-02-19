import java.util.*;

public class RandomPlayer extends Player {

    public RandomPlayer(String name) {
        super(name);
    }

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

    public void paraDrop(Board board) {
        Square sq = randomSquare(board, "paradrop", 0);
        sq.setOwner(this);
        board.incrementOccupiedCount();
        board.addOccupiedSquare(sq);
        super.addToScore(sq.getValue());
        super.addOwnedSquare(sq);
    }

    public void blitz(Board board) {
        Square sq = randomSquare(board, "blitz", 0);
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
        super.addToScore(sq.getValue());
        if(neighbor.getOccupiedNeighbors().size() > 0) {
            for(String key: neighbor.getOccupiedNeighbors().keySet()) {
                Square temp = neighbor.getOccupiedNeighbors().get(key);
                Player opponent = temp.getOwner();
                opponent.subtractFromScore(temp.getValue());
                addToScore(temp.getValue());
                temp.setOwner(this);
                super.addOwnedSquare(temp);
                opponent.removeOwnedSquare(temp);
                board.addOccupiedSquare(temp);
            }
        }
    }

    private Square randomSquare(Board board, String caller, int timesCalled) {
        if(caller.equals("blitz")) {
            if(getOwnedSquares().size() == 0)
                paraDrop(board);
            int randomIndex = new Random().nextInt(getOwnedSquares().size());
            Square sq = getOwnedSquares().get(randomIndex);
            if(sq.getUnoccupiedNeighbors().size() > 0)
                return sq;
            if(sq.getOccupiedNeighbors().size() == 0)
                paraDrop(board);
            return randomSquare(board, caller, timesCalled+1);
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

    private Square getRandomUnoccupiedNeighbor(Square sq) {
        HashMap<String, Square> unoccupiedNeighbors = sq.getUnoccupiedNeighbors();
        int randomKey = new Random().nextInt(unoccupiedNeighbors.size());
        List<String> keys = new ArrayList<String>(unoccupiedNeighbors.keySet());
        String mapKey = keys.get(randomKey);
        return sq.removeUnoccupiedNeighbor(mapKey);
    }
}
