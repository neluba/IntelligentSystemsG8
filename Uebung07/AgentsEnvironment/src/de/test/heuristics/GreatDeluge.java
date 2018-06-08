package de.test.heuristics;

import de.competition.thesis.heuristics.Heuristic;
import de.competition.thesis.models.Configuration;
import de.competition.thesis.models.ProblemDefinition;

public class GreatDeluge extends Heuristic {
    private ProblemDefinition best_structure;
    int rain = 5;
    int level ;
    int counter = 100;

    public GreatDeluge(ProblemDefinition problemDefinition) {
        super(problemDefinition);
    }

    @Override
    public Configuration optimize(Configuration configuration) {
        int fitness = calculateFitness(configuration);
        Configuration best_configuration = (Configuration) cloner.cloneObject(configuration);
        best_structure = (ProblemDefinition) cloner.cloneObject(problemDefinition);
        int best_fitness = fitness;
        level = best_fitness+5;
        while (!this.checkAbortCondition()) {
            Configuration new_configuration = neighbourFinder.findNeighbour(configuration);
            int new_fitness = calculateFitness(new_configuration);
            if (new_fitness <= level) {
                configuration = (Configuration) cloner.cloneObject(new_configuration);
                fitness = new_fitness;
            }
            if (fitness < best_fitness) {
                best_structure = problemDefinition;
                best_configuration = (Configuration) cloner.cloneObject(configuration);
                best_fitness = fitness;
                level = level-rain;
            } else {
                loadTemps();
            }
        }
        problemDefinition = best_structure;
        return best_configuration;
    }

    @Override
    public boolean checkAbortCondition() {
        if(counter<= 0){
            return true;
        }
        counter--;
        return false;
    }


}
