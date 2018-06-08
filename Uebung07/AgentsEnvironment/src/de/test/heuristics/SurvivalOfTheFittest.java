package de.test.heuristics;

import de.competition.thesis.heuristics.Heuristic;
import de.competition.thesis.models.Configuration;
import de.competition.thesis.models.ProblemDefinition;

import java.util.*;
import java.util.stream.Collectors;

public class SurvivalOfTheFittest extends Heuristic {

    List<Configuration> parents;
    List<Configuration> population;
    LinkedHashMap<Configuration, Integer> ranking;
    HashMap<Configuration,Integer> popWithFitness;
    public SurvivalOfTheFittest(ProblemDefinition problemDefinition) {
        super(problemDefinition);
    }

    public Configuration optimize(Configuration configuration) {
        createStartPopulation(configuration);
        while (!checkAbortCondition()) {
            recombination();
            evaluate();
            selection();
        }

        return parents.get(0);
    }

    private void createStartPopulation(Configuration configuration) {
        popWithFitness = new HashMap<>();
        population = new ArrayList<>();
        parents = new ArrayList();
        parents.add((Configuration) cloner.cloneObject(configuration));
        while (parents.size() < 6) {
            Configuration individual = neighbourFinder.findNeighbour(configuration);
            parents.add((Configuration) cloner.cloneObject(individual));
            population.add(individual);
        }
    }

    private void selection() {
        parents = new ArrayList<>();

        for(Configuration key: ranking.keySet()){
            if(parents.size() >= 5){
                return;
            }
            parents.add((Configuration) cloner.cloneObject(key));
        }
    }

    private void evaluate() {
        int size = population.size();
        for(int i = 0; i < size; i++) {
            Configuration individual = population.get(i);
            int fitness = calculateFitness(individual);
            popWithFitness.put(individual, fitness);
        }
        rank();
    }

    private void rank() {
        ranking = popWithFitness.entrySet().stream().
                        sorted(Map.Entry.comparingByValue()).
                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                                (e1, e2) -> e1, LinkedHashMap::new));
    }


    private void recombination() {
        for (Configuration parent : parents) {
            for (int i = 0; i < 2; i++) {
                Configuration child = neighbourFinder.findNeighbour(parent);
                population.add(child);
            }
        }
    }

    public boolean checkAbortCondition() {
        if(iteration>= 100){
            return true;
        }
        iteration++;
        return false;
    }

}
