package gurdle.gui;

import gurdle.CharChoice;
import gurdle.Model;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.Observer;

import java.util.HashMap;

/**
 * The graphical user interface to the Wordle game model in
 * {@link Model}.
 *
 * @author Nam Huynh
 */
public class Gurdle extends Application
        implements Observer< Model, String > {
    /** the model */
    private Model model;
    /** the 2d array the update method use to access the display */
    private Label[][] labelGrid;
    /** the headers containing # of guesses, state of the game, and the secret word */
    private Label[] topList;
    /** a hashmap use to get the button of a specific letter*/
    private final HashMap<String, Button> keyBoardMap = new HashMap<>();

    /**
     * initialize the model
     * add this class to be an observer of the model
     */
    @Override
    public void init() {
        this.model = new Model();
        model.newGame();
        model.addObserver(this);
    }

    /**
     * Initialize the stage, or the UI
     * @param mainStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start( Stage mainStage ) {
        this.labelGrid = new Label[6][5];
        mainStage.setTitle("GURDLE");
        BorderPane grid = new BorderPane();
        grid.setTop(top());
        grid.setBottom(bottom());
        grid.setCenter(center());
        Scene scene = new Scene(grid);
        mainStage.setScene(scene);
        mainStage.setWidth(480);
        mainStage.setHeight(650);
        mainStage.show();
    }

    /**
     * Make the headers.
     * and the secret if the user press the cheat buttons
     * @return the headers
     */
    private BorderPane top(){
        BorderPane borderPane = new BorderPane();
        this.topList = new Label[3];
        Label guessLabel = new Label("#guesses: " + model.numAttempts());
        Label stateLabel = new Label("Make a guess!");
        Label secret = new Label("    ");
        topList[0] = guessLabel;
        topList[1] = stateLabel;
        topList[2] = secret;
        borderPane.setLeft(guessLabel);
        borderPane.setCenter(stateLabel);
        borderPane.setRight(secret);
        return borderPane;
    }

    /**
     * @return Borderpane of keyboard, new game, cheat, and enter buttons
     */
    private BorderPane bottom(){
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(keyboard());
        HBox hbox = new HBox();
        Button newGameButton = new Button("NEW GAME");
        Button cheatButton = new Button("CHEAT");
        newGameButton.setOnAction(event -> {
            topList[2].setText("");
            model.newGame();
            borderPane.setCenter(keyboard());
            });
        cheatButton.setOnAction(event -> topList[2].setText(model.secret()));
        hbox.getChildren().addAll(newGameButton, cheatButton);

        borderPane.setBottom(hbox);
        hbox.setAlignment(Pos.CENTER);
        Button enter = new Button("ENTER");
        enter.setOnAction(event -> model.confirmGuess());
        borderPane.setRight(enter);
        return borderPane;
    }

    /**
     * @return the keyboard
     */
    private GridPane keyboard(){
        GridPane keyNode = new GridPane();
        String line = "QWERTYUIOPASDFGHJKLZXCVBNM";
        int col = 0;
        int row = 0;
        for (int i = 0; i < line.length(); i++){
            Button button = new Button(String.valueOf(line.charAt(i)));
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            keyBoardMap.put(String.valueOf(line.charAt(i)), button);
            button.setStyle("-fx-border-color: black;");
            button.setBackground(new Background( new BackgroundFill( Color.WHITE, null, null ) ));
            int finalI = i;
            button.setOnAction(event -> model.enterNewGuessChar(line.charAt(finalI)));
            button.setMinWidth(40);
            button.setMinHeight(40);
            keyNode.add(button, col , row);
            col +=1;
            if (i == 9 || i == 18){
                row += 1;
                col = 0;
            }
        }
        keyNode.setStyle( "-fx-font: 18px Menlo" );
        return keyNode;
    }

    /**
     * @return the display of the guesses in the center of Gurdle
     */
    private BorderPane center(){
        BorderPane center = new BorderPane();
        GridPane gridPane = new GridPane();
        for (int col = 0; col < 5; col++){
            for (int row = 0; row < 6; row++){
                Label myLetterLabel = new Label();
                this.labelGrid[row][col] = myLetterLabel;

                myLetterLabel.setMinSize(55,60);
                myLetterLabel.setStyle( "-fx-border-color: black;");
                myLetterLabel.setAlignment(Pos.CENTER);
                gridPane.add(myLetterLabel, col,row);
            }
        }
        gridPane.setStyle( "-fx-font: 18px Menlo" );
        gridPane.setAlignment(Pos.CENTER);
        center.setCenter(gridPane);
        return center;
    }

    /**
     * Update method when the subject notify the observer.
     * @param model the object that wishes to inform this object
     *                about something that has happened.
     * @param message    optional data the model can send to the observer
     */
    @Override
    public void update( Model model, String message ) {
        for (int row = 0; row < 6; row++){
            for (int col = 0; col < 5; col ++){
                CharChoice letter = model.get(row, col);
                this.labelGrid[row][col].setText(String.valueOf(letter));
                topList[1].setText(message);
                switch (letter.getStatus()){
                    case WRONG -> this.labelGrid[row][col].setBackground(new Background( new BackgroundFill( Color.LIGHTGRAY, null, null)));
                    case RIGHT_POS -> this.labelGrid[row][col].setBackground(new Background( new BackgroundFill( Color.LIGHTGREEN, null, null ) ));
                    case WRONG_POS -> this.labelGrid[row][col].setBackground(new Background( new BackgroundFill( Color.BURLYWOOD, null, null ) ));
                    case EMPTY -> this.labelGrid[row][col].setBackground(new Background( new BackgroundFill( Color.WHITE, null, null ) ));
                }
                if (model.usedLetter(letter.getChar())){
                    if (letter.getStatus().equals(CharChoice.Status.WRONG_POS)){
                        keyBoardMap.get(String.valueOf(letter)).setBackground(new Background( new BackgroundFill( Color.BURLYWOOD, null, null)));}
                    if (letter.getStatus().equals(CharChoice.Status.RIGHT_POS)){
                        keyBoardMap.get(String.valueOf(letter)).setBackground(new Background( new BackgroundFill( Color.LIGHTGREEN, null, null)));}
                    if (letter.getStatus().equals(CharChoice.Status.WRONG)) {
                        keyBoardMap.get(String.valueOf(letter)).setBackground(new Background( new BackgroundFill( Color.LIGHTGRAY, null, null)));}
                }
            }
            topList[0].setText("#guess: " + model.numAttempts());
        }

    }


    public static void main( String[] args ) {
        if ( args.length > 1 ) {
            System.err.println( "Usage: java Gurdle [1st-secret-word]" );
        }
        Application.launch( args );
    }
}
