package de.competition.thesis.operators;


import de.competition.thesis.models.*;
import de.competition.thesis.ressources.Machine;

import java.util.List;
import java.util.Random;

public class SwitchResourceOperator extends Operator {


    public SwitchResourceOperator(Random random) {
        super(random);
    }

    @Override
    public Configuration operate(Configuration configuration) {
        this.configuration = configuration;
        List<Order> orders = configuration.getOrders();
        int randomOrder = random.nextInt(orders.size());
        Order order = orders.get(randomOrder);
        for (Product product : order.getProducts()) {
            Variant variant = product.getChosenVariant();
            removeOrder(order);
            schedule(order, variant);
            product.setChosenVariant(variant);
        }

        return configuration;
    }

    @Override
    protected void schedule(Order order, Variant variant) {
        for (Operation operation : variant.getOperations()) {
            int randomResource =  random.nextInt(operation.getResources().size());
            int duration = operation.getDurations().get(randomResource);
            String resource = operation.getResources().get(randomResource);
            Machine machine = configuration.getMachineByName(resource);
            int[] interval = machine.occupy(order, duration, variant.getName() + " " + operation.getIndex() + "/" + variant.getOperations().size());
            operation.setChosenDuration(duration);
            operation.setChosenResource(resource);
            operation.setChosenInterval(interval);
        }

    }


}
