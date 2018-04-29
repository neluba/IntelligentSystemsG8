import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * the dummy agent who play in game
 * 
 * @author IS Group 08
 *
 */
public class DummyAgent extends Agent {

	private AID gamemaster;
	private ArrayList<String> playerHand;
	private String tableCard;
	
	
	@Override
	protected void setup() {
		gamemaster = new AID("Gamemaster", AID.ISLOCALNAME);
		playerHand = new ArrayList<String>();		
		RegistrationRequest registrationRequest = new RegistrationRequest();
		registrationRequest.action();
		
		addBehaviour(new CyclicBehaviour(this) {			
						
			/**
			 * Receive and response the messages from and to gameadmin to play the game.
			 *  
			 */
			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
				
					// information for admin the game
					if (msg.getPerformative() == ACLMessage.INFORM) {
						String msgContent = msg.getContent();
						String[] msgContentAsString = msgContent.split("\n");
						String msgHead = msgContentAsString[0];
						switch (msgHead) {	
					
						case "playerCards":					
							for (int i = 0; i < msgContentAsString.length; i++) {
								playerHand.add(msgContentAsString[i]);
							}
							break;
								
						case "tableCard":
							tableCard = msgContentAsString[1];
							break;	
				
						case "getCard":
							String card = msgContentAsString[1];
							playerHand.add(card);
							break;
								
						case "gameOver":
							doDelete();
							break;
						}
					}
				
					// the players turn
					if (msg.getPerformative() == ACLMessage.CFP) {	
						if (!playerHand.isEmpty()) {
							ACLMessage playCardReplay = msg.createReply();
							playCardReplay.setPerformative(ACLMessage.PROPOSE);
							
							String inform = "";
							boolean pass = true;
							String colorTableCard = tableCard.substring(0, 1);
							String valueTableCard = tableCard.substring(1);
							int playerHandIndex = 0;
														
							// check for a playable card
							if (!valueTableCard.contains("active")) {
							for (String card : playerHand) {
									String color = card.substring(0, 1);
									String value = card.substring(1);
									if ((color.contains(colorTableCard) || value.contains(valueTableCard))) {
										pass = false;
										inform = card;
										playerHand.remove(playerHandIndex);
										
										// a Bube will be play with the color Kreuz 
										if (value.contains("B")) {
											inform += "\n" + "K ";
										} else {
											inform += "\n" + "no wish";
										}
										
										inform += "\n" + (playerHand.size() - 1);
										break;
									}
									playerHandIndex++;
								}
							playerHandIndex = 0;
							}
							
							if (pass == true)
								inform = "pass";
													
							playCardReplay.setContent(inform);
							send(playCardReplay);
						}
					}
				
					if (msg.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {	
						System.out.println(msg.getContent());						
					}
				
				
				} else {
					block();
				}
			}
		});
	}
		
	/**
	 * the request for registration in game to gameadmin
	 * 
	 */
	private class RegistrationRequest extends OneShotBehaviour {		
		@Override
		public void action() {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(gamemaster);
			msg.setContent("rigistration");
			send(msg);
		}
	}

}
