package de.test.heuristics;

import de.competition.thesis.heuristics.Heuristic;
import de.competition.thesis.models.Configuration;
import de.competition.thesis.models.ProblemDefinition;


public class SimulatedAnnealing extends Heuristic {
    int counter = 0;
    double temperature = 220;
    int reduceTempFactor= 5;
    ProblemDefinition best_structure;

    public SimulatedAnnealing(ProblemDefinition problemDefinition) {
        super(problemDefinition);
    }

    @Override
    public Configuration optimize(Configuration old_configuration){
        int old_fitness = calculateFitness(old_configuration);
        Configuration best_configuration = (Configuration) cloner.cloneObject(old_configuration);
        best_structure = (ProblemDefinition) cloner.cloneObject(problemDefinition);
        int best_fitness = old_fitness;
        while(!this.checkAbortCondition()){
            Configuration new_configuration = neighbourFinder.findNeighbour(old_configuration);
            int new_fitness = calculateFitness(new_configuration);
            double diff = new_fitness - old_fitness;
            if(accept(diff, temperature)){
                old_configuration = (Configuration) cloner.cloneObject(new_configuration);
                old_fitness = new_fitness;
            }
            if(old_fitness< best_fitness) {
                best_structure = problemDefinition;
                best_configuration = (Configuration) cloner.cloneObject(old_configuration);
                best_fitness = old_fitness;
            }else{
                counter++;
                if(counter >= 5){
                    counter = 0;
                    temperature=temperature-reduceTempFactor;
                    old_configuration = (Configuration) cloner.cloneObject(best_configuration);
                    old_fitness = best_fitness;
                }
                loadTemps();
            }
        }
        problemDefinition = best_structure;
        return best_configuration;
    }


    private boolean accept(double diff, double temperature) {
        if(diff<=0){
            return true;
        }
        double p = Math.exp((diff)/temperature);

        if(p >= random.nextDouble()){
            return true;
        }
        return false;

    }
    public boolean checkAbortCondition() {
        if(temperature<= 0){
            return true;
        }
        iteration++;
        return false;
    }


}
