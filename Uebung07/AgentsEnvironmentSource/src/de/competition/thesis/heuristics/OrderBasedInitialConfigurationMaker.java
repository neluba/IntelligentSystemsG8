package de.competition.thesis.heuristics;


import de.competition.thesis.helper.Cloner;
import de.competition.thesis.models.*;
import de.competition.thesis.ressources.Machine;

import java.util.ArrayList;
import java.util.List;

public class OrderBasedInitialConfigurationMaker {

    private final ProblemDefinition problemDefinition;
    Configuration configuration;
    Cloner cloner;
    List<Order> notScheduledOrders = new ArrayList<>();
    public OrderBasedInitialConfigurationMaker(ProblemDefinition problemDefinition){
        this.problemDefinition = problemDefinition;
        cloner = new Cloner();
    }

    public void generateInitialConfiguration() {
        configuration = new Configuration();
        addMachinesToConfiguration();
        copyOrders();
        for(int i = 0; i < problemDefinition.getOrders().size(); i++) {
            Order order = chooseOrder();
            if (scheduleOrder(order))
                configuration.addOrder(order);
        }
        problemDefinition.setConfiguration(configuration);
    }

    private void copyOrders() {
        for(Order order: problemDefinition.getOrders()){
            Order newOrder = (Order) cloner.cloneObject(order);
            notScheduledOrders.add(newOrder);
        }

    }

    private Order chooseOrder() {
        int i = 0;
        Order chosenOrder = null;
        do{
            Order order = notScheduledOrders.get(i);
            if (chosenOrder == null ||chosenOrder.getStart() > order.getStart()) {
                chosenOrder = order;
            }
            i++;
        } while(i<notScheduledOrders.size());
        notScheduledOrders.remove(chosenOrder);
        return chosenOrder;
    }

    private void addMachinesToConfiguration() {
        for(Machine machine: problemDefinition.getMachines()){
            Machine newMachine = (Machine) cloner.cloneObject(machine);
            configuration.addMachine(newMachine);
        }

    }

    private boolean scheduleOrder(Order order) {
        String productName = order.getProduct();
        order.setNextMoment(order.getStart());
        for(int i = 0; i < order.getQuantity(); i++){
            Product product = (Product) cloner.cloneObject(problemDefinition.getProductByName(productName));
            if(chooseVariant(product, order)){
                order.addProduct(product);
            }else{
                System.out.println("fehler bei Auftrag: " + order.getOrderName());
                return false;
            }
        }
        return true;

    }

    private boolean chooseVariant(Product product, Order order) {
        for(Variant variant: product.getVariants()){
            if(selectOperation(variant, order)){
                product.setChosenVariant(variant);
                return true;
            }
        }
        return false;
    }

    private boolean selectOperation(Variant variant, Order order) {
        for(Operation operation: variant.getOperations()) {
            int duration = operation.getDurations().get(0);
            String resource = operation.getResources().get(0);
            Machine machine = configuration.getMachineByName(resource);
            int[] interval = machine.occupy(order, duration, variant.getName()+" " + operation.getIndex()+"/" +variant.getOperations().size() );
            operation.setChosenDuration(duration);
            operation.setChosenResource(resource);
            operation.setChosenInterval(interval);
        }
        return true;
    }

}
