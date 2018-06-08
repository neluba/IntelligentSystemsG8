package de.test.agents;

import de.competition.thesis.agents.defaultagents.ProblemSolvingAgent;
import de.competition.thesis.models.Configuration;
import de.test.heuristics.GreatDeluge;

public class GreatDelugeAgent extends ProblemSolvingAgent {

    @Override
    protected void solveProblem() {
        GreatDeluge greatDelugeAgent = new GreatDeluge(problemDefinition);
        Configuration newConfiguration = greatDelugeAgent.optimize(problemDefinition.getConfiguration());
        problemDefinition.setConfiguration(newConfiguration);
    }

}


