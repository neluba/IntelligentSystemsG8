package de.test.heuristics;

import de.competition.thesis.heuristics.Heuristic;
import de.competition.thesis.models.Configuration;
import de.competition.thesis.models.ProblemDefinition;

public class ThresholdAccepting extends Heuristic {

    int threshold = 10;
    int counter = 0;
    private ProblemDefinition best_structure;

    public ThresholdAccepting(ProblemDefinition problemDefinition) {
        super(problemDefinition);
    }

    @Override
    public Configuration optimize(Configuration configuration) {
        int fitness = calculateFitness(configuration);
        Configuration best_configuration = (Configuration) cloner.cloneObject(configuration);
        best_structure = (ProblemDefinition) cloner.cloneObject(problemDefinition);
        int best_fitness = fitness;
        while (!this.checkAbortCondition()) {
            Configuration new_configuration = neighbourFinder.findNeighbour(configuration);
            int new_fitness = calculateFitness(new_configuration);
            if (new_fitness <= fitness+ threshold) {
                configuration = (Configuration) cloner.cloneObject(new_configuration);
                fitness = new_fitness;
            }
            if (fitness < best_fitness) {
                best_structure = problemDefinition;
                best_configuration = (Configuration) cloner.cloneObject(configuration);
                best_fitness = fitness;
            } else {
                counter++;
                if(counter > 10) {
                    threshold = threshold - 1;
                    configuration = (Configuration) cloner.cloneObject(best_configuration);
                    fitness = best_fitness;
                    counter = 0;
                }
                loadTemps();
            }
        }
        problemDefinition = best_structure;
        return best_configuration;
    }

    public boolean checkAbortCondition() {
        if(threshold<= 0){
            return true;
        }
        return false;
    }


}
