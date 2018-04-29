import java.util.ArrayList;
import java.util.Collections;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * the agent to admin the game
 * 
 * @author IS Group 08
 *
 */
public class GamemasterAgent extends Agent {

	private ArrayList<AID> players;
	private int playersInGame;
	private int currentPlayerIndex;
	private ArrayList<String> deck;
	private String tableCard;
	private int sevenCount;
	private boolean eightActive;
	private String cardWish;
	private ArrayList<String> tableCardStack;
	
	
	@Override
	protected void setup() {
		players = new ArrayList<AID>();
		currentPlayerIndex = 0;
		playersInGame = 0;
		deck = new ArrayList<String>();
		tableCard = "";
		
		// rules for 7, 8, B
		sevenCount = 0;
		eightActive = false;
		cardWish = null;
		
		tableCardStack = new ArrayList<String>();
		initDeck();
		
		addBehaviour(new CyclicBehaviour(this) {	
		
		/**
		 * Receive and response the registration-messages from players to registration in an game.
		 * Receive and response the table card and the cards that the players played. 
		 * Plot the turns from agents in console. 	
		 */
		@Override
		public void action() {
			ACLMessage msg = receive();
			if (msg != null) {
				doWait(500);
				AID msgSender = msg.getSender();
				ACLMessage reply= msg.createReply();
				
				// registration players	and give five handcards to each player			
				if (msg.getPerformative() == ACLMessage.INFORM) {	
					reply.setPerformative(ACLMessage.INFORM);
					String inform = "";
					inform = "playerCards\n";
					int playerCardsCount = 5;
					for (int i = 0; i < playerCardsCount; i++) {
						String card = deck.get(0);
						deck.remove(0);
						inform += card + "\n";
					}
					reply.setContent(inform);
					players.add(msgSender);
					playersInGame++;
					send(reply);
					if (playersInGame == 3) {
						tableCard = deck.get(0);
						tableCardStack.add(tableCard);
						deck.remove(0);
						playCard();
					}
				}
				
				// player turn in game
				if (msg.getPerformative() == ACLMessage.PROPOSE) {
					System.out.println();
					System.out.println(msgSender.getLocalName() + " is turning");
					System.out.println("Tablecard: " + tableCard);
					reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					String msgContent = msg.getContent();
					String[] msgContentAsString = msgContent.split("\n");
					String msgHead = msgContentAsString[0];
					
					// player can not play a card	
					if (msgHead.contains("pass")) {
						
						if (cardWish != null)
							System.out.println("Wish: " + cardWish);
						
						// player must suspend when he can not play an eight
						if (eightActive) {
							reply.setContent("Suspend");
						}
						
						// player must take cards when he can not play a seven
						if (sevenCount > 0) {
							for (int i = 0; i < sevenCount; i++) {
								takeCard();
							}
							reply.setContent("Take " + sevenCount);
						}
						
						if (!eightActive && sevenCount == 0) {	
							reply.setContent("Pass");
							takeCard();
						}
						
						eightActive = false;
						sevenCount = 0;
						send(reply);
						
						currentPlayerIndex++;
						if (currentPlayerIndex >= players.size())
							currentPlayerIndex = 0;
						
						playCard();
												
					// player plays a card	
					} else {
						String colorTableCard = tableCard.substring(0, 1);
						
						// a Bube was played
						if (cardWish != null) {
							colorTableCard = cardWish;
							System.out.println("Wish: " + cardWish);
						}
						String valueTableCard = tableCard.substring(1);
						
						String playedCard = msgContentAsString[0];
						String playerWish = msgContentAsString[1];
						String playersHandSize = msgContentAsString[2];
						String colorPlayedCard = playedCard.substring(0, 1);
						String valuePlayedCard = playedCard.substring(1);
																		
						int playerHandSizeAsInt = Integer.parseInt(playersHandSize);
						System.out.println("Played Card: " + playedCard);
						System.out.println("Handsize: " + playersHandSize);
						
						// player plays a Bube
						if (valuePlayedCard.contains("B")) {
							cardWish = playerWish;
							System.out.println("Wish: " + cardWish);
						}
						if (!valuePlayedCard.contains("B"))
							cardWish = null;
																
						if (colorTableCard.contains(colorPlayedCard) || valueTableCard.contains(valuePlayedCard) || cardWish != null) {
							
							// player plays a seven
							if (valuePlayedCard.contains("7")) 
								sevenCount += 2;
						
							// player plays an eight
							if (valuePlayedCard.contains("8"))
								eightActive = true;
												
							if (playerHandSizeAsInt < 1) {
								System.out.println("Game Over! The winner is: " + msgSender.getLocalName());
								reply.setPerformative(ACLMessage.INFORM);
								reply.setContent("gameOver");
								for (AID player : players) {
									reply.addReceiver(player);
								}
								send(reply);
								doDelete();
							}
							
							reply.setContent("Done");
							send(reply);
							tableCard = playedCard;
							tableCardStack.add(tableCard);
							currentPlayerIndex++;
							if (currentPlayerIndex >= players.size())
								currentPlayerIndex = 0;
							playCard();
					
						// player card is not valid
						} else {
							takeCard();
							playCard();
						}
						
					}	
				}
			} else {
				block();
			}
		}
		});
	}
	
	/**
	 * Inform the players about the actually tablecard and instruct the next player to play a card.
	 */
	private void playCard() {
		ACLMessage informTableCard = new ACLMessage(ACLMessage.INFORM);
		String inform = "tableCard\n";
		
		// a Bube was played
		if (cardWish != null) {
			inform += cardWish;
		} else {
			inform += tableCard;
		}
		
		// a seven or an eight were played and active
		if (sevenCount > 0 || eightActive == true)
			inform += " active";
		informTableCard.setContent(inform);
		for (AID player : players) {
			informTableCard.addReceiver(player);
		}
		send(informTableCard);
		
		ACLMessage playCard = new ACLMessage(ACLMessage.CFP);
		String card = tableCard;
		playCard.setContent(card);
				
		AID currentPlayer = players.get(currentPlayerIndex);
		playCard.addReceiver(currentPlayer);
		send(playCard);
	}
	
	/**
	 * Instruct a player to get a card from cardstack.
	 */
	private void takeCard() {
		ACLMessage msgResponse = new ACLMessage(ACLMessage.INFORM);
		String inform = "getCard\n";
		if (deck.isEmpty()) {
			Collections.shuffle(tableCardStack);
			deck.addAll(tableCardStack);
		}
		String card = deck.get(0);
		deck.remove(0);
		inform += card; 
		msgResponse.setContent(inform);
		AID currentPlayer = players.get(currentPlayerIndex);
		msgResponse.addReceiver(currentPlayer);
		send(msgResponse);
	}
	
	/**
	 * initialize the carddeck
	 */
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
