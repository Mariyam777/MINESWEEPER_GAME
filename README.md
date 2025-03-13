Minesweeper Game - Documentation
This project is a Minesweeper game implemented in Java. The game allows users to select a difficulty level and play Minesweeper using a command-line interface. The objective of the game is to uncover all safe cells while avoiding mines.

#Features:
1)Selectable difficulty levels: Beginner, Intermediate, and Expert
2)Random mine placement
3)Numbered hints indicating the count of adjacent mines
4)Flagging system to mark suspected mines
5)Recursive uncovering of adjacent empty cells
6)Timer tracking game duration
7)Option to restart the game after winning or losing

#How to Play
Start the game, and choose a difficulty level:

Beginner: 9x9 grid with 10 mines
Intermediate: 16x16 grid with 40 mines
Expert: 16x30 grid with 99 mines

Enter a command to interact with the game:
Uncover a cell: u row col (e.g., u 3 5 to uncover row 3, column 5)
Flag a suspected mine: f row col (e.g., f 2 4 to flag row 2, column 4)

The game continues until:

You uncover all safe cells (win)
You hit a mine (lose)
You choose to quit

#Code Overview

The program consists of the following main components:

1. Difficulty Selection (difficulty method)
Allows users to select a difficulty level.
Sets board dimensions and mine count accordingly.

2. Mine Placement (placeMines method)
Randomly places mines on the board.
Ensures no duplicate placements.

3. Mine Count Calculation (calculateNumbers method)
Determines the number of mines adjacent to each cell.
Populates the board with hints.

4. Game Board Display (printBoard method)
Displays the current state of the game board.
Shows mines remaining and elapsed time.

5. Recursive Cell Uncovering (uncoverAdjacentCells method)
Automatically reveals adjacent empty cells if a zero is uncovered.
Uses recursion to propagate clearing of cells.

6. Game Loop (main method)
Handles user input (uncovering and flagging cells).
Checks win/loss conditions.
Offers the option to restart upon game over.

#Conclusion

This Minesweeper game is a fully functional CLI-based implementation that closely follows the traditional Minesweeper rules. It features different difficulty levels, random mine placement, and a recursive cell uncovering mechanism to enhance gameplay. Users can enjoy the classic experience of Minesweeper with simple text commands.


