package de.competition.thesis.operators;


import de.competition.thesis.helper.Cloner;
import de.competition.thesis.models.*;
import de.competition.thesis.ressources.Machine;

import java.util.Random;

/**
 * Standardoperator, kann verwendet werden um neue Operatoren zu rnachbarschaftssuche hinzuzufügen
 */
public class Operator {


    protected Random random;
    protected Cloner cloner;
    protected Configuration configuration;

    public Operator(Random random){
        this.random = random;
        cloner = new Cloner();
    }

    protected Configuration operate(Configuration configuration){
        this.configuration = configuration;
        return configuration;
    }

    protected void schedule(Order order, Variant variant) {
        for (Operation operation : variant.getOperations()) {
            for (int i = 0; i < operation.getResources().size(); i++) {
                int duration = operation.getDurations().get(i);
                String resource = operation.getResources().get(i);
                Machine machine = configuration.getMachineByName(resource);
                int[] interval = machine.occupy(order, duration, variant.getName() + " " + operation.getIndex() + "/" + variant.getOperations().size());
                operation.setChosenDuration(duration);
                operation.setChosenResource(resource);
                operation.setChosenInterval(interval);
            }
        }
    }

    protected void removeOrder(Order order) {
        Product product = order.getProducts().get(0);
        Variant variant=  product.getChosenVariant();
        for(Operation operation: variant.getOperations()){
            Machine machine = configuration.getMachineByName(operation.getChosenResource());
            machine.removeOrder(order);
        }
        order.setNextMoment(order.getStart());
    }
}
