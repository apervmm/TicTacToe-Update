package cs56_group11_tictactoe_submission;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;

public final class Tile extends Button {
    private final int row;
    private final int col;
    private Mark mark;
    public static Board board;

    //Constructor for tile class
    public Tile(int initRow, int initCol, Mark initMark) {
        this.row = initRow;
        this.col = initCol;
        this.mark = initMark;

        this.setOnMouseClicked((MouseEvent e)->{
            if(e.getButton() == MouseButton.PRIMARY){ //If Left Button on Mouse clicked
                if(!board.isCrossTurn()){
                    return;
                }
                board.xTurn(true); //Switching player
                board.placeMark(row, col);
                this.update();
            }
            else if(e.getButton() == MouseButton.SECONDARY){ //If Right Button on Mouse clicked
                if(board.isCrossTurn()){
                    return;
                }
                board.xTurn(false); //Switching player
                board.placeMark(row, col);
                this.update();
            }
        });
        
        //Modifying font size of Mark
        this.setStyle("-fx-font-size:70");
        //Aligning on center
        this.setTextAlignment(TextAlignment.CENTER);
        this.setMinSize(200.0, 200.0);
        this.setText("" + this.mark);
    }

    //Updates marked position
    public void update() {
        this.mark = board.getMarkAt(this.row, this.col);
        this.setText("" + mark);
    }
}