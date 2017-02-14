import java.util.*;

public class Board {

    ArrayList<ArrayList<Square>> grid;
    Player player1, player2;

    public Board() {
        this.player1 = player1;
        this.player2 = player2;
        grid = new ArrayList<>();
        setUpBoard();
    }

    public void printBoard() {
        System.out.println(this);
    }

    public String toString() {
        String s = "   A  B  C  D  E\n";
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

    private void setUpBoard() {
        for(int i = 0; i < 5; i++) {
            ArrayList<Square> temp = new ArrayList<>();
            for(int j = 0; j < 5; j++) {
                Square sq = new Square(j, i);
                temp.add(sq);
            }
            grid.add(temp);
        }
    }
}
