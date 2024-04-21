package puzzles.strings;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;

/**
 * @author Nam Huynh
 */

public class Strings {
    /**
     * The constructor that takes in the initial and desired strings
     * @param args the command line arguments that contains the initial and desired strings
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {
            StringsConfig stringsConfig = new StringsConfig(args[0], args[1]);
            System.out.println("Start: " + args[0] + ", End: " + args[1]);
            Solver solver = new Solver();
            Collection<Configuration> path = solver.solve(stringsConfig);
            int step = 0;
            if (path == null){
                System.out.println("No solution");
                return;
            }
            for (Configuration configuration: path){
                System.out.println("Steps " + step + ": " + configuration);
                step +=1 ;
            }
        }
    }
}
