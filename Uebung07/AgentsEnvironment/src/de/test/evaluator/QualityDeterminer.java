package de.test.evaluator;

import de.competition.thesis.models.*;

import java.util.HashMap;

/**
 * Stellt güte funktion für den Evalautor bereit
 */
public class QualityDeterminer {

    public int evaluateSolution(Configuration configuration) {
        return makeTardiness(configuration);
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

    private int makeSpan(Configuration configuration) {
        int span = 0;
        for(Order order: configuration.getOrders()){
            int orderSpan = getSpanOfOrder(order);
            if(orderSpan> span){
                span = orderSpan;
            }
        }
        return span;
    }
    private int makeTardiness(Configuration configuration){
        HashMap<String,Integer> tardinessHashMap = new HashMap<>();
        int sumOfTardiness= 0;
        for(Order order: configuration.getOrders()){
            String orderName = order.getOrderName();
            int span = getSpanOfOrder(order);
            if(span> order.getEnd()) {
                tardinessHashMap.put(orderName, (span - order.getEnd()));
                sumOfTardiness+=(span - order.getEnd());
            }
            else{
                tardinessHashMap.put(orderName, 0);
            }
        }
        tardinessHashMap.put("sum", sumOfTardiness);
        return tardinessHashMap.get("sum");
    }
}
