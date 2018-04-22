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
	private ArrayList<String> tableCardStack;
	
	
	@Override
	protected void setup() {
		players = new ArrayList<AID>();
		currentPlayerIndex = 0;
		playersInGame = 0;
		deck = new ArrayList<String>();
		tableCardStack = new ArrayList<String>();
		initDeck();
		
		addBehaviour(new CyclicBehaviour(this) {	
		
		/**
		 * Receive and response the registration-messages from players to registration in an game.
		 * Receive and response the table card and the cards that the players played. 
		 * 
		 */
		@Override
		public void action() {
			ACLMessage msg = receive();
			if (msg != null) {
				ACLMessage msgResponse = new ACLMessage(ACLMessage.INFORM);
				String inform = "";
				AID msgSender = msg.getSender();
				String msgContent = msg.getContent();
				String[] msgContentAsString = msgContent.split("\n");
				String msgHead = msgContentAsString[0];
				switch (msgHead) {		
					
				case "rigistration":  
					inform = "playerCards\n";
					int playerCardsCount = 5;
					for (int i = 0; i < playerCardsCount; i++) {
						String card = deck.get(0);
						deck.remove(0);
						inform += card + "\n";
					}
					msgResponse.setContent(inform);
					msgResponse.addReceiver(msgSender);
					players.add(msgSender);
					playersInGame++;
					send(msgResponse);
					if (playersInGame == 3) {
						Collections.sort(players);
						tableCard = deck.get(0);
						tableCardStack.add(tableCard);
						deck.remove(0);
						playCard();
					}
					break;
				
				// player can not play a card	
				case "pass":
					takeCard();
					currentPlayerIndex++;
					if (currentPlayerIndex >= players.size())
						currentPlayerIndex = 0;
					playCard();
					break;
				
				// player played a card	
				case "playedCard":
					String colorTableCard = tableCard.substring(0, 1);
					String valueTableCard = tableCard.substring(1);
					String playedCard = msgContentAsString[1];
					String colorPlayedCard = playedCard.substring(0, 1);
					String valuePlayedCard = playedCard.substring(1);
					String playersHandSize = msgContentAsString[2];
					int playerHandSizeAsInt = Integer.parseInt(playersHandSize);
					System.out.println("Playercard: " + playedCard);
					System.out.println("Playerhandsize: " + playersHandSize);
					if (colorTableCard.contains(colorPlayedCard) || valueTableCard.contains(valuePlayedCard)) {
						if (playerHandSizeAsInt < 1) {
							System.out.println("Game Over! The winner is: " + msgSender.getLocalName());
							msgResponse.setContent("gameOver");
							for (AID player : players) {
								msgResponse.addReceiver(player);
							}
							send(msgResponse);
							doDelete();
						}
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
					break;
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
		ACLMessage msgRequestTableCard = new ACLMessage(ACLMessage.INFORM);
		String inform = "";
		inform = "tableCard\n" + tableCard;
		msgRequestTableCard.setContent(inform);
		for (AID player : players) {
			msgRequestTableCard.addReceiver(player);
		}
		send(msgRequestTableCard);
		
		ACLMessage msgRequestPlayCard = new ACLMessage(ACLMessage.INFORM);
		inform = "playCard";
		msgRequestPlayCard.setContent(inform);
		AID currentPlayer = players.get(currentPlayerIndex);
		msgRequestPlayCard.addReceiver(currentPlayer);
		send(msgRequestPlayCard);
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
