package de.competition.thesis.constraints;

import de.competition.thesis.models.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Stellt für die Problemdefinition die Constraints bereit
 */
public class ConstraintsBuilder{

    private List<Constraint> hardConstraints;
    private List<Constraint> softConstraints;
    private ProblemDefinition problemDefinition;

    public ConstraintsBuilder(ProblemDefinition problemDefinition){
        this.problemDefinition = problemDefinition;
        hardConstraints= new ArrayList<>();
        softConstraints = new ArrayList<>();
        buildHardConstraints();
        buildSoftConstraints();
    }

    private void buildHardConstraints(){
        Constraint operationsHConstraint = new Constraint("All needed Operations executed?"){
            @Override
            public boolean check(Configuration configuration){
                for(Order order: configuration.getOrders()){
                    for(Product product: order.getProducts()){
                        Variant variant = product.getChosenVariant();
                        for(Operation operation: variant.getOperations()){
                            String resource = operation.getChosenResource();
                            int duration = operation.getChosenDuration();
                            if (resource == null || duration == 0) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        };
        hardConstraints.add(operationsHConstraint);
        Constraint startTimeHConstraint = new Constraint("startTime?"){
            @Override
            public boolean check(Configuration configuration){
                for(Order order: configuration.getOrders()){
                    int startTime = order.getStart();
                    for(Product product: order.getProducts()){
                        Variant variant = product.getChosenVariant();
                        for(Operation operation: variant.getOperations()){
                            String resource = operation.getChosenResource();
                            int duration = operation.getChosenDuration();
                            int[] interval = operation.getChosenInterval();
                            if (interval[0] < startTime) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        };
        hardConstraints.add(startTimeHConstraint);
        Constraint allOrdersScheduled = new Constraint("All Orders Scheduled?"){
            @Override
            public boolean check(Configuration configuration) {
                for (Order order : problemDefinition.getOrders()) {
                    if (!orderContainsInConfiguration(order, configuration)) {
                        return false;
                    }
                }
                return true;
            }

            private boolean orderContainsInConfiguration(Order orderFromStructure, Configuration configuration) {
                for(Order order: configuration.getOrders()){
                    if(order.getOrderName().equals(orderFromStructure.getOrderName())) {
                        return true;
                    }
                }
                return false;
            }
        };
        hardConstraints.add(allOrdersScheduled);
        Constraint onlyOneVariant = new Constraint("Only one Variant?"){
            @Override
            public boolean check(Configuration configuration) {
                for (Order order : configuration.getOrders()) {
                    for (Product product : order.getProducts()) {
                        Variant variant = product.getChosenVariant();
                        if(variant == null){
                            return false;
                        }
                    }
                }
                return true;
            }
        };
        hardConstraints.add(onlyOneVariant);

        Constraint correctSequence = new Constraint("Correct Sequence of Operations?"){
            @Override
            public boolean check(Configuration configuration) {
                for(Order order: configuration.getOrders()){
                    for(Product product: order.getProducts()){
                        Variant variant = product.getChosenVariant();
                        int lastOperationIndex = 0;
                        for(Operation operation: variant.getOperations()){
                            if(operation.getIndex()-1 != lastOperationIndex){
                                return false;
                            }
                            lastOperationIndex = operation.getIndex();
                        }
                    }
                }
                return true;
            }

        };
        hardConstraints.add(correctSequence);

        Constraint abortedOperations = new Constraint("Some Operations aborted?"){
            @Override
            public boolean check(Configuration configuration) {
                for(Order order: configuration.getOrders()){
                    for(Product product: order.getProducts()){
                        Variant variant = product.getChosenVariant();
                        for(Operation operation: variant.getOperations()){
                            int duration = operation.getChosenDuration();
                            int[] interval = operation.getChosenInterval();
                            if(duration-1 < interval[1]-interval[0]){
                                return false;
                            }
                        }
                    }
                }
                return true;
            }

        };
        hardConstraints.add(abortedOperations);
    }
    private void buildSoftConstraints(){
        Constraint endTimeSConstraint = new Constraint("endTime?"){
            @Override
            public boolean check(Configuration configuration){
                for(Order order: configuration.getOrders()){
                    int endTime = order.getEnd();
                    for(Product product: order.getProducts()){
                        Variant variant = product.getChosenVariant();
                        for(Operation operation: variant.getOperations()){
                            int[] interval = operation.getChosenInterval();
                            if (interval[1] > endTime) {
                                return false;
                            }
                        }
                    }
                }
                return true;
            }
        };
        softConstraints.add(endTimeSConstraint);
    }


    public List<Constraint> getHardConstraints() {
        return hardConstraints;
    }

    public List<Constraint> getSoftConstraints() {
        return softConstraints;
    }

}
