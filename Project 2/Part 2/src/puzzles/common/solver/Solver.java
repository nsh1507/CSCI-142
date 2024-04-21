package puzzles.common.solver;

import java.util.*;

/**
 * @author Nam Huynh
 */

public class Solver {
    private int totalConfigs;
    private int uniqueConfigs;
    /**
     * Generate the shortest path to the desired destination using BFS
     * @param start the starting configuration
     * @return the shortest path to the desired destination
     */
    public List<Configuration> solve (Configuration start){
        List<Configuration> queue = new LinkedList<>();
        Map<Configuration ,Configuration> predecessors = new HashMap<>();
        queue.add(start);
        predecessors.put(start, null);
        totalConfigs = 1;
        uniqueConfigs = 1;

        while (!queue.isEmpty()){
            Configuration current = queue.remove(0);
            if (!current.isSolution()){
                for (Configuration configuration: current.getNeighbors()){
                    totalConfigs +=1;
                    if (!predecessors.containsKey(configuration)){
                        uniqueConfigs += 1;
                        queue.add(configuration);
                        predecessors.put(configuration, current);
                    }
                }
            }
            else if(current.isSolution()){
                List<Configuration> path = new LinkedList<>();
                while (!current.equals(start)) {
                    path.add(0, current);
                    current = predecessors.get(current);
                }
                path.add(0, start);
                return path;
            }
        }
        return new LinkedList<>();
    }

    public void configsPrint(){
        System.out.println("Total Configs: " + totalConfigs);
        System.out.println("Unique Configs: " + uniqueConfigs);
    }
}
