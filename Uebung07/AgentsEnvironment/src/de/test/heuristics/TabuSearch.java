package de.test.heuristics;

import de.competition.thesis.heuristics.Heuristic;
import de.competition.thesis.models.Configuration;
import de.competition.thesis.models.ProblemDefinition;

import java.util.ArrayList;
import java.util.List;

public class TabuSearch  extends Heuristic {


    int counter = 0;
    private ProblemDefinition best_structure;

    public TabuSearch(ProblemDefinition problemDefinition){
        super(problemDefinition);
    }



    @Override
    public Configuration optimize(Configuration old_configuration) {
        int old_fitness = calculateFitness(old_configuration);
        List<Integer> ringBuffer = new ArrayList<>();
        ringBuffer.add(old_configuration.hashCode());
        while (!this.checkAbortCondition()) {
            counter++;
            Configuration new_configuration = neighbourFinder.findNeighbour(old_configuration);
            while(ringBuffer.contains(new_configuration.hashCode())){
                new_configuration = neighbourFinder.findNeighbour(old_configuration);
            }

            int new_fitness = calculateFitness(new_configuration);
            ringBuffer.add(new_configuration.hashCode());

            if (new_fitness < old_fitness) {
                old_configuration = new_configuration;
                old_fitness = new_fitness;
            } else {
                loadTemps();
            }
            if(ringBuffer.size() >= 50){
                ringBuffer.remove(0);
            }
        }
        return old_configuration;
    }

public boolean checkAbortCondition() {
    if(counter<= 100){
        return false;
    }
    return true;
}

}
