package cs56_group11_tictactoe_submission;

public class Board {
    Mark[][] board;
    private Mark winningMark;
    private final int BOARD_WIDTH = 3;
    private boolean xTurn, gameOver;

    public Board() {
        this.board = new Mark[BOARD_WIDTH][BOARD_WIDTH];
        this.xTurn = true;
        this.gameOver = false;
        this.winningMark = Mark.BLANK;
        
        //Marks board initially Blank
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                board[row][col] = Mark.BLANK;
            }
        }
    }

    public boolean placeMark(int row, int col) {
        if (row < 0 || row >= BOARD_WIDTH || col < 0 || col >= BOARD_WIDTH || isTileMarked(row, col) || gameOver) {
            return false;
        }
        board[row][col] = xTurn ? Mark.X : Mark.O;
        togglePlayer();
        checkWin(row, col);
        return true;
    }

 
    private void checkWin(int row, int col) {
        int rowSum = 0;
        // Check row for winner.
        for (int c = 0; c < BOARD_WIDTH; c++) {
            rowSum += getMarkAt(row, c).getMark();
        }
        if (calcWinner(rowSum) != Mark.BLANK) {
            return;
        }

        // Check column for winner.
        rowSum = 0;
        for (int r = 0; r < BOARD_WIDTH; r++) {
            rowSum += getMarkAt(r, col).getMark();
        }
        if (calcWinner(rowSum) != Mark.BLANK) {
            return;
        }

        // Top-left to bottom-right diagonal.
        rowSum = 0;
        for (int i = 0; i < BOARD_WIDTH; i++) {
            rowSum += getMarkAt(i, i).getMark();
        }
        if (calcWinner(rowSum) != Mark.BLANK) {
            return; 
        }

        // Top-right to bottom-left diagonal.
        rowSum = 0;
        int indexMax = BOARD_WIDTH - 1;
        for (int i = 0; i <= indexMax; i++) {
            rowSum += getMarkAt(i, indexMax - i).getMark();
        }
        
        if (calcWinner(rowSum) != Mark.BLANK) {
            return;
        }

        if (!anyMovesAvailable()) {
            gameOver = true;
        }
    }
    
    //Helper function to check winner
    private Mark calcWinner(int rowSum) {
        int winX = Mark.X.getMark() * BOARD_WIDTH;
        int winO = Mark.O.getMark() * BOARD_WIDTH;
        if (rowSum == winX) {
            gameOver = true;
            winningMark = Mark.X;
            return Mark.X;
        }
        else if (rowSum == winO) {
            gameOver = true;
            winningMark = Mark.O;
            return Mark.O;
        }
        return Mark.BLANK;
    }

    //Toggling Player
    public void togglePlayer() {
        xTurn = !xTurn;
    }

    //Check whether there still cells to mark
    //Returns true, if there are moves available
    public boolean anyMovesAvailable() {
        for (int row = 0; row < BOARD_WIDTH; row++) {
            for (int col = 0; col < BOARD_WIDTH; col++) {
                if (!isTileMarked(row, col)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    //Returns position to mark
    public Mark getMarkAt(int row, int column) {
        return board[row][column];
    }

    public boolean isTileMarked(int row, int column) {
        return board[row][column].isMarked();
    }

    public void setMarkAt(int row, int column, Mark newMark) {
        board[row][column] = newMark;
    }

    public boolean isCrossTurn() {
        return xTurn;
    }
    
    public void xTurn(boolean a){
        this.xTurn = a;
    }

    public int getWidth() {
        return BOARD_WIDTH;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Mark getWinningMark() {
        return winningMark;
    }
    
    public void setWinningMark(Mark k){
        winningMark = k;
    }
}