package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;
import java.util.LinkedList;

public class Clock {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Clock hours stop end"));
        } else {
            int hours = Integer.parseInt(args[0]);
            int start = Integer.parseInt(args[1]);
            int end = Integer.parseInt(args[2]);
            ClockConfig clockConfig = new ClockConfig(hours, start, end);
            Solver solver = new Solver();
            System.out.println("Hours: " + hours + ", Start: " + start + ", End: " + end );
            int step = 0;
            Collection<Configuration> path = solver.solve(clockConfig);
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
    }

    public static class ClockConfig implements Configuration {
        /** Total number of hours on the clock */
        private final int hours;
        /** The start hour */
        private final int start;
        /** The destination hour */
        private final int end;
        /** The list of neighbors of the current hour */
        private final Collection<Configuration> neighbors = new LinkedList<>();

        /**
         * Constructor that initialize start, end, and hours.
         * @param hours Total number of hours on the clock
         * @param start The start hour
         * @param end The destination hour
         */
        public ClockConfig(int hours, int start, int end){
            this.hours = hours;
            this.start = start;
            this.end = end;
        }

        /**
         * @return whether the current configuration is goal or not
         */
        @Override
        public boolean isSolution() {
            return this.start == this.end;
        }

        /**
         * @return generate and return a list of neighbors of the current configuration.
         */
        @Override
        public Collection<Configuration> getNeighbors() {
            int moreStart = (start + 1) % hours;
            int lessStart = (start - 1) % hours;
            if (moreStart == 0){
                moreStart = hours;
            }
            if (lessStart == 0){
                lessStart = hours;
            }

            this.neighbors.add(new ClockConfig(hours, moreStart, end));
            this.neighbors.add(new ClockConfig(hours, lessStart, end));

            return this.neighbors;
        }

        /**
         * @param other other configuration to compare to
         * @return whether two configuration are the same
         */
        @Override
        public boolean equals(Object other) {
            if (other instanceof ClockConfig o){
                return this.start == o.start && this.end == o.end && this.hours == o.hours;
            }
            return false;
        }

        /**
         * @return generate a unique hash code for the current configuration
         */
        @Override
        public int hashCode() {
            return this.start + this.end + this.hours;
        }

        /**
         * @return a string representation of the start hour
         */
        @Override
        public String toString() {
            return String.valueOf(this.start);
        }
    }
}
