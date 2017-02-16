import java.util.*;

public class Square {

    int value;
    int[] coords;

    HashMap<String, Square> neighbors;
    Player owner;
    boolean occupied;

    public Square(int value, int x, int y) {
        this.value = value;
        neighbors = new HashMap<>();
        coords = new int[2];
        coords[0] = x;
        coords[1] = y;
        occupied = false;
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

    public HashMap<String, Square> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(String direction, Square neighbor) {
        neighbors.put(direction, neighbor);
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

    public String neighborString() {
        String s = "" + coords[0] + coords[1] + ":\n";
        for(String key: neighbors.keySet()) {
            s += "  " + key + " > " + neighbors.get(key).getX() + neighbors.get(key).getY() + "\n";
        }
        return s;
    }

    public String toString() {
        return "" + value + owner;
    }
}
