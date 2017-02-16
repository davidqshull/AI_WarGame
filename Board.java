import java.util.*;
import java.io.*;

public class Board {

    ArrayList<ArrayList<Square>> grid;
    Player player1, player2;
    int occupiedCount;

    public Board(File boardFile) {
        this.player1 = player1;
        this.player2 = player2;
        grid = new ArrayList<>();
        setUpBoard(boardFile);
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

    public void setSquare(Square square, int x, int y) {
        grid.get(x).set(y, square);
    }

    public ArrayList<ArrayList<Square>> getGrid() {
        return grid;
    }

    public boolean isGameOver() {
        if(occupiedCount == 25) {
            return true;
        }
        return false;
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
        String s = "\n   A   B   C   D   E\n";
        for(int i = 0; i < grid.size(); i++) {
            s+= (i+1) + "  ";
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
