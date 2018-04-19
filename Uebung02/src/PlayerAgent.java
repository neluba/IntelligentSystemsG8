import java.util.HashMap;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class PlayerAgent extends Agent {

	private AID gamemasterAgent;
	
	
	@Override
	protected void setup() {
		gamemasterAgent = new AID("master", AID.ISLOCALNAME);
		RegistrationRequest registrationRequest = new RegistrationRequest();
		registrationRequest.action();
		
		
		
	}
		
		/**
		 * the request for registration in game to gamemaster
		 * 
		 */
		private class RegistrationRequest extends OneShotBehaviour {		
			@Override
			public void action() {
				ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
				msg.addReceiver(gamemasterAgent);
				msg.setContent("rigistration");
				send(msg);
			}
		}
}
