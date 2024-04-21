package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;

/**
 * @author Nam Huynh
 */

public class Clock {
    /**
     * The constructor that takes in hours, start, and end.
     * @param args the command line arguments that contains hours, start, and end.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Clock start stop"));
        } else {
            int hours = Integer.parseInt(args[0]);
            int start = Integer.parseInt(args[1]);
            int end = Integer.parseInt(args[2]);
            ClockConfig clockConfig = new ClockConfig(hours, start, end);
            Solver solver = new Solver();
            System.out.println("Hours: " + hours + ", Start: " + start + ", End: " + end );
            int step = 0;
            Collection<Configuration> path = solver.solve(clockConfig);
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
