package de.competition.thesis.behaviours;

import de.competition.thesis.agents.defaultagents.BlackBoard;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;

import java.io.IOException;

public class BlackBoardBehaviour extends CyclicBehaviour {

    ACLMessage problemMessage;

    @Override
    public void action() {
        ACLMessage msg= myAgent.blockingReceive();
        if (msg!=null) {
            problemMessage = ((BlackBoard) myAgent).getProblemMessage();
            if(msg.getPerformative()== ACLMessage.REQUEST){
                System.out.println("request from " + msg.getSender().getLocalName() + " received!");
                ACLMessage message = new ACLMessage(ACLMessage.PROPAGATE);
                message.addReceiver(msg.getSender());
                try {
                    message.setContentObject(problemMessage.getContentObject());
                } catch (UnreadableException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                myAgent.send(message);
            }

        }

    }
}
