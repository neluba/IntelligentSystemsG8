package de.competition.thesis.operators;

import de.competition.thesis.helper.Cloner;
import de.competition.thesis.models.*;
import de.competition.thesis.ressources.Machine;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * bietet die Operatoren zur Nachbarschaftssuche an
 */
public class NeighbourFinder {
    List<Operator> operators;
    SwitchVariantOperator switchVariantOperator;
    SwitchOrderOperator switchOrderOperator;
    SwitchResourceOperator switchResourceOperator;
    private ProblemDefinition problemDefinition;
    private Random random;
    private Cloner cloner;
    private ArrayList<Machine> tempMachines;
    private ArrayList<Order> tempOrders;

    public NeighbourFinder(ProblemDefinition problemDefinition, Random random){
        this.problemDefinition = problemDefinition;
        this.random = random;
        operators = new ArrayList<>();
        switchVariantOperator = new SwitchVariantOperator(random);
        switchOrderOperator = new SwitchOrderOperator(random);
        switchResourceOperator = new SwitchResourceOperator(random);
        operators.add(switchResourceOperator);
        operators.add(switchOrderOperator);
        operators.add(switchVariantOperator);
        cloner = new Cloner();
    }

    public Configuration findNeighbour(Configuration old_configuration) {
        Configuration configuration = (Configuration) cloner.cloneObject(old_configuration);
        saveProblemStructure();
        int ran = random.nextInt(3);
        operators.get(ran).operate(configuration);
        //System.out.println(operators.get(ran).getClass().getName());

        return configuration;
    }

    private void saveProblemStructure() {
        tempOrders = new ArrayList<>();
        tempMachines = new ArrayList<>();
        for(Machine machine: problemDefinition.getMachines()){
            Machine newMachine = (Machine) cloner.cloneObject(machine);
            tempMachines.add(newMachine);
        }
        for(Order order: problemDefinition.getOrders()){
            Order newOrder = (Order) cloner.cloneObject(order);
            tempOrders.add(newOrder);
        }
    }

    public List<Machine> getTempMachines() {
        return tempMachines;
    }

    public List<Order> getTempOrders(){
        return tempOrders;
    }
}
