package de.test.agents;

import de.competition.thesis.agents.defaultagents.ProblemSolvingAgent;
import de.competition.thesis.models.Configuration;
import de.test.heuristics.ThresholdAccepting;

public class ThresholdAcceptingAgent extends ProblemSolvingAgent {

    @Override
    protected void solveProblem() {
        ThresholdAccepting thresholdAccepting = new ThresholdAccepting(problemDefinition);
        Configuration newConfiguration = thresholdAccepting.optimize(problemDefinition.getConfiguration());
        problemDefinition.setConfiguration(newConfiguration);
    }
}
