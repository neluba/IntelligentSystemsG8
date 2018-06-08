package de.test.evaluator;

import de.competition.thesis.agents.defaultagents.DefaultEvaluator;
import de.competition.thesis.helper.Cloner;
import de.competition.thesis.models.Configuration;
import de.competition.thesis.models.ProblemDefinition;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Evaluator welcher auf dem DefaultEvlatuator aufbaut.
 *
 */
public class Evaluator extends DefaultEvaluator{

    String path = "src\\";
    String fileName = "result1.csv";
    int numberOfCompetitions = 5;
    int numberOfAgents = 5;
    int bestSolutionFitness = Integer.MAX_VALUE;
    Cloner cloner = new Cloner();

    public Evaluator() {
        super.path = this.path;
        super.fileName = this.fileName;
        super.numberOfAgents = this.numberOfAgents;
    }

    /**
     * Beendet wettkampf
     */
    @Override
    public boolean abortCompetition() {
        if(numberOfCompetitions <= 1){
            return true;
        }
        for(String key: addresses.keySet()){
            ACLMessage message = new ACLMessage(RESTART);
            message.addReceiver(addresses.get(key));
            send(message);
        }
        numberOfCompetitions--;
        return false;
    }

/**
 * Bewertet und vergleicht die Lösungen der Agenten
 */
    public void evaluateSolutions() {
        int best = Integer.MAX_VALUE;
        String bestAgent = null;
        QualityDeterminer qualityDeterminer = new QualityDeterminer();
        ProblemDefinition problemDefinition;
        for(String sender: solutions.keySet()){
            problemDefinition = solutions.get(sender);
            int quality = qualityDeterminer.evaluateSolution(problemDefinition.getConfiguration());
            if(results.keySet().contains(sender)){
                results.get(sender).add(quality);
            }else{
                List<Integer> result = new ArrayList<>();
                result.add(quality);
                results.put(sender, result);
            }
            if(quality <= best){
                best = quality;
                bestAgent = sender;
            }
            if(quality<=bestSolutionFitness){
                bestSolutionAgent = sender;
                bestSolution = (Configuration) cloner.cloneObject(problemDefinition.getConfiguration());
                bestSolutionFitness = quality;
            }
        }
        increaseBestCounter(bestAgent);
    }

}
