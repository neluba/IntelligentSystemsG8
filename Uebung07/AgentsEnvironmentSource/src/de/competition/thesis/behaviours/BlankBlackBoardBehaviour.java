package de.competition.thesis.behaviours;

import de.competition.thesis.agents.defaultagents.DefaultEvaluator;
import de.competition.thesis.agents.defaultagents.BlackBoard;
import de.competition.thesis.agents.messagetypes.CompetitionMessageTypes;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public class BlankBlackBoardBehaviour extends SimpleBehaviour implements CompetitionMessageTypes{

    ACLMessage problemMessage;

    @Override
    public void action() {
        while(problemMessage == null){
            ACLMessage msg= myAgent.blockingReceive();
            if (msg!=null) {
                if (msg.getPerformative() == WRITE) {
                    System.out.println("Problem from " + msg.getSender().getLocalName() + " received");
                    problemMessage = msg;
                    ((BlackBoard) myAgent).setProblemMessage(problemMessage);
                    sendToEvaluator();
                    done();
                }
                if (msg.getPerformative() == ACLMessage.REQUEST) {
                    ACLMessage message = new ACLMessage(ACLMessage.INFORM);
                    System.out.println("Request from " + msg.getSender().getLocalName() + " received");
                    System.out.println(myAgent.getLocalName() +" sends inform to " +  msg.getSender().getLocalName());
                    message.setContent("Blackboard is empty");
                    message.addReceiver(msg.getSender());
                    myAgent.send(message);
                }
            }
        }

    }

    @Override
    public boolean done() {
        return false;
    }

    private void sendToEvaluator() {
        ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
        message.addReceiver(DefaultEvaluator.aid);
        try {
            message.setContentObject(problemMessage.getContentObject());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnreadableException e) {
            e.printStackTrace();
        }
        myAgent.send(message);
    }
}
