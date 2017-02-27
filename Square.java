/*
David Shull
CSC 380: Artificial Intelligence
Project 2: WarGame

Square represents a space on the Board. It holds onto its owner, its neighbors,
its value, and its coordinates.
*/

import java.util.*;

public class Square {

    int value;
    int[] coords;

    HashMap<String, Square> neighbors;
    Player owner;
    boolean occupied;
    HashMap<String, Square> unoccupiedNeighbors;
    HashMap<String, Square> occupiedNeighbors;

    /*
    Default constructor; takes in the given values and creates a new Square.
    */
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

    /*
    Copy constructor for use by Board. Creates a copy of the input Square
    with different references.
    */
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
        for(String s: sq.getUnoccupiedNeighbors().keySet()) {
            this.unoccupiedNeighbors.put(s, sq.getUnoccupiedNeighbors().get(s));
        }
        this.occupiedNeighbors = new HashMap<>();
        for(String s: sq.getOccupiedNeighbors().keySet()) {
            this.occupiedNeighbors.put(s, sq.getOccupiedNeighbors().get(s));
        }
    }

    /*
    Copy constructor for use by Player. Takes in a Square and a Player. Exactly
    like the other copy constructor, but using that one would generate an
    infinite loop of creating Squares for the Player and creating Players for
    the Squares.
    */
    public Square(Square sq, Player owner) {
        this.value = sq.getValue();
        this.neighbors = new HashMap<>();
        this.coords = new int[] {sq.getCoords()[0], sq.getCoords()[1]};
        this.owner = owner;
        this.occupied = sq.isOccupied();
        this.unoccupiedNeighbors = new HashMap<>();
        for(String s: sq.getUnoccupiedNeighbors().keySet()) {
            this.unoccupiedNeighbors.put(s, sq.getUnoccupiedNeighbors().get(s));
        }
        this.occupiedNeighbors = new HashMap<>();
        for(String s: sq.getOccupiedNeighbors().keySet()) {
            this.occupiedNeighbors.put(s, sq.getOccupiedNeighbors().get(s));
        }
    }

    /*
    Returns the value of this Square.
    */
    public int getValue() {
        return value;
    }

    /*
    Sets the value of this Square to the given value.
    */
    public void setValue(int value) {
        this.value = value;
    }

    /*
    Returns the x coordinate of this Square.
    */
    public int getX() {
        return coords[0];
    }

    /*
    Sets the x coordinate of this Square to the given value.
    */
    public void setX(int x) {
        coords[0] = x;
    }

    /*
    Returns the y coordinate of this Square.
    */
    public int getY() {
        return coords[1];
    }

    /*
    Sets the y coordinate of this Square to the given value.
    */
    public void setY(int y) {
        coords[1] = y;
    }

    /*
    Returns the array holding this Square's coordinates.
    */
    public int[] getCoords() {
        return coords;
    }

    /*
    Returns the HashMap holding this Square's neighbors.
    */
    public HashMap<String, Square> getNeighbors() {
        return neighbors;
    }

    /*
    Adds the given Square to the neighbors HashMap.
    */
    public void addNeighbor(String direction, Square neighbor) {
        neighbors.put(direction, neighbor);
        unoccupiedNeighbors.put(direction, neighbor);
    }

    /*
    Adds the given HashMap of neighbors to this Square's neighbors HashMap.
    */
    public void addNeighbors(HashMap<String, Square> neighbors) {
        this.neighbors.putAll(neighbors);
    }

    /*
    Returns the owner.
    */
    public Player getOwner() {
        return owner;
    }

    /*
    Sets the owner to the given Player.
    */
    public void setOwner(Player player) {
        owner = player;
        occupied = true;
    }

    /*
    Returns true if this Square is owned, false if it is not.
    */
    public boolean isOccupied() {
        return occupied;
    }

    /*
    Checks whether this Square has unoccupied neighbors.
    */
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

    /*
    Returns the HashMap of unoccupied neighbors.
    */
    public HashMap<String, Square> getUnoccupiedNeighbors() {
        return unoccupiedNeighbors;
    }

    /*
    Returns the HashMap of occupied neighbors.
    */
    public HashMap<String, Square> getOccupiedNeighbors() {
        return occupiedNeighbors;
    }

    /*
    Removes the specified neighbor from the HashMap of unoccupied neighbors.
    */
    public Square removeUnoccupiedNeighbor(String neighborKey) {
        Square neighbor = unoccupiedNeighbors.remove(neighborKey);
        occupiedNeighbors.put(neighborKey, neighbor);
        return neighbor;
    }

    /*
    Removes the specified neighbor from the HashMap of unoccupied neighbors.
    */
    public Square removeUnoccupiedNeighbor(Square neighbor) {
        for(String s: unoccupiedNeighbors.keySet()) {
            if(unoccupiedNeighbors.get(s) == neighbor) {
                return removeUnoccupiedNeighbor(s);
            }
        }
        return null;
    }

    /*
    Returns a String representation of the contents of the neighbors HashMap.
    */
    public String neighborString() {
        String s = "" + coords[0] + coords[1] + ":\n";
        for(String key: neighbors.keySet()) {
            s += "  " + key + " > " + neighbors.get(key).getX() + neighbors.get(key).getY() + "\n";
        }
        return s;
    }

    /*
    Checks whether the input Square is equal to this one by comparing their
    coordinates.
    */
    public boolean equals(Square other) {
        return Arrays.equals(coords, other.getCoords());
    }

    /*
    Converts this Square to a String representation of itself. 
    */
    public String toString() {
        String s = Integer.toString(value);
        if(owner != null)
            return s+= owner;
        return s += "_";
    }
}
