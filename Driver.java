import java.util.*;
import java.io.*;

public class Driver {

    ArrayList<File> boards;
    ArrayList<String> playerTypes;
    ArrayList<String> mapStrings;
    Player player1;
    Player player2;
    Board b;
    int moveTime1, moveTime2, score1, score2, moves1, moves2;

    public static void main(String[] args) {
        Driver d = new Driver();
        File boardFile = d.askForBoard();
        String playerType1 = d.askForPlayerType();
        String playerType2 = d.askForPlayerType();
        d.play(boardFile, playerType1, playerType2);
        //d.test();
    }

    public Driver() {
        boards = new ArrayList<>(Arrays.asList(new File("./GameBoards").listFiles()));
        playerTypes = new ArrayList<>(Arrays.asList("Random", "Minimax", "AlphaBeta"));
        mapStrings = new ArrayList<>();
        moveTime1 = moveTime2 = score1 = score2 = moves1 = moves2 = 0;

    }

    private void test() {
        Player player1 = new MinimaxPlayer("B");
        Player player2 = new MinimaxPlayer("G");
        Board b = new Board(boards.get(0), player1, player2);
        System.out.print("\n");
        while(true) {
            player1.makeMove(b);
            b.printBoard();
            if(b.gameIsOver())
                break;
            player2.makeMove(b);
            b.printBoard();
            if(b.gameIsOver())
                break;
        }

        b.printBoard();

        System.out.print("\n");
        player1.printStats();
        player2.printStats();
    }

    private void play(File board, String playerType1, String playerType2) {

        //int cap = (playerType1.equals("Random") || playerType2.equals("Random")) ? 51 : 2;

        for(int i = 1; i < 51; i++) {
            player1 = createPlayer(playerType1, 1);
            player2 = createPlayer(playerType2, 2);

            b = new Board(board, player1, player2);
            System.out.print("\n");
            int turnNumber = 1;

            System.out.println("GAME " + i);

            while(true) {
                player1.makeMove(b);
                if(b.gameIsOver())
                    break;
                player2.makeMove(b);
                if(b.gameIsOver())
                    break;
                turnNumber++;
            }

            System.out.println(board);
            b.printBoard();

            System.out.print("\n");
            player1.printStats();
            player2.printStats();

            moveTime1 += player1.getAverageMoveTime();
            moveTime2 += player2.getAverageMoveTime();
            moves1 += player1.getMoves();
            moves2 += player2.getMoves();
            score1 += player1.getScore();
            score2 += player2.getScore();
        }
        //if(cap == 51)
            printAverages();
    }

    private Player createPlayer(String playerType, int playerNumber) {
        switch (playerType) {
            case "Random":
                if(playerNumber == 1)
                    return new RandomPlayer("B");
                return new RandomPlayer("G");
            case "Minimax":
                if(playerNumber == 1)
                    return new MinimaxPlayer("B");
                return new MinimaxPlayer("G");
            case "AlphaBeta":
                if(playerNumber == 1)
                    return new AlphaBetaPlayer("B");
                return new AlphaBetaPlayer("G");
        }
        return null;
    }

    private void printAverages() {
        System.out.println("\nPlayer B Avg Score: " + score1/50);
        System.out.println("Average moves: " + moves1/50);
        System.out.println("Average movetime: " + (moveTime1/50) + "\n");
        System.out.println("Player G Avg Score: " + score2/50);
        System.out.println("Average moves: " + moves2/50);
        System.out.println("Average movetime: " + (moveTime2/50));
    }

    private String askForPlayerType() {
        System.out.println("\nEnter the number of the Player type that you would like to use:\n");
        System.out.println("Options");
        int i = 1;
        for(String s: playerTypes) {
            System.out.println("\t" + "(" + i + ")  " + s);
            i++;
        }
        System.out.print("\nChoice: ");
        return handlePlayerTypeInput();
    }

    private String handlePlayerTypeInput() {
        String input = System.console().readLine();
        int i = Integer.parseInt(input);
        if(i > 0 && i < 4) {
            return playerTypes.get(i-1);
        }
        if(!playerTypes.contains(input)) {
            System.out.println("ERROR: Incorrect input!!!");
            System.out.println("Please use one of the exact player types specified.");
            return askForPlayerType();
        }
        return input;
    }

    private File askForBoard() {
        System.out.println("\nEnter the board number that you would like to use:\n");
        System.out.println("Options");
        int i = 1;
        for (File file: boards) {
            System.out.println("\t" + "(" + i + ")  " + file.getName());
            mapStrings.add(file.getName());
            i++;
        }
        System.out.print("\nChoice: ");
        return handleBoardInput();
    }

    private File handleBoardInput() {
        String input = System.console().readLine();
        int i = Integer.parseInt(input);
        if(i > 0 && i < 6) {
            return boards.get(i-1);
        }
        else if(!mapStrings.contains(input)) {
            System.out.println("\nERROR: Incorrect input!!!");
            System.out.println("Please use one of the exact board names specified.");
            return askForBoard();
        }
        return getFileByName(input);
    }

    private File getFileByName(String name) {
        File temp = new File("");
        for(int i = 0; i < boards.size(); i++) {
            if(mapStrings.get(i).equals(name))
                temp = boards.get(i);
        }
        return temp;
    }
}
