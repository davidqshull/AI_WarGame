David Shull
CSC380: Artificial Intelligence
Project 2: WarGame



============================FILES============================

AlphaBetaPlayer:

This subclass of Player makes its moves using the minimax adversarial search
algorithm with alpha beta pruning.

Board:

Board holds onto the Square objects that are used to play the game, as well as
the Player objects that are playing the game. A Board is used to track the
move-by-move progress through the game as well as determine when the game is
done.

Driver:

UDriver uses all of the other files to execute the WarGame and print out the
statistics at the end.

MinimaxPlayer:

This subclass of Player makes its moves using the minimax adversarial search
algorithm without alpha beta pruning.

Player:

Player is an abstract superclass used so that all of the different types of
players can be treated equally while the game is running.

RandomPlayer:

This subclass of Player makes its moves by randomly moving into squares on the
board.

Square:

Square represents a space on the Board. It holds onto its owner, its neighbors,
its value, and its coordinates.

Writeup.pdf:

The end statistics of all the different possible games.




============================HOW TO RUN============================

At the command line, change directory to the P1 folder. Once inside, run the
command "javac *.java" to ensure that the class files are all up to date with
the corresponding Java files. Next, run the command "java Driver". This will
print a prompt asking you to enter one of the board options. Type in one of the
listed board numbers and hit Enter. Next, you will be presented with a list of
Player types for player 1. Type the number of the desired player type, then hit
Enter. Next, you will be presented with a list of Player types for player 2.
Type the number of the desired player type, then hit Enter. At this point, the
game will be played with the given player types. The completed board will be
printed out and the end-of-game statistics will be printed to the console. To
run the code again, simply hit the up arrow key and hit Enter, or type in
"java Driver" again and hit Enter.
