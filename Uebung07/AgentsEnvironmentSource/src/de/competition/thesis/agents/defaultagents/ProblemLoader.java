package de.competition.thesis.agents.defaultagents;

import de.competition.thesis.agents.messagetypes.CompetitionMessageTypes;
import de.competition.thesis.constraints.Constraint;
import de.competition.thesis.constraints.ConstraintsBuilder;
import de.competition.thesis.helper.Cloner;
import de.competition.thesis.heuristics.OrderBasedInitialConfigurationMaker;
import de.competition.thesis.loaderwriter.JsonLoader;
import de.competition.thesis.models.ProblemDefinition;
import de.competition.thesis.ressources.Machine;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Der Problemloader generiert die Problemdefinition und sendet diese an das Blackboard
 */
public class ProblemLoader extends jade.core.Agent implements CompetitionMessageTypes{

    public static AID aid;
    ProblemDefinition problemDefinition;
    Cloner cloner = new Cloner();
    private ArrayList<Machine> machines;
    final String path = "src\\";
    final String data = "ft06_erw.json";

    protected void setup()  {
        aid = getAID();
        System.out.println("Hello. Problemloader starts.");
        loadJsonFile();
        loadProblem();
        writeOnBlackboard();
    }


    private void loadProblem() {
        createConstraints();
        createMachines();
        createInitialConfiguration();
    }

    private void createInitialConfiguration() {
        OrderBasedInitialConfigurationMaker orderBasedInitialConfiguration = new OrderBasedInitialConfigurationMaker(problemDefinition);
        orderBasedInitialConfiguration.generateInitialConfiguration();
    }

    private void writeOnBlackboard() {
        ACLMessage message = new ACLMessage(WRITE);
        message.addReceiver(BlackBoard.aid);
        try {
            message.setContentObject(problemDefinition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        send(message);
    }


    private void loadJsonFile() {
        JsonLoader JsonLoader = new JsonLoader(path, data);
        problemDefinition = JsonLoader.getProblemDefinition();
    }

    private void createConstraints() {
        ConstraintsBuilder constraintsBuilder = new ConstraintsBuilder(problemDefinition);
        List<Constraint> softConstraints = new ArrayList<>();
        List<Constraint> hardConstraints = new ArrayList<>();
        for(Constraint sConstraint: constraintsBuilder.getSoftConstraints()){
            Constraint constraint = (Constraint) cloner.cloneObject(sConstraint);
            softConstraints.add(constraint);
        }
        for(Constraint hConstraint: constraintsBuilder.getHardConstraints()){
            Constraint constraint = (Constraint) cloner.cloneObject(hConstraint);
            hardConstraints.add(constraint);
        }
        problemDefinition.setSoftConstraints(softConstraints);
        problemDefinition.setHardConstraints(hardConstraints);
    }

    private void createMachines(){
        machines = new ArrayList<>();
        for(String resource: problemDefinition.getResources()){
            Machine machine = new Machine(resource);
            machines.add(machine);
        }
        problemDefinition.setMachines(machines);
    }

}
