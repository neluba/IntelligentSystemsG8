import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * the agent who play in game
 * 
 * @author IS Group 08
 *
 */
public class PlayerAgent extends Agent {

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
			 * Plot the conversations between agents in console. 	
			 *  
			 */
			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
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
					
					case "playCard":
						if (!playerHand.isEmpty()) {
							System.out.println();	
							System.out.println(this.getAgent().getLocalName() + " is turning");
							System.out.println("Tablecard: " + tableCard);
							ACLMessage msgRequest = new ACLMessage(ACLMessage.INFORM);
							String inform = "";
							boolean pass = true;
							String colorTableCard = tableCard.substring(0, 1);
							String valueTableCard = tableCard.substring(1);
							int playerHandIndex = 0;
							for (String card : playerHand) {
								String color = card.substring(0, 1);
								String value = card.substring(1);
								if (color.contains(colorTableCard) || value.contains(valueTableCard)) {
									pass = false;
									inform = "playedCard\n" + card;
									playerHand.remove(playerHandIndex);
									inform += "\n" + (playerHand.size() - 1);
									msgRequest.setContent(inform);
									msgRequest.addReceiver(gamemaster);
									break;
								}
								playerHandIndex++;
							}
							if (pass == true) {
								inform = "pass";
								msgRequest.setContent(inform);
								System.out.println(msgRequest.getContent());
								System.out.println("Playerhandsize: " + playerHand.size());
								msgRequest.addReceiver(gamemaster);
							}
							send(msgRequest);
						}
						break;
						
					case "getCard":
						String card = msgContentAsString[1];
						playerHand.add(card);
						break;
									
					case "gameOver":
						doDelete();
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
