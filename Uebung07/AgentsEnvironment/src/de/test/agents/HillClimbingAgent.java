package de.test.agents;

import de.competition.thesis.agents.defaultagents.ProblemSolvingAgent;
import de.competition.thesis.heuristics.Heuristic;
import de.competition.thesis.models.Configuration;


/**
 * Created by TK on 21.07.2017.
 */
public class HillClimbingAgent extends ProblemSolvingAgent {


    @Override
    protected void solveProblem() {
        Heuristic heuristic = new Heuristic(problemDefinition);
        Configuration newConfiguration = heuristic.optimize(problemDefinition.getConfiguration());
        problemDefinition.setConfiguration(newConfiguration);
    }




}
