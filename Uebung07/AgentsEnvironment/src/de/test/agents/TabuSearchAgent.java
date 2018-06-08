package de.test.agents;

import de.competition.thesis.agents.defaultagents.ProblemSolvingAgent;
import de.competition.thesis.models.Configuration;
import de.test.heuristics.TabuSearch;

public class TabuSearchAgent extends ProblemSolvingAgent {

    @Override
    protected void solveProblem() {
        TabuSearch tabuSearch = new TabuSearch(problemDefinition);
        Configuration newConfiguration = tabuSearch.optimize(problemDefinition.getConfiguration());
        problemDefinition.setConfiguration(newConfiguration);
    }
}
