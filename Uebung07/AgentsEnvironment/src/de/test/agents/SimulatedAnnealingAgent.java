package de.test.agents;

import de.competition.thesis.agents.defaultagents.ProblemSolvingAgent;
import de.competition.thesis.models.Configuration;
import de.test.heuristics.SimulatedAnnealing;

public class SimulatedAnnealingAgent extends ProblemSolvingAgent {

    @Override
    protected void solveProblem() {
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(problemDefinition);
        Configuration newConfiguration = simulatedAnnealing.optimize(problemDefinition.getConfiguration());
        problemDefinition.setConfiguration(newConfiguration);
    }

}
