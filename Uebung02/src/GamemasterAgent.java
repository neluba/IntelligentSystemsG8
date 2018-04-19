import java.util.ArrayList;
import java.util.Collections;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class GamemasterAgent extends Agent {

	private ArrayList<AID> players;
	private ArrayList<String> deck;
	
	
	@Override
	protected void setup() {
		players = new ArrayList<AID>();
		deck = new ArrayList<String>();
		initDeck();
		
		addBehaviour(new CyclicBehaviour(this) {	
		
		/**
		 * Receive and response the registration-messages from players to registration in an game.
		 * 
		 */
		@Override
		public void action() {
			ACLMessage msg = receive();
			if (msg != null) {
				String msgContent = msg.getContent();
				AID msgSender = msg.getSender();
				ACLMessage msgResponse = new ACLMessage(ACLMessage.INFORM);
		
				if (msgContent.contains("rigistration")) {
					
				
					players.add(msgSender);
					System.out.println(msgSender.getLocalName() + " is in game");
			}
			send(msgResponse);
		} else {
			block();
		}
		}

		});
	}
	
	private void initDeck() {
		deck.add("K7");
		deck.add("K8");
		deck.add("K9");
		deck.add("KZ");
		deck.add("KB");
		deck.add("KD");
		deck.add("KK");
		deck.add("KA");
		
		deck.add("P7");
		deck.add("P8");
		deck.add("P9");
		deck.add("PZ");
		deck.add("PB");
		deck.add("PD");
		deck.add("PK");
		deck.add("PA");
		
		deck.add("H7");
		deck.add("H8");
		deck.add("H9");
		deck.add("HZ");
		deck.add("HB");
		deck.add("HD");
		deck.add("HK");
		deck.add("HA");
		
		deck.add("C7");
		deck.add("C8");
		deck.add("C9");
		deck.add("CZ");
		deck.add("CB");
		deck.add("CD");
		deck.add("CK");
		deck.add("CA");
		
		Collections.shuffle(deck);
	}

}
