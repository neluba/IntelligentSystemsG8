package de.competition.thesis.agents.defaultagents;

import de.competition.thesis.behaviours.BlankBlackBoardBehaviour;
import de.competition.thesis.models.ProblemDefinition;
import de.competition.thesis.behaviours.BlackBoardBehaviour;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 * Behandelt die Anfragen der Problemlösenden Agenten und stellt diesen eine Problemdefinition bereit
 */
public class BlackBoard extends jade.core.Agent{

    public static AID aid;
    protected ACLMessage problemMessage;
    protected void setup()  {
        problemMessage = null;
        aid = getAID();
        System.out.println("Hello. BlackBoard starts.");
        waitForProblem();
        addBehaviour(new BlackBoardBehaviour());
    }

    private void waitForProblem() {
        addBehaviour(new BlankBlackBoardBehaviour());
    }

    public void setProblemMessage(ACLMessage problem) {
        this.problemMessage = problem;
    }

    public ACLMessage getProblemMessage() {
        return problemMessage;
    }

}
