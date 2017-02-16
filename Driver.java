import java.util.*;
import java.io.*;

public class Driver {

    public static void main(String[] args) {
        ArrayList<File> boards = new ArrayList<>(Arrays.asList(new File("./GameBoards").listFiles()));
        Board b = new Board(boards.get(0));
        //b.printBoard();
        b.printAllNeighbors();
    }

}
