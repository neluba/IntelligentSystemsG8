package de.competition.thesis.agents.defaultagents;


import de.competition.thesis.agents.messagetypes.CompetitionMessageTypes;
import de.competition.thesis.loaderwriter.CsvWriter;
import de.competition.thesis.models.Configuration;
import de.competition.thesis.models.ProblemDefinition;
import de.competition.thesis.ressources.Machine;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.util.HashMap;
import java.util.List;

/**
 * Dieser Evaluator ist durch einen individuellen Evaluator zu erweitern.
 * Dabeio sollen vor allem die abortCompetition und evaluateSolutions methoden überschrieben werden.
 */
public class DefaultEvaluator extends jade.core.Agent implements CompetitionMessageTypes{

    public static AID aid;
    protected HashMap<String, List<Integer>> results = new HashMap<>();
    protected HashMap<String, Integer> bestCounter = new HashMap<>();
    protected HashMap<String, AID> addresses = new HashMap<>();
    protected HashMap<String, ProblemDefinition> solutions = new HashMap<>();
    protected String path;
    protected String fileName;
    protected String content;
    public int numberOfAgents;
    public Configuration bestSolution;
    public String bestSolutionAgent = "";

    protected void setup() {
        aid = getAID();
        System.out.println("Hello. DefaultEvaluator starts.");
        do{
            receiveSolutions();
            evaluateSolutions();
        }while(!abortCompetition());
        createResultCSV();
    }

    /**
     * generiert die CSV Results datei
     */
    private void createResultCSV() {
        createContentString();
        writeToCSV();
    }

    /**
     * gibt diese true zurück, wird der Wettkampf beendet
     * @return
     */
    public boolean abortCompetition() {
        return false;
    }

    protected void createContentString() {
        StringBuilder stringBuilder = new StringBuilder();
        createTimeSeries(stringBuilder);
        stringBuilder.append("Best Solution is from: " + bestSolutionAgent+"\n");
        for (Machine machine: bestSolution.getMachines()){
            stringBuilder.append(machine.getName()+ ": " +machine.toString()+ "\n");
        }
        System.out.println(stringBuilder.toString());
        content = stringBuilder.toString();
    }

    private void writeToCSV() {
        CsvWriter csvWriter= new CsvWriter(path, fileName);
        csvWriter.writeStringToCSV(content);
    }

    private void createTimeSeries(StringBuilder stringBuilder) {
        for(String agent: results.keySet()){
            List<Integer> agentResult = results.get(agent);
            stringBuilder.append(agent+";");
            for(int i: agentResult){
                stringBuilder.append(i+";");
            }
            stringBuilder.append(";\n");
            calcResult(results.get(agent), stringBuilder);
            stringBuilder.append("Bests: "+ bestCounter.get(agent) +"\n\n");
        }
    }

    protected void calcResult(List<Integer> results, StringBuilder stringBuilder) {
        double mean;
        int sum = 0;
        int best = Integer.MAX_VALUE;
        int worst = Integer.MIN_VALUE;
        for (int i : results) {
            sum += i;
            if (i <= best)
                best = i;
            if (i >= worst)
                worst = i;
        }
        mean = ((double) sum) / ((double) results.size());
        stringBuilder.append("Mean Fitness: " + mean + "\n");
        stringBuilder.append("Best Fitness: " + best + "\n");
        stringBuilder.append("Worst Fitness: " + worst + "\n");
    }

    /**
     * Methode zum Empfangen der Nachrichten der Agenten
     */
    public void receiveSolutions() {
        int agentsCounter= 0;
        ProblemDefinition problemDefinition;
        while(agentsCounter < numberOfAgents){
            ACLMessage message = blockingReceive();
            if(message != null) {
                problemDefinition = null;
                String sender = message.getSender().getLocalName();
                System.out.println("Solution from "+ sender + " received");
                addresses.put(sender, message.getSender());
                try {
                    problemDefinition = (ProblemDefinition) message.getContentObject();
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
                solutions.put(sender, problemDefinition);
                if(!sender.equals(BlackBoard.aid.getLocalName())){
                    agentsCounter++;
                }
            }
        }
    }

    /**
     * Kern Methode des Evaluators.
     * dient der bestimmung der Güte der empfangenen Lösungen
     */
    public void evaluateSolutions() {

    }

    protected void increaseBestCounter(String bestAgent) {
        if(bestCounter.keySet().contains(bestAgent)){
            int old = bestCounter.get(bestAgent);
            bestCounter.put(bestAgent, old+1 );
        }else{
            bestCounter.put(bestAgent, 1);
        }

    }


}