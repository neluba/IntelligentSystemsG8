package de.competition.thesis.heuristics;

import de.competition.thesis.models.*;
import de.competition.thesis.helper.Cloner;
import de.competition.thesis.operators.NeighbourFinder;


import java.util.Random;

/**
 * Standard heuristik
 */
public class Heuristic {

    protected ProblemDefinition problemDefinition;
    protected Random random;
    protected NeighbourFinder neighbourFinder;
    protected Cloner cloner;
    protected int iteration = 0;

    public Heuristic(ProblemDefinition problemDefinition){
        this.problemDefinition = problemDefinition;
        this.random = new Random();
        this.neighbourFinder = new NeighbourFinder(problemDefinition,random);
        cloner = new Cloner();
    }

    /**
     * Kernfunktion der iterativen Optimierung.
     * Optimiert eine eingangs configuration
     * @param old_configuration
     * @return
     */
    public Configuration optimize(Configuration old_configuration){
        int old_fitness = calculateFitness(old_configuration);
        while(!checkAbortCondition()){
            Configuration new_configuration = neighbourFinder.findNeighbour(old_configuration);
            int new_fitness = calculateFitness(new_configuration);
            if(new_fitness< old_fitness){
                old_configuration = new_configuration;
                old_fitness = new_fitness;
            }else{
                loadTemps();
            }
        }
        return old_configuration;
    }

    /*protected int calculateFitness(Configuration configuration) {
        int span = 0;
        for(Order order: configuration.getOrders()){
            for(Product product: order.getProducts()){
                Variant variant = product.getChosenVariant();
                for(Operation operation: variant.getOperations()){
                    int operationFinished = operation.getChosenInterval()[1];
                    if(operationFinished> span){
                        span = operationFinished;
                    }
                }
            }
        }
        return span;
    }
*/
    public boolean checkAbortCondition() {
        if(iteration>= 100){
            return true;
        }
        iteration++;
        return false;
    }

    public void checkHardConstraints() {

    }

    protected void loadTemps() {
        problemDefinition.loadTempOrders(neighbourFinder.getTempOrders());
    }

    /**
     * Fitnessfunktion der Heuristik
     * @param configuration
     * @return
     */
    protected int calculateFitness(Configuration configuration){
      int tardiness = 0;
       for(Order order: configuration.getOrders()){
           int span = getSpanOfOrder(order);
           if(span> order.getEnd()) {
               tardiness+=(span - order.getEnd());
           }
       }
       return tardiness;

   }

    private int getSpanOfOrder(Order order) {
        int span = 0;
        for(Product product: order.getProducts()){
            Variant variant = product.getChosenVariant();
            for(Operation operation: variant.getOperations()){
                int operationFinished = operation.getChosenInterval()[1];
                if(operationFinished> span){
                    span = operationFinished;
                }
            }
        }
        return span;
    }


}
