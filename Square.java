import java.util.*;

public class Square {

    int value;
    int[] coords;

    HashMap<String, Square> neighbors;
    Player owner;
    boolean occupied;
    HashMap<String, Square> unoccupiedNeighbors;
    HashMap<String, Square> occupiedNeighbors;


    public Square(int value, int x, int y) {
        this.value = value;
        neighbors = new HashMap<>();
        coords = new int[2];
        coords[0] = x;
        coords[1] = y;
        occupied = false;
        unoccupiedNeighbors = new HashMap<>();
        occupiedNeighbors = new HashMap<>();
    }

    public Square(Square sq) {
        this.value = sq.getValue();
        this.neighbors = new HashMap<>();
        for(String s: sq.getNeighbors().keySet())
            this.neighbors.put(s, sq.getNeighbors().get(s));
        this.coords = new int[] {sq.getCoords()[0], sq.getCoords()[1]};
        String playerType = Player.getPlayerType(sq.getOwner());
        if(playerType != null) {
            switch (playerType) {
                case "Random":
                    this.owner = new RandomPlayer(sq.getOwner());
                    break;
                case "Minimax":
                    this.owner = new MinimaxPlayer(sq.getOwner());
                    break;
                case "AlphaBeta":
                    this.owner = new AlphaBetaPlayer(sq.getOwner());
                    break;
            }
        }
        this.occupied = sq.isOccupied();
        this.unoccupiedNeighbors = new HashMap<>();
        for(String s: sq.getUnoccupiedNeighbors().keySet())
            this.unoccupiedNeighbors.put(s, sq.getUnoccupiedNeighbors().get(s));
        this.occupiedNeighbors = new HashMap<>();
        for(String s: sq.getOccupiedNeighbors().keySet())
            this.occupiedNeighbors.put(s, sq.getOccupiedNeighbors().get(s));
    }

    public Square(Square sq, Player owner) {
        this.value = sq.getValue();
        this.neighbors = new HashMap<>();
        // for(String s: sq.getNeighbors().keySet())
        //     this.neighbors.put(s, sq.getNeighbors().get(s));
        this.coords = new int[] {sq.getCoords()[0], sq.getCoords()[1]};
        this.owner = owner;
        this.occupied = sq.isOccupied();
        this.unoccupiedNeighbors = new HashMap<>();
        for(String s: sq.getUnoccupiedNeighbors().keySet())
            this.unoccupiedNeighbors.put(s, sq.getUnoccupiedNeighbors().get(s));
        this.occupiedNeighbors = new HashMap<>();
        for(String s: sq.getOccupiedNeighbors().keySet())
            this.occupiedNeighbors.put(s, sq.getOccupiedNeighbors().get(s));
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getX() {
        return coords[0];
    }

    public void setX(int x) {
        coords[0] = x;
    }

    public int getY() {
        return coords[1];
    }

    public void setY(int y) {
        coords[1] = y;
    }

    public int[] getCoords() {
        return coords;
    }

    public HashMap<String, Square> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(String direction, Square neighbor) {
        neighbors.put(direction, neighbor);
        unoccupiedNeighbors.put(direction, neighbor);
    }

    public void addNeighbors(HashMap<String, Square> neighbors) {
        this.neighbors.putAll(neighbors);
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player player) {
        owner = player;
        occupied = true;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean hasUnoccupiedNeighbors() {
        for(String key: neighbors.keySet()) {
            if(neighbors.get(key).isOccupied()) {
                occupiedNeighbors.put(key, neighbors.get(key));
            }
            else {
                unoccupiedNeighbors.put(key, neighbors.get(key));
            }
        }
        if(occupiedNeighbors.size() > 0)
            return true;
        return false;
    }

    public HashMap<String, Square> getUnoccupiedNeighbors() {
        return unoccupiedNeighbors;
    }

    public HashMap<String, Square> getOccupiedNeighbors() {
        return occupiedNeighbors;
    }

    public Square removeUnoccupiedNeighbor(String neighborKey) {
        Square neighbor = unoccupiedNeighbors.remove(neighborKey);
        occupiedNeighbors.put(neighborKey, neighbor);
        return neighbor;
    }

    public Square removeUnoccupiedNeighbor(Square neighbor) {
        for(String s: unoccupiedNeighbors.keySet()) {
            if(unoccupiedNeighbors.get(s) == neighbor) {
                return removeUnoccupiedNeighbor(s);
            }
        }
        return null;
    }

    public String neighborString() {
        String s = "" + coords[0] + coords[1] + ":\n";
        for(String key: neighbors.keySet()) {
            s += "  " + key + " > " + neighbors.get(key).getX() + neighbors.get(key).getY() + "\n";
        }
        return s;
    }

    public boolean equals(Square other) {
        return Arrays.equals(coords, other.getCoords());
    }

    public String toString() {
        String s = Integer.toString(value);
        if(owner != null)
            return s+= owner;
        return s += "_";
    }
}
