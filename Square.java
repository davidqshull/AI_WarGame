import java.util.*;

public class Square {

    int value;
    int[] coords;

    ArrayList<Square> neighbors;
    Player owner;

    public Square(int x, int y) {
        value = new Random().nextInt(99) + 1;
        neighbors = new ArrayList<>();
        coords = new int[2];
        coords[0] = x;
        coords[1] = y;
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
    }

    public String toString() {
        return "" + value;
    }
}
