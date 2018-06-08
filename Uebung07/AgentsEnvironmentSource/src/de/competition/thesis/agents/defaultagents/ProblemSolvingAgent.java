package de.competition.thesis.agents.defaultagents;

import de.competition.thesis.models.ProblemDefinition;
import de.competition.thesis.agents.messagetypes.CompetitionMessageTypes;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;


/**
 * Problemlösender Standardagent
 * Alle anderen problemlöser erben von diesem.
 */
public class ProblemSolvingAgent extends jade.core.Agent implements CompetitionMessageTypes{

    public static AID aid;
    protected ProblemDefinition problemDefinition;
    protected void setup()  {
        aid = getAID();
        do {
            lookAtBlackboard();
            solveProblem();
            sendSolution();
        }while (evaluatorContinues());
    }

    /**
     * Prüft, ob der Evaluator eine weitere Wettkampfrunde startet
     * @return
     */
    private boolean evaluatorContinues() {
        ACLMessage msg= blockingReceive();
        if (msg!=null) {
            if(msg.getPerformative() == RESTART){
                return true;
            }
            else{
                return false;
            }
        }
        return false;
    }

    /**
     * Kernmethode des Evaluators.
     * Diese enthält die Lösuzngsstrategie
     */
    protected void solveProblem() {

    }

    private void sendSolution() {
        ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
        message.addReceiver(DefaultEvaluator.aid);
        try {
            message.setContentObject(problemDefinition);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(getLocalName() + " sends Solution");
        send(message);
    }

    private void lookAtBlackboard() {
        do {
            sendRequest();
        }while(receiveTask());
    }

    private boolean receiveTask() {
        ACLMessage msg= blockingReceive();
        if (msg!=null) {
            if(msg.getPerformative() == ACLMessage.INFORM){
                System.out.println("Inform from " + msg.getSender().getLocalName() +" received");
                return true;
            }
            else if(msg.getPerformative() == ACLMessage.PROPAGATE) {
                try {
                    problemDefinition = (ProblemDefinition) msg.getContentObject();
                    System.out.println("configuration received");
                    return false;
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    private void sendRequest() {
        ACLMessage message = new ACLMessage(ACLMessage.REQUEST);
        message.addReceiver(BlackBoard.aid);
        message.setContent("order?");
        System.out.println(getLocalName() + " sends Request ");
        send(message);
    }


}
