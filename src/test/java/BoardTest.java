import org.example.Board;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class BoardTest {
    static final int WIDTH = 9;
    static final int HEIGHT = 6;
    static final int DEFAULT = 0;
    static final int PLAYER1 = 1;
    static final int PLAYER2 = 2;

    @Test
    public void boardConstructor_initialisesBoard_withWidthAndHeight() {
        Board board = new Board(WIDTH, HEIGHT);

        Assertions.assertNotNull(board);
    }

    @Test
    public void getBoard_returnsBoardWithWidth_whenCalled() {
        Board boardClass = new Board(WIDTH, HEIGHT);
        int[][] board = boardClass.getBoard();

        Assertions.assertEquals(WIDTH, board.length);
    }

    @Test
    public void getBoard_returnsBoardWithHeight_whenCalled() {
        Board boardClass = new Board(WIDTH, HEIGHT);
        int[][] board = boardClass.getBoard();
        Assertions.assertEquals(HEIGHT, board[0].length);
    }

    @Test
    public void getBoard_returnsAValueCopyOfBoard_whenCalled() {
        Board boardClass = new Board(WIDTH, HEIGHT);
        int[][] board = boardClass.getBoard();

        board[0][0] = 1;

        int[][] newBoard = boardClass.getBoard();

        Assertions.assertNotEquals(newBoard[0][0], board[0][0]);
    }

    @Test
    void getBoard_returnsBoardInDefaultState_whenCalledBeforeAnyTurnsTaken() {
        Board boardClass = new Board(WIDTH, HEIGHT);
        int[][] board = boardClass.getBoard();

        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[i].length; j++){
                Assertions.assertEquals(board[i][j], DEFAULT);
            }
        }
    }

    @Test
    void dropPiece_putsPieceInFirstRowOfColumn_whenCalledWithEmptyColumn() throws Exception {
            Board boardClass = new Board(WIDTH, HEIGHT);
            int column = 1;

            int[][] board = boardClass.dropPiece(column, PLAYER1);
            Assertions.assertEquals(board[column][0], PLAYER1);
    }

    @Test
    void dropPiece_putsPieceInSecondRowOfFirstColumn_whenCalledOnColumnWithASinglePiece() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);
        int column = 1;
        boardClass.dropPiece(column, PLAYER1);

        int[][] turnTwoBoard = boardClass.dropPiece(column, PLAYER2);

        Assertions.assertEquals(turnTwoBoard[column][1], PLAYER2);
    }

    @Test
    void dropPiece_throwsException_whenCalledOnAFullColumn() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);
        int column = 2;
        for(int i = 0; i < HEIGHT; i++) {
            boardClass.dropPiece(column, PLAYER1);
        }

        Assertions.assertThrows(Exception.class, () -> boardClass.dropPiece(column, PLAYER1));
    }

    @Test
    void dropPiece_throwsException_whenCalledWithColumnLessThanZero() {
        Board boardClass = new Board(WIDTH, HEIGHT);
        Assertions.assertThrows(IllegalArgumentException.class, () -> boardClass.dropPiece(-1, PLAYER1));
    }

    @Test
    void dropPiece_throwsException_whenCalledWithColumnGreaterThanBoardWidth() {
        Board boardClass = new Board(WIDTH, HEIGHT);
        Assertions.assertThrows(IllegalArgumentException.class, () -> boardClass.dropPiece(WIDTH, PLAYER2));
    }

    @Test
    void canContinue_returnsTrue_whenBoardIsEmpty() {
        Board boardClass = new Board(WIDTH, HEIGHT);
        Assertions.assertTrue(boardClass.canContinue());
    }

    @Test
    void canContinue_returnsFalse_whenBoardIsFull() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);
        for(int i = 0; i < WIDTH; i++) {
            for(int j = 0; j < HEIGHT; j++) {
                boardClass.dropPiece(i, PLAYER2);
            }
        }

        Assertions.assertFalse(boardClass.canContinue());
    }

    @Test
    void boardHasWinCondition_returnsFalse_whenBoardIsEmpty() {
        Board boardClass = new Board(WIDTH, HEIGHT);
        int winCondition = 4;

        Assertions.assertFalse(boardClass.boardHasWinCondition(boardClass.getBoard(), winCondition));
    }

    @Test
    void boardHasWinCondition_returnsTrue_whenFourInARowInBottomRow() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);

        boardClass.dropPiece(0, PLAYER1);
        boardClass.dropPiece(1, PLAYER1);
        boardClass.dropPiece(2, PLAYER1);
        boardClass.dropPiece(3, PLAYER1);

        Assertions.assertTrue(boardClass.boardHasWinCondition(boardClass.getBoard(), 4));
    }

    @Test
    void boardHasWinCondition_returnsFalse_whenNotThreeInARowOnBottomRow() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);

        boardClass.dropPiece(0, PLAYER1);
        boardClass.dropPiece(1, PLAYER1);
        boardClass.dropPiece(2, PLAYER1);

        Assertions.assertFalse(boardClass.boardHasWinCondition(boardClass.getBoard(), 4));
    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2", "3", "4", "5", "6"})
    void boardHasWinCondition_returnsTrue_whenFourInARowInColumn(int column) throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);

        boardClass.dropPiece(column, PLAYER1);
        boardClass.dropPiece(column, PLAYER1);
        boardClass.dropPiece(column, PLAYER1);
        boardClass.dropPiece(column, PLAYER1);

        Assertions.assertTrue(boardClass.boardHasWinCondition(boardClass.getBoard(), 4));
    }

    @ParameterizedTest
    @CsvSource({"0", "1", "2", "3", "4", "5", "6"})
    void boardHasWinCondition_returnsFalse_whenThreeInARowInColumn(int column) throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);

        boardClass.dropPiece(column, PLAYER1);
        boardClass.dropPiece(column, PLAYER1);
        boardClass.dropPiece(column, PLAYER1);

        Assertions.assertFalse(boardClass.boardHasWinCondition(boardClass.getBoard(), 4));
    }

    @Test
    void boardHasWinCondition_returnsTrue_whenFourInARowDiagonallyLeft() {
        Board boardClass = new Board(WIDTH, HEIGHT);

        int[][] board = new int[][] {
                {1, 0, 0, 0},
                {2, 1, 0, 0},
                {2, 2, 1, 0},
                {2, 2, 2, 1}
        };

        Assertions.assertTrue(boardClass.boardHasWinCondition(board, 4));
    }

    @Test
    void boardHasWinCondition_returnsTrue_whenFourInARowDiagonallyRight() {
        Board boardClass = new Board(WIDTH, HEIGHT);

        int[][] board = new int[][] {
                {2, 2, 2, 1},
                {2, 2, 1, 0},
                {2, 1, 0, 0},
                {1, 0, 0, 0}
        };

        Assertions.assertTrue(boardClass.boardHasWinCondition(board, 4));
    }

    @Test
    void isValidMove_returnsTrue_whenBoardIsEmpty() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);
        Assertions.assertTrue(boardClass.isValidMove(0));
    }

    @Test
    void invalidMove_returnsFalse_whenCalledOnAFullColumn() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);
        int column = 2;
        for(int i = 0; i < HEIGHT; i++) {
            boardClass.dropPiece(column, PLAYER1);
        }

        Assertions.assertFalse(boardClass.isValidMove(2));
    }

    @Test
    void invalidMove_returnsFalse_whenCalledWithColumnLessThanZero() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);
        Assertions.assertFalse(boardClass.isValidMove(-1));
    }

    @Test
    void invalidMove_returnsFalse_whenCalledWithColumnGreaterThanBoardWidth() throws Exception {
        Board boardClass = new Board(WIDTH, HEIGHT);
        Assertions.assertFalse(boardClass.isValidMove(WIDTH));
    }

    @Test
    void boardHasWinCondition_returnsFalse_whenWinConditionIsLargerThatBoardWidthAndHeight() {
        Board boardClass = new Board(2, 2);

        Assertions.assertFalse(boardClass.boardHasWinCondition(boardClass.getBoard(), 3));
    }
}
