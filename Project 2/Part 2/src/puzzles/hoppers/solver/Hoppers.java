package puzzles.hoppers.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.IOException;
import java.util.Collection;

/**
 * @author nsh1507
 */

public class Hoppers {
    /**
     * Takes in a file name and generate the solution of the puzzle with the shortest path
     * @param args the filename
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }
        try {
            HoppersConfig hoppersConfig = new HoppersConfig(args[0]);
            System.out.println("File: " + args[0]);
            System.out.println(hoppersConfig);
            Solver solver = new Solver();
            int step = 0;
            Collection<Configuration> path = solver.solve(hoppersConfig);
            solver.configsPrint();
            if (path.size() == 0){
                System.out.println("No solution");
                return;
            }
            for (Configuration configuration: path){
                System.out.println("Steps " + step + ": " + configuration);
                step +=1 ;
            }
        }
        catch (IOException ignored) {}
    }
}
