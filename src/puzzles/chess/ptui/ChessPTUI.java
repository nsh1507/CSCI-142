package puzzles.chess.ptui;

import puzzles.common.Observer;
import puzzles.chess.model.ChessModel;

import java.io.IOException;
import java.util.Scanner;

/**
 * PTUI implementation of the chess puzzle game
 * @author Alex Eng
 */
public class ChessPTUI implements Observer<ChessModel, String> {
    /**
     * The model
     */
    private ChessModel model;

    /**
     *
     * @param filename Initializes the model and adds the PTUI as an observer of the model
     * @throws IOException invalid filename
     */
    public void init(String filename) throws IOException {
        this.model = new ChessModel(filename);
        this.model.addObserver(this);
        displayHelp();
    }

    /**
     * Updates the PTUI to align with user input
     * @param model the object that wishes to inform this object
     *                about something that has happened.
     * @param data optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(ChessModel model, String data) {
        // for demonstration purposes
        System.out.println(data);
        System.out.println(model);
    }

    /**
     * Displays the commands a user can input
     */
    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    /**
     * Runs the PTUI and takes user input
     */
    public void run() {
        Scanner in = new Scanner(System.in);
        for (; ; ) {
            System.out.print("> ");
            String line = in.nextLine();
            String[] words = line.split("\\s+");
            if (words.length > 0) {
                if (words[0].startsWith("q")) {
                    break;
                } else if (words[0].startsWith("h")) {
                    this.model.getHint();
                } else if (words[0].startsWith("l")) {
                    this.model.load(line);
                } else if (words[0].startsWith("s")) {
                    this.model.select(line);
                } else if (words[0].startsWith("r")) {
                    this.model.reset();
                } else {
                    displayHelp();
                }
            }
        }
    }

    /**
     * Main method, launches application
     * @param args command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java ChessPTUI filename");
        } else {
            try {
                ChessPTUI ptui = new ChessPTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}

