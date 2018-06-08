package de.competition.thesis.operators;

import de.competition.thesis.models.*;


import java.util.List;
import java.util.Random;

public class SwitchVariantOperator extends Operator{


    public SwitchVariantOperator(Random random) {
        super(random);
    }

    /**
     * Wählt zufälligen Auftrag aus und ändert die Variante des Auftrages in eine Zufällige
     * @param configuration
     */
    @Override
    public Configuration operate(Configuration configuration) {
        this.configuration = configuration;
        List<Order> orders = configuration.getOrders();
        int randomOrder = random.nextInt(orders.size());
        Order order = orders.get(randomOrder);
        Product product = order.getProducts().get(0);
        int randomVariant = random.nextInt(product.getVariants().size());
        Variant variant = product.getVariants().get(randomVariant);
        removeOrder(order);
        schedule(order, variant);
        product.setChosenVariant(variant);
        return configuration;
    }

}
