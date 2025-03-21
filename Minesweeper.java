import java.util.*;

public class Minesweeper {

    static Scanner scanner = new Scanner(System.in);

    // Clear the console
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Ask user for difficulty level
    public static void difficulty(int[] side, int[] secside, int[] mines, int[] maxmoves) {
        System.out.println("Welcome to Minesweeper!");
        System.out.println("\nChoose a difficulty level:");
        System.out.println("1. Beginner (9x9 grid with 10 mines)");
        System.out.println("2. Intermediate (16x16 grid with 40 mines)");
        System.out.println("3. Expert (16x30 grid with 99 mines)");
        System.out.print("Enter your choice: ");
        int n = scanner.nextInt();

        if (n == 1) {
            side[0] = secside[0] = 9;
            mines[0] = 10;
        } else if (n == 2) {
            side[0] = secside[0] = 16;
            mines[0] = 40;
        } else if (n == 3) {
            side[0] = 16;
            secside[0] = 30;
            mines[0] = 99;
        } else {
            System.out.println("Please choose a number between 1 and 3.");
            difficulty(side, secside, mines, maxmoves);
        }

        maxmoves[0] = side[0] * secside[0] - mines[0]; // calculate the number of safe cells
    }

    // Check if a cell is valid
    public static boolean isValid(int row, int column, int side, int secside) {
        return row >= 0 && row < side && column >= 0 && column < secside;
    }

    // Generate mines randomly
    public static void placeMines(int[][] realboard, int mines, int side, int secside) {
        Random rand = new Random();
        for (int i = 0; i < mines; i++) {
            int x, y;
            do {
                x = rand.nextInt(side);
                y = rand.nextInt(secside);
            } while (realboard[x][y] == 10); // 10 represents a mine
            realboard[x][y] = 10; // Place mine
        }
    }

    // Calculate numbers for the non-mine cells
    public static void calculateNumbers(int[][] realboard, int side, int secside) {
        for (int row = 0; row < side; row++) {
            for (int column = 0; column < secside; column++) {
                if (realboard[row][column] == 10) continue; // Skip mines
                int mineCount = 0;
                // Check 8 neighboring cells for mines
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nr = row + dx, nc = column + dy;
                        if (isValid(nr, nc, side, secside) && realboard[nr][nc] == 10) mineCount++;
                    }
                }
                realboard[row][column] = mineCount;
            }
        }
    }

    // Print the current state of the board
    public static void printBoard(int[][] board, int side, int secside, int mines, int flagsPlaced, long startTime) {
        int minesRemaining = mines - flagsPlaced;
        long currentTime = System.currentTimeMillis();
        float timeElapsed = (float) (currentTime - startTime) / 1000;

        System.out.println("\nMines remaining: " + minesRemaining + " | Time elapsed: " + timeElapsed + "s");

        // Print column headers
        System.out.print("   ");
        for (int i = 1; i <= secside; i++) {
            System.out.print((i < 10 ? " " : "") + i + " ");
        }
        System.out.println();

        // Print board rows
        for (int i = 0; i < side; i++) {
            System.out.print((i + 1 < 10 ? " " : "") + (i + 1) + " ");
            for (int j = 0; j < secside; j++) {
                if (board[i][j] == 11) System.out.print("|-| ");
                else if (board[i][j] == 10) System.out.print("|*| ");
                else if (board[i][j] == 12) System.out.print("|F| ");
                else System.out.print("|" + board[i][j] + "| ");
            }
            System.out.println();
        }
    }

    // Recursive function to uncover adjacent cells
    public static void uncoverAdjacentCells(int row, int column, int[][] board, int
            [][] realboard, int side, int secside, int[] maxmoves) {
        if (!isValid(row, column, side, secside) || board[row][column] != 11) return;

        board[row][column] = realboard[row][column];
        --maxmoves[0];

        if (realboard[row][column] != 0) return; // Stop if the cell is not a 0

        // Directions to uncover: up, down, left, right, and diagonals
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int i = 0; i < 8; ++i) {
            int newRow = row + directions[i][0];
            int newColumn = column + directions[i][1];
            uncoverAdjacentCells(newRow, newColumn, board, realboard, side, secside, maxmoves);
        }
    }

    // End game board display
    public static void endBoard(int[][] board, int[][] realboard, int side, int secside) {
        System.out.println("   ");
        for (int i = 1; i <= secside; i++) {
            System.out.print((i < 10 ? " " : "") + i + " ");
        }
        System.out.println();

        for (int i = 0; i < side; i++) {
            System.out.print((i + 1 < 10 ? " " : "") + (i + 1) + " ");
            for (int j = 0; j < secside; j++) {
                if (board[i][j] == 11 && realboard[i][j] != 10) System.out.print("|-| ");
                else if (board[i][j] == 12) System.out.print("|F| ");
                else if (realboard[i][j] == 10) System.out.print("|*| ");
                else System.out.print("|" + board[i][j] + "| ");
            }
            System.out.println();
        }
    }

    // Main game loop
    public static void main(String[] args) {
        int[] side = new int[1], secside = new int[1], mines = new int[1], maxmoves = new int[1];
        int flagsPlaced = 0;
        String a;
        long startTime = System.currentTimeMillis();

        clear();
        difficulty(side, secside, mines, maxmoves);

        int[][] board = new int[side[0]][secside[0]];
        int[][] realboard = new int[side[0]][secside[0]];

        for (int i = 0; i < side[0]; i++) {
            for (int j = 0; j < secside[0]; j++) {
                board[i][j] = 11; // 11 represents an unopened cell
                realboard[i][j] = 0; // Initially, all cells are 0
            }
        }

        placeMines(realboard, mines[0], side[0], secside[0]);
        calculateNumbers(realboard, side[0], secside[0]);

        while (maxmoves[0] > 0) {
            clear();
            printBoard(board, side[0], secside[0], mines[0], flagsPlaced, startTime);
            char command;
            int row, column;

            System.out.println("\nCommands:");
            System.out.println("- Uncover Cell: u row col");
            System.out.println("- Flag Cell: f row col");
            System.out.print("Enter your command: ");
            command = scanner.next().charAt(0);
            row = scanner.nextInt() - 1; // Adjust for 0-indexed board
            column = scanner.nextInt() - 1;

            if (command == 'u') {
                if (!isValid(row, column, side[0], secside[0])) continue;
                if (realboard[row][column] == 10) {
                    clear();
                    System.out.println("Game Over! You hit a mine!");
                    endBoard(board, realboard, side[0], secside[0]);
                    System.out.print("Play again? (Y/N): ");
                    a = scanner.next();
                    if (a.equalsIgnoreCase("Y")) {
                        main(args); // Restart the game
                    } else {
                        System.out.println("Thank you for playing Minesweeper!");
                        break;
                    }
                } else {
                    uncoverAdjacentCells(row, column, board, realboard, side[0], secside[0], maxmoves);
                }
            } else if (command == 'f') {
                if (isValid(row, column, side[0], secside[0]) && board[row][column] == 11) {
                    board[row][column] = 12; // Flag the cell
                    flagsPlaced++;
                }
            }

            if (maxmoves[0] == 0) {
                clear();
                printBoard(board, side[0], secside[0], mines[0], flagsPlaced, startTime);
                System.out.println("Congratulations! You have uncovered all safe cells!");
                System.out.print("Play again? (Y/N): ");
                a = scanner.next();
                if (a.equalsIgnoreCase("Y")) {
                    main(args); // Restart the game
                } else {
                    System.out.println("Thank you for playing Minesweeper!");
                    break;
                }
            }
        }
    }
}
