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

    public void printBoard() {
        System.out.println(this);
    }

    public String toString() {
        String s = "\n   A  B  C  D  E\n";
        for(int i = 0; i < grid.size(); i++) {
            s+= (i+1) + "  ";
            for(Square sq: grid.get(i)) {
                s += sq + " ";
                if(sq.toString().length() == 1)
                    s += " ";
            }
            s+="\n";
        }
        return s;
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
        }
        catch (IOException e) {
            System.out.println("Bad file! Please try again.");
        }
    }

    public boolean isGameOver() {
        if(occupiedCount == 25) {
            return true;
        }
        return false;
    }
}
