package org.example;

import java.util.Arrays;

public class Board {
    private int[][] board;

    public Board(int width, int height) {
        board = new int[width][height];
    }

    public int[][] getBoard() {
        int[][] boardCopy = new int[board.length][board[0].length];
        for(int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, boardCopy[i], 0, board[0].length);
        }
        return boardCopy;
    }

    public boolean isValidMove(int column) throws Exception {
        if(column < 0 || column >= board.length) return false;

        int height = board[column].length - 1;
        return board[column][height] == 0;
    }

    public int[][] dropPiece(int column, int player) throws IllegalArgumentException {
        if(column < 0 || column >= board.length) throw new IllegalArgumentException("column out of bounds");;

        int height = board[column].length - 1;
        if(board[column][height] != 0) throw new IllegalArgumentException("column already full");

        for(int i = 0; i < board[column].length; i++) {
            if(board[column][i] == 0) {
                board[column][i] = player;
                break;
            }
        }

        return getBoard();
    }

    public boolean canContinue() {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] == 0) return true;
            }
        }

        return false;
    }

    public boolean boardHasWinCondition(int[][] board, int winCondition) {
        for(int i = 0; i < board.length; i++) {
            for(int j = 0; j < board[0].length; j++) {
                if(board[i][j] != 0) {
                    int player = board[i][j];

                    // column check
                    if(checkWinConditionInColumn(board, i, j, player, winCondition)) return true;

                    // row check
                    if(checkWinConditionInRow(board, i, j, player, winCondition)) return true;

                    // diagonal left check
                    if(checkWinConditionLeftDiagonal(board, i, j, player, winCondition)) return true;

                    // diagonal right check
                    if(checkWinConditionRightDiagonal(board, i, j, player, winCondition)) return true;
                }
            }
        }


        return false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = board[0].length -1; i >= 0; i--) {
            for (int j = 0; j < board.length; j++) {
                sb.append(board[j][i]);
                if (j < board.length - 1) sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private boolean checkWinConditionInColumn(int[][] board, int col, int row, int player, int winCondition) {
        int colCount = 0;
        for(; col < board.length; col++) {
            if(board[col][row] == player) {
                colCount++;
                if(colCount == winCondition) return true;
            } else break;
        }

        return false;
    }

    private boolean checkWinConditionInRow(int[][] board, int col, int row, int player, int winCondition) {
        int rowCount = 0;
        for(; row < board[col].length; row++) {
            if(board[col][row] == player) {
                rowCount++;
                if(rowCount == winCondition) return true;
            } else break;
        }

        return false;
    }

    private boolean checkWinConditionLeftDiagonal(int[][] board, int col, int row, int player, int winCondition) {
        int lDiagonalCount = 0;
        for(int delta = 0; delta < 4; delta++) {
            int x = col + delta;
            int y = row + delta;

            if(x >= board.length) break;
            if(y >= board[0].length) break;

            if(board[x][y] == player) {
                lDiagonalCount++;
                if(lDiagonalCount == winCondition) return true;
            } else break;
        }

        return false;
    }

    private boolean checkWinConditionRightDiagonal(int[][] board, int col, int row, int player, int winCondition) {
        int rDiagonalCount = 0;
        for(int delta = 0; delta < 4; delta++) {
            int x = col + delta;
            int y = row - delta;

            if(x >= board.length) break;
            if(y < 0) break;

            if(board[x][y] == player) {
                rDiagonalCount++;
                if(rDiagonalCount == winCondition) return true;
            } else break;
        }

        return false;
    }
}
