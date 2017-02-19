import java.util.*;
import java.io.*;

public class Driver {

    public static void main(String[] args) {
        ArrayList<File> boards = new ArrayList<>(Arrays.asList(new File("./GameBoards").listFiles()));
        Player player1;
        Player player2;
        Board b;
        int moveTime1 = 0;
        int score1 = 0;
        int moves1 = 0;
        int moveTime2 = 0;
        int score2 = 0;
        int moves2 = 0;

        for(int i = 1; i < 51; i++) {
            player1 = new RandomPlayer("B");
            player2 = new RandomPlayer("G");
            b = new Board(boards.get(0), player1, player2);
            //System.out.print("\n");
            boolean gameIsOver = false;
            int turnNumber = 1;

            //System.out.println("GAME " + i);

            while(true) {
                player1.makeMove(b);
                if(b.gameIsOver())
                    break;
                player2.makeMove(b);
                if(b.gameIsOver())
                    break;
                turnNumber++;
            }

            // System.out.println(boards.get(0));
            // b.printBoard();
            //
            // System.out.print("\n");
            // player1.printStats();
            // player2.printStats();

            moveTime1 += player1.getAverageMoveTime();
            moveTime2 += player2.getAverageMoveTime();
            moves1 += player1.getMoves();
            moves2 += player2.getMoves();
            score1 += player1.getScore();
            score2 += player2.getScore();
        }
        System.out.println("\nPlayer B Avg Score: " + score1/50);
        System.out.println("Average moves: " + moves1/50);
        System.out.println("Average movetime: " + (moveTime1/50) + "\n");
        System.out.println("Player G Avg Score: " + score2/50);
        System.out.println("Average moves: " + moves2/50);
        System.out.println("Average movetime: " + (moveTime2/50));
    }
}
