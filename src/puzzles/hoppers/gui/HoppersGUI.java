package puzzles.hoppers.gui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author nsh1507
 */

public class HoppersGUI extends Application implements Observer<HoppersModel, String> {
    /** The size of all icons, in square dimension */
    private final static int ICON_SIZE = 75;
    /** the font size for labels and buttons */
    private final static int FONT_SIZE = 12;

    /** The resources directory is located directly underneath the gui package */
    private final static String RESOURCES_DIR = "resources/";

    // for demonstration purposes
    /**
     * the image of the red frog for the UI
     */
    private final Image redFrog = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "red_frog.png")));
    /**
     * the image of the green frog for the UI
     */
    private final Image greenFrog = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "green_frog.png")));
    /**
     * the image of the lily pad for the UI
     */
    private final Image lily_pad = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "lily_pad.png")));
    /**
     * the image of the water for the UI
     */
    private final Image water = new Image(Objects.requireNonNull(getClass().getResourceAsStream(RESOURCES_DIR + "water.png")));

    /**
     * the stage representing the game
     */
    private Stage stage;
    /**
     * the model that handles the logic
     */
    private HoppersModel model;
    /**
     * display the status of the game on top of the board
     */
    private Label status;
    /**
     * the board to play
     */
    private BorderPane borderPane;


    /**
     * Initialize the model and register the UI as it's observer
     */
    public void init() {
        String filename = getParameters().getRaw().get(0);
        try {
            this.model = new HoppersModel(filename);
        } catch (IOException ignored) {}
        model.addObserver(this);
    }

    /**
     * Generate the starter board
     * @param stage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.borderPane = new BorderPane();

        HBox hBox = new HBox();
        Button reset = new Button("Reset");
        reset.setOnAction(event -> model.reset());
        Button hint = new Button("Hint");
        hint.setOnAction(event -> model.hint());
        Button load = new Button("Load");


        load.setOnAction(event -> {


            FileChooser fileChooser = new FileChooser();
            String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
            currentPath += File.separator + "data" + File.separator + "hoppers";
            fileChooser.setInitialDirectory(new File(currentPath));
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                String fileName = "data/hoppers/" + file.getName();
                model.load(fileName);
                borderPane.setCenter(center());
                stage.sizeToScene();
            }

        });

        hBox.getChildren().addAll(load,reset,hint);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hBox);

        this.status = new Label();
        status.setMinSize(FONT_SIZE,FONT_SIZE);
        BorderPane.setAlignment(status, Pos.CENTER);
        borderPane.setTop(status);

        GridPane center = center();
        borderPane.setCenter(center);

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Generate the board for the game
     * @return a new board for the game
     */
    private GridPane center(){
        GridPane gridPane = new GridPane();
        String [] config = model.getCurrentConfig().toString().strip().split("\n");
        for (int r = 0; r < config.length; r++){
            String[] charList = config[r].split("\\s+");
            for (int c = 0; c < charList.length; c++){
                Button button = new Button();
                int finalR = r;
                int finalC = c;
                if (charList[c].charAt(0) == 'G'){
                    button.setGraphic(new ImageView(greenFrog));
                }
                else if (charList[c].charAt(0) == 'R'){
                    button.setGraphic(new ImageView(redFrog));
                }
                else if (charList[c].charAt(0) == '.'){
                    button.setGraphic(new ImageView(lily_pad));
                }
                else if (charList[c].charAt(0) == '*'){
                    button.setGraphic(new ImageView(water));
                }
                button.setOnAction(event -> model.select(finalR, finalC));
                button.setMinSize(ICON_SIZE, ICON_SIZE);
                button.setMaxSize(ICON_SIZE, ICON_SIZE);
                gridPane.add(button, c, r);
            }
        }

        return gridPane;
    }

    /**
     * Update the board to a new one when the user interact with the game
     * @param hoppersModel the object that wishes to inform this object
     *                about something that has happened.
     * @param msg optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(HoppersModel hoppersModel, String msg) {
        this.status.setText(msg);
        model = hoppersModel;
        borderPane.setCenter(center());
        this.stage.sizeToScene();  // when a different sized puzzle is loaded
    }

    /**
     * The main program that takes in a filename and initialize the game
     * @param args the filename
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            Application.launch(args);
        }
    }
}
