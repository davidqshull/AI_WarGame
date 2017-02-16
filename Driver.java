import java.util.*;
import java.io.*;

public class Driver {

    public static void main(String[] args) {
        ArrayList<File> boards = new ArrayList<>(Arrays.asList(new File("./GameBoards").listFiles()));
        for(File file: boards) {
            Board b = new Board(file);
            b.printBoard();
        }
    }

}
