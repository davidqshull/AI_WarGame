import java.util.*;
import java.io.*;

public class Board {

    ArrayList<ArrayList<Square>> grid;
    Player player1, player2;
    int occupiedCount;
    ArrayList<Square> occupiedSquares;
    ArrayList<Square> unoccupiedSquares;

    public Board(File boardFile, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        grid = new ArrayList<>();
        occupiedSquares = new ArrayList<>();
        unoccupiedSquares = new ArrayList<>();
        setUpBoard(boardFile);
    }

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

    public Player getOpponent(Player player) {
        return (player1.equals(player)) ? player2 : player1;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setSquare(Square square, int x, int y) {
        grid.get(x).set(y, square);
        for(Square sq: unoccupiedSquares) {
            if(sq.getX() == x && sq.getY() == y) {
                sq = square;
                break;
            }
        }
    }

    public ArrayList<ArrayList<Square>> getGrid() {
        return grid;
    }

    public boolean gameIsOver() {
        if(occupiedCount == 25) {
            return true;
        }
        return false;
    }

    public Square getSquare(int xCoord, int yCoord) {
        return grid.get(yCoord).get(xCoord);
    }

    public void incrementOccupiedCount() {
        occupiedCount++;
    }

    public int getOccupiedCount() {
        return occupiedCount;
    }

    public ArrayList<Square> getOccupiedSquares() {
        return occupiedSquares;
    }

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

    public ArrayList<Square> getUnoccupiedSquares() {
        return unoccupiedSquares;
    }

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

    public void printBoard() {
        System.out.println(this);
    }

    public void printAllNeighbors() {
        String s = "";
        for(ArrayList<Square> list: grid) {
            for(Square sq: list) {
                s += sq.neighborString() + "\n";
            }
        }
        System.out.println(s);
    }

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
