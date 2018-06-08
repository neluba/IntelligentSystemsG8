package de.competition.thesis.operators;

import de.competition.thesis.models.*;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SwitchOrderOperator extends Operator {


    public SwitchOrderOperator(Random random) {
        super(random);
    }

    @Override
    public Configuration operate(Configuration configuration){
        this.configuration = configuration;
        List<Order> orders = configuration.getOrders();
        int i1 = 0;
        int i2 = 0;
        while(i1 == i2){
            i1 = random.nextInt(orders.size());
            i2 = random.nextInt(orders.size());
        }
        removeOrder(orders.get(i1));
        removeOrder(orders.get(i2));
        Product productI2 = orders.get(i2).getProducts().get(0);
        Variant variantI2=  productI2.getChosenVariant();
        Product productI1 = orders.get(i1).getProducts().get(0);
        Variant variantI1=  productI1.getChosenVariant();
        schedule(orders.get(i2), variantI2);
        schedule(orders.get(i1), variantI1);
        Collections.swap(orders,i1, i2);
        return configuration;
    }


}
