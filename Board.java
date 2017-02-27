/*
David Shull
CSC 380: Artificial Intelligence
Project 2: WarGame

Board holds onto the Square objects that are used to play the game, as well as
the Player objects that are playing the game. A Board is used to track the
move-by-move progress through the game as well as determine when the game is
done.
*/

import java.util.*;
import java.io.*;

public class Board {

    ArrayList<ArrayList<Square>> grid;
    Player player1, player2;
    int occupiedCount;
    ArrayList<Square> occupiedSquares;
    ArrayList<Square> unoccupiedSquares;

    /*
    Normal constructor used to instantiate the board.
    */
    public Board(File boardFile, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        grid = new ArrayList<>();
        occupiedSquares = new ArrayList<>();
        unoccupiedSquares = new ArrayList<>();
        setUpBoard(boardFile);
    }

    /*
    Copy constructor used to create a replica of the board, with all new references.
    */
    public Board(Board copy) {
        this.grid = new ArrayList<>();
        for(ArrayList<Square> list: copy.getGrid()) {
            ArrayList<Square> temp = new ArrayList<>();
            for(Square sq: list) {
                temp.add(new Square(sq));
            }
            grid.add(temp);
        }
        String playerType1 = Player.getPlayerType(copy.getPlayer1());
        String playerType2 = Player.getPlayerType(copy.getPlayer2());
        switch (playerType1) {
            case "Random":
                this.player1 = new RandomPlayer(copy.getPlayer1());
                break;
            case "Minimax":
                this.player1 = new MinimaxPlayer(copy.getPlayer1());
                break;
            case "AlphaBeta":
                this.player1 = new AlphaBetaPlayer(copy.getPlayer1());
                break;
        }
        switch (playerType2) {
            case "Random":
                this.player2 = new RandomPlayer(copy.getPlayer2());
                break;
            case "Minimax":
                this.player2 = new MinimaxPlayer(copy.getPlayer2());
                break;
            case "AlphaBeta":
                this.player2 = new AlphaBetaPlayer(copy.getPlayer2());
                break;
        }
        this.occupiedCount = copy.getOccupiedCount();
        this.occupiedSquares = new ArrayList<>();
        for(Square sq: copy.getOccupiedSquares())
            occupiedSquares.add(new Square(sq));
        this.unoccupiedSquares = new ArrayList<>();
        for(Square sq: copy.getUnoccupiedSquares())
            unoccupiedSquares.add(new Square(sq));
    }

    /*
    Creates the 2-D ArrayList of Square objects based on the file input received.
    */
    private void setUpBoard(File boardFile) {
        ArrayList<Square> temp;
        try {
            FileReader fr = new FileReader(boardFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            for(int i = 0; i < 5; i++) {
                line = br.readLine();
                ArrayList<String> tokenized = new ArrayList<>(Arrays.asList(line.split("\\s+")));
                temp = new ArrayList<>();
                for(int j = 0; j < 5; j++) {
                    int value = Integer.parseInt(tokenized.get(j));
                    temp.add(new Square(value, j, i));
                }
                grid.add(temp);
            }
            addNeighbors();
            for(ArrayList<Square> list: grid) {
                for(Square sq: list)
                    unoccupiedSquares.add(sq);
            }
        }
        catch (IOException e) {
            System.out.println("Bad file! Please try again.");
        }
    }

    /*
    For each Square in the grid, adds the neighboring Square to its neighbors
    list.
    */
    private void addNeighbors() {
        for(int i = 0; i < grid.size(); i++) {
            for(int j = 0; j < grid.get(i).size(); j++) {
                if(i > 0)
                    grid.get(i).get(j).addNeighbor("Up", grid.get(i-1).get(j));
                if(i < 4)
                    grid.get(i).get(j).addNeighbor("Down", grid.get(i+1).get(j));
                if(j > 0)
                    grid.get(i).get(j).addNeighbor("Left", grid.get(i).get(j-1));
                if(j < 4)
                    grid.get(i).get(j).addNeighbor("Right", grid.get(i).get(j+1));
            }
        }
    }

    /*
    Returns the opponent of the input Player.
    */
    public Player getOpponent(Player player) {
        return (player1.equals(player)) ? player2 : player1;
    }

    /*
    Returns player 1.
    */
    public Player getPlayer1() {
        return player1;
    }

    /*
    Returns player 2.
    */
    public Player getPlayer2() {
        return player2;
    }

    /*
    Sets the square at the given coordinates to be equal to the square that is
    input.
    */
    public void setSquare(Square square, int x, int y) {
        grid.get(x).set(y, square);
        for(Square sq: unoccupiedSquares) {
            if(sq.getX() == x && sq.getY() == y) {
                sq = square;
                break;
            }
        }
    }

    /*
    Returns the 2D ArrayList of Squares.
    */
    public ArrayList<ArrayList<Square>> getGrid() {
        return grid;
    }

    /*
    Checks whether the game has concluded.
    */
    public boolean gameIsOver() {
        if(occupiedCount == 25) {
            return true;
        }
        return false;
    }

    /*
    Returns the Square at the given coordinates.
    */
    public Square getSquare(int xCoord, int yCoord) {
        return grid.get(yCoord).get(xCoord);
    }

    /*
    Increments the number of occupied Squares by 1.
    */
    public void incrementOccupiedCount() {
        occupiedCount++;
    }

    /*
    Returns the number of occupied Squares.
    */
    public int getOccupiedCount() {
        return occupiedCount;
    }

    /*
    Returns the ArrayList of occupied Squares.
    */
    public ArrayList<Square> getOccupiedSquares() {
        return occupiedSquares;
    }

    /*
    Adds the given Square to the list of occupied Squares and removes the Square
    from the list of unoccupied Squares.
    */
    public void addOccupiedSquare(Square square) {
        occupiedSquares.add(square);
        Square temp = null;
        for(Square sq: unoccupiedSquares) {
            if(sq.getX() == square.getX() && sq.getY() == square.getY()) {
                temp = sq;
            }
        }
        if(temp != null)
            unoccupiedSquares.remove(temp);
    }

    /*
    Returns the ArrayList of unoccupied Squares.
    */
    public ArrayList<Square> getUnoccupiedSquares() {
        return unoccupiedSquares;
    }

    /*
    Returns the Square on the board with the highest value.
    */
    public Square getHighestValueSquare() {
        Square highestValue = new Square(0, 0, 0);
        for(ArrayList<Square> list: grid) {
            for(Square sq: list) {
                if(sq.getValue() > highestValue.getValue()) {
                    highestValue = sq;
                }
            }
        }
        return highestValue;
    }

    /*
    Prints the String representation of the Board to the console.
    */
    public void printBoard() {
        System.out.println(this);
    }

    /*
    Prints out all Squares followed by their neighbors.
    */
    public void printAllNeighbors() {
        String s = "";
        for(ArrayList<Square> list: grid) {
            for(Square sq: list) {
                s += sq.neighborString() + "\n";
            }
        }
        System.out.println(s);
    }

    /*
    Converts the Board to a String representation.
    */
    public String toString() {
        String s = "\n    A   B   C   D   E\n";
        for(int i = 0; i < grid.size(); i++) {
            s+= (i+1) + "|  ";
            for(Square sq: grid.get(i)) {
                s += sq + " ";
                if(sq.toString().length() == 2)
                    s += " ";
            }
            s+="\n";
        }
        return s;
    }
}
