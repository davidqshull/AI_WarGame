import java.util.*;

public class Square {

    int value;
    int[] coords;

    ArrayList<Square> neighbors;
    Player owner;
    boolean occupied;

    public Square(int value, int x, int y) {
        this.value = value;
        neighbors = new ArrayList<>();
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

    public ArrayList<Square> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(Square neighbor) {
        neighbors.add(neighbor);
    }

    public void addNeighbors(ArrayList<Square> neighbors) {
        this.neighbors.addAll(neighbors);
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

    public String toString() {
        return "" + value;
    }
}
