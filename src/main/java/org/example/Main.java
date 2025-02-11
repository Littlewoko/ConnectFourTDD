package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try {
            Board board = new Board(1, 1);

            boolean noValidBoard = true;
            while(noValidBoard) {
                try {
                    print("Please enter the width of the game board");
                    int width = Integer.parseInt(getUserInput());

                    print("Please enter the height of the game board");
                    int height = Integer.parseInt(getUserInput());

                    board = new Board(width, height);

                    noValidBoard = false;
                } catch(Exception e) {
                    print(e.getMessage());
                    print("Game board is invalid, please enter new values");
                }
            }

            int winCondition = 4;


            int turnCounter = 0;
            int player = getNextPlayer(turnCounter);

            while(!board.boardHasWinCondition(board.getBoard(), winCondition) && board.canContinue()) {
                print(board.toString());

                player = getNextPlayer(turnCounter);
                turnCounter++;

                int move = getNextMove(board);
                board.dropPiece(move, player);
            }

            if(board.boardHasWinCondition(board.getBoard(), winCondition)) {
                print(board.toString());
                System.out.printf("Player %d won on turn %d!", player, turnCounter);
            } else {
                print(board.toString());
                System.out.printf("Stalemate on turn %d!", turnCounter);
            }
        } catch(Exception e) {
            System.out.println("Wow cowboy, somethings gone real wrong...");
        }
    }

    public static int getNextPlayer(int currentTurn) {
        return currentTurn % 2 == 0 ? 1 : 2;
    }

    public static void print(String str) {
        System.out.println(str);
    }

    public static int getNextMove(Board board) throws Exception {
        boolean invalidMoveChosen = true;
        int columnChosen = -1;

        int[][] gameBoard = board.getBoard();
        while(invalidMoveChosen) {

            System.out.printf("Please enter the column [%d - %d] you will drop a piece into: ", 0, gameBoard.length - 1);
            Scanner scanner = new Scanner(System.in);
            try {
                int temp = Integer.parseInt(scanner.nextLine());
                if(board.isValidMove(temp)) {
                    columnChosen = temp;
                    invalidMoveChosen = false;
                } else throw new Exception();
            } catch(Exception ex) {
                System.out.println("That is not a valid move");
            }
        }

        return columnChosen;
    }

    public static String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}