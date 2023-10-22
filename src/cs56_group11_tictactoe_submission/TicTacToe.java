package cs56_group11_tictactoe_submission;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TicTacToe extends Application 
{
    private static GridPane gameBoard;
    private AnimationTimer gameTimer;
    private BorderPane root;
    private BorderPane startMenu;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        //Creates starting page
        startMenu = new BorderPane();
        startMenu.setPrefSize(600, 600);
        
        Label welcomeMessage = new Label("Play Tic Tac Toe!");
        welcomeMessage.setFont(new Font(30));
        
        Label choiceOfOpponent = new Label("Who do you want to play against?");
        choiceOfOpponent.setFont(new Font(20));
        
        Button playAgainstHuman = new Button("Play against another person");
        playAgainstHuman.setFont(new Font(15));
        
        VBox startLayout = new VBox(5);
        startLayout.getChildren().addAll(welcomeMessage, choiceOfOpponent, playAgainstHuman);
        startLayout.setAlignment(Pos.CENTER); //Vertical alignment of the contents in the starting page to the center
        
        
        HBox layout1 = new HBox(5);
        layout1.getChildren().add(startLayout);
        layout1.setAlignment(Pos.CENTER); //Horizontal alignment of the contents in the starting page to the center
        
        startMenu.setCenter(layout1);
        
        Scene scene1 = new Scene(startMenu);
        
        //Creates main scene with the Tic-Tac-Toe grid
        root = new BorderPane();
        root.setPrefSize(600, 600);
        root.setCenter(generateGUI());
        
        //Shows instructions and "Back to start page" button on left side of GUI
        VBox layout2 = new VBox(5);
        Label instructions = new Label("Player 1's mark: X\nClick the left button on the mouse to place your mark."
                + "\nPlayer 2's mark: O\nClick the right button on the mouse to place your mark.");
        Button back = new Button("Back to start page");
        layout2.getChildren().addAll(instructions, back);
        root.setLeft(layout2);
        
        Scene gameScene = new Scene(root);
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene1);
        
        //User starts game against another person when "Play against another person" button is clicked
        playAgainstHuman.setOnAction(e-> primaryStage.setScene(gameScene));
        
        //Game is reset and user goes back to starting page when "Back to start page" button is clicked
        back.setOnAction(e->{
            resetGame();
            primaryStage.setScene(scene1);
        });
        
        runGame();
        primaryStage.show();
    }

   //Generates Tic-Tac-Toe Board
    private static GridPane generateGUI() {
        gameBoard = new GridPane();
        Tile.board = new Board();
        gameBoard.setAlignment(Pos.CENTER);
        
        for (int row = 0; row < Tile.board.getWidth(); row++) {
            for (int col = 0; col < Tile.board.getWidth(); col++) {
                Tile tile = new Tile(row, col, Tile.board.getMarkAt(row, col));
                //Sets the column,row indices for the child when contained in a GridPane
                GridPane.setConstraints(tile, col, row); 
                //Adding new tile
                gameBoard.getChildren().add(tile);
            }
        }
        return gameBoard;
    }

    //Runs the game
    private void runGame() {
        gameTimer = new AnimationTimer() {
            //Animation Timer is an Abstract class
            //Overriding method by using Anonymous class
            @Override
            public void handle(long now) {
                if (Tile.board.isGameOver()) {
                    endGame();
                }
            }
        };
        gameTimer.start(); //Starts game animation
    }
    
    //Resets the game by generating a new empty Tic-Tac-Toe board
    private void resetGame() {
        root.setCenter(generateGUI());
    }
    
    private void endGame() {
        gameTimer.stop(); //Ends game animation
        showEndMenu();
    }
    
    private void showEndMenu() {
        //Creates an alert with no specified alert type
        //Alert will ask if user wants to play another round or quit the game
        Alert gameOverAlert = new Alert(AlertType.NONE);
        Mark winner = Tile.board.getWinningMark();
        
        ButtonType reset = new ButtonType("Play again");
        ButtonType quit = new ButtonType("Quit");

        //Remove default ButtonTypes and add custom ButtonTypes
        gameOverAlert.getButtonTypes().clear();
        gameOverAlert.getButtonTypes().addAll(reset, quit);
                
        gameOverAlert.setTitle("Game Over");
        gameOverAlert.setHeaderText(null);
        if (winner == Mark.BLANK) {
            gameOverAlert.setContentText("Tie!\nAnother round?"); 
        }
        else {
            gameOverAlert.setContentText(winner + " wins!\nAnother round?");
        }
        
        gameOverAlert.setOnHidden(e -> {
            //Gets which button was clicked on the alert
            ButtonType result = gameOverAlert.getResult();
            if (result != null && result == reset) {
                //Pops up a new window and resets a game
                gameOverAlert.close();
                resetGame();
                runGame();
            }
            else {
                //Closes windows
                Platform.exit();
            }
        });
        
        gameOverAlert.show();
    }
}