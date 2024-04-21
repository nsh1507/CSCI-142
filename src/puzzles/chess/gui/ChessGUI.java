package puzzles.chess.gui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * GUI implementation for the chess puzzle game
 * @author Alex Eng
 */

public class ChessGUI extends Application implements Observer<ChessModel, String> {
    private ChessModel model;

    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int FONT_SIZE = 12;

    /** The stage of the GUI */
    private Stage stage;

    /** Resource library for chess */
    private final static String RESOURCES_DIR = "resources/";

    private final Image bishop =
            new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "bishop.png")));

    private final Image king =
            new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "king.png")));

    private final Image knight =
            new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "knight.png")));

    private final Image queen =
            new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "queen.png")));

    private final Image pawn =
            new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "pawn.png")));

    private final Image rook =
            new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "rook.png")));

    /** a definition of light and dark and for the button backgrounds */
    private static final Background LIGHT =
            new Background( new BackgroundFill(Color.WHITE, null, null));
    private static final Background DARK =
            new Background( new BackgroundFill(Color.MIDNIGHTBLUE, null, null));

    /** GUI elements */
    private BorderPane background;

    private GridPane board;

    private Label status;

    /**
     * Creates the chess board
     */
    protected void makeBoard(){
        Background squareColor = DARK;
        String [] config = model.configToString().strip().split("\n");
        for ( int r = 0; r < config.length; r++){
            String[] pieceList = config[r].split("\\s+");
            for (int c = 0; c < pieceList.length; c++){
                int Rselect = r;
                int Cselect = c;
                Button button = new Button();
                button.setBackground(squareColor);
                if (pieceList.length % 2 == 0){
                    if (squareColor.equals(DARK)){
                        if (c == pieceList.length-1){
                            squareColor = DARK;
                        } else {
                            squareColor = LIGHT;
                        }
                    } else {
                        if (c == pieceList.length-1){
                            squareColor = LIGHT;
                        } else {
                            squareColor = DARK;
                        }
                    }
                } else {
                    if (squareColor.equals(DARK)){
                        squareColor = LIGHT;
                    } else {
                        squareColor = DARK;
                }
            }
                switch (pieceList[c].charAt(0)){
                    case 'K' -> button.setGraphic(new ImageView(king));
                    case 'Q' -> button.setGraphic(new ImageView(queen));
                    case 'R' -> button.setGraphic(new ImageView(rook));
                    case 'B' -> button.setGraphic(new ImageView(bishop));
                    case 'N' -> button.setGraphic(new ImageView(knight));
                    case 'P' -> button.setGraphic(new ImageView(pawn));
                }
                button.setOnAction(event -> model.select("s " + Rselect + " " + Cselect));
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                this.board.add(button, c, r);
            }
        }
    }

    /**
     * Makes the load, reset, and hint buttons
     * @return HBox containing the load, reset, and hint buttons
     */
    protected HBox makeInputs(){
        Button reset = new Button();
        reset.setText("Reset");
        reset.setOnAction(event -> model.reset());

        Button load = new Button();
        load.setText("Load");
        load.setOnAction(event -> {
            FileChooser chooser = new FileChooser();
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            currentPath += File.separator + "data" + File.separator + "chess";
            chooser.setInitialDirectory(new File(currentPath));
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                String fileName = "data/chess/" + file.getName();
                model.load("l " + fileName);
                this.board = new GridPane();
                this.makeBoard();
                this.background.setCenter(this.board);
                this.stage.sizeToScene();
            }
        });

        Button hint = new Button();
        hint.setText("Hint");
        hint.setOnAction(event -> this.model.getHint());

        HBox inputs = new HBox(load, reset, hint);
        inputs.setAlignment(Pos.CENTER);
        return inputs;
    }

    /**
     * Initializes the GUI
     */
    @Override
    public void init() {
        // get the file name from the command line
        String filename = getParameters().getRaw().get(0);
        try {
            this.model = new ChessModel(filename);
        } catch (IOException e){
        }
        model.addObserver(this);
    }

    /**
     * Generates the starting view of the GUI
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.background = new BorderPane();

        this.background.setBottom(this.makeInputs());

        this.board = new GridPane();
        this.makeBoard();
        this.background.setCenter(this.board);

        this.status = new Label();
        BorderPane.setAlignment(status, Pos.CENTER);
        this.background.setTop(this.status);

        Scene scene = new Scene(this.background);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Updates the GUI to align with user input
     * @param chessModel the object that wishes to inform this object
     *                about something that has happened.
     * @param msg optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(ChessModel chessModel, String msg) {
        this.status.setText(msg);
        model = chessModel;
        this.makeBoard();
        this.stage.sizeToScene();  // when a different sized puzzle is loaded
    }

    /**
     * Main method, launched the application
     * @param args Command line argument
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}
