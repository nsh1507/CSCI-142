package puzzles.chess.solver;

import puzzles.chess.model.ChessConfig;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

public class Chess {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Chess filename");
        } else {
            try {
                Configuration start = new ChessConfig(args[0]);
                Solver solver = new Solver();
                Collection<Configuration> path = solver.solve(start);
                solver.configsPrint();
                for (int i = 0; i < (path).size(); i++){
                    System.out.println("Step " + i + ": ");
                    System.out.println(((LinkedList<Configuration>)path).get(i));
                }

            } catch (IOException e){}



        }
    }
}
