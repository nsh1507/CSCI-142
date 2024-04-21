package puzzles.hoppers.ptui;

import puzzles.common.Observer;
import puzzles.hoppers.model.HoppersModel;


import java.io.IOException;
import java.util.Scanner;

/**
 * @author nsh1507
 */

public class HoppersPTUI implements Observer<HoppersModel, String> {
    /**
     * the model
     */
    private HoppersModel model;

    /**
     * Initialize the model and register the UI as it's observer
     * @param filename the filename to parse through
     * @throws IOException if the file is found or not
     */
    public void init(String filename) throws IOException {
        this.model = new HoppersModel(filename);
        this.model.addObserver(this);
        displayHelp();
    }

    /**
     * update method to generate the new state of the game when user interacted with the UI
     * @param model the object that wishes to inform this object
     *                about something that has happened.
     * @param data optional data the server.model can send to the observer
     *
     */
    @Override
    public void update(HoppersModel model, String data) {
        // for demonstration purposes
        System.out.println(data);
        System.out.println(model);
    }

    /**
     * the options to play the game
     */
    private void displayHelp() {
        System.out.println( "h(int)              -- hint next move" );
        System.out.println( "l(oad) filename     -- load new puzzle file" );
        System.out.println( "s(elect) r c        -- select cell at r, c" );
        System.out.println( "q(uit)              -- quit the game" );
        System.out.println( "r(eset)             -- reset the current game" );
    }

    /**
     * Interpret the user's input and decides to call the appropriate model's method for it
     */
    public void run() {
        Scanner in = new Scanner( System.in );
        for ( ; ; ) {
            System.out.print( "> " );
            String line = in.nextLine();
            String[] words = line.split( "\\s+" );
            if (words.length > 0) {
                if (words[0].startsWith( "q" )) {
                    System.out.println();
                    break;
                }
                else if (words[0].startsWith( "h" )) {
                    model.hint();
                }
                else if (words[0].startsWith( "s" )) {
                    model.select(Integer.parseInt(words[1]), Integer.parseInt(words[2]));
                }
                else if (words[0].startsWith( "r" )) {
                    model.reset();
                }
                else if (words[0].startsWith( "l" )) {
                    model.load(words[1]);
                }
                else {
                    displayHelp();
                }
            }
        }
    }

    /**
     * Constructor that takes in the file name and run the game
     * @param args the filename
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java HoppersPTUI filename");
        } else {
            try {
                HoppersPTUI ptui = new HoppersPTUI();
                ptui.init(args[0]);
                ptui.run();
            } catch (IOException ioe) {
                System.out.println(ioe.getMessage());
            }
        }
    }
}
