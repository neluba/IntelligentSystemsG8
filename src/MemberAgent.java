import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * the member-agent in contract net
 * 
 * @author IS Group 08
 *
 */
public class MemberAgent extends Agent {
		
	private Random randomTimer;
	private AID adminAgent;
	private boolean isAuctionator;
	private HashMap<AID, String[]> offers;
	private int offerCount;
	private int bestOfferTime;
	private AID bestOffer;
				
	@Override
	protected void setup() {
		randomTimer = new Random();
		adminAgent = new AID("admin", AID.ISLOCALNAME);
		offers = new HashMap<AID, String[]>();
		
		RegistrationRequest registrationRequest = new RegistrationRequest();
		registrationRequest.action();
		
		addBehaviour(new CyclicBehaviour(this) {			
			
			/**
			 * Receive the start-message from admin to start the auction.
			 * Receive and response the messages in auction and 
			 * differentiates between auctionator and auction-member.
			 * Plot the conversations between agents in console. 	
			 *  
			 */
			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					String msgContent = msg.getContent();
					ACLMessage msgResponse = new ACLMessage(ACLMessage.INFORM);
					String[] msgContentAsString = msgContent.split("\n");
					String msgHead = msgContentAsString[0];
					switch (msgHead) {		
					
					case "setAuctionator":					
						isAuctionator = true;
						break;
					
					case "startAuction":
						if (isAuctionator == true) {
							Iterator receiverIterator = msg.getAllReceiver();
							while (receiverIterator.hasNext()) {
								AID auctionMember = (AID) receiverIterator.next();
								String auctionMemberName = auctionMember.getName();
								String auctionatorName = this.getAgent().getName();
								if (!auctionMemberName.contains(auctionatorName)) 
									msgResponse.addReceiver(auctionMember);
							}	
							String announcement = new Announcement().getAsString();
							
							msgResponse.setContent(announcement);
							send(msgResponse);
							System.out.println(msgResponse.getContent());
							System.out.println((msgResponse.getSender().getName()));
							System.out.println();
						}
						break;
										
					case "order":
						AID auctionator = msg.getSender();
						String[] orderAsRows = msgContent.split("\n");
						String id = orderAsRows[1];
						String startTimeAsString = orderAsRows[2];
						String endTimeAsString = orderAsRows[3];
						
						int startTime = Integer.parseInt(startTimeAsString);
						int endTime = Integer.parseInt(endTimeAsString);
						
						int offerDuration = randomTimer.nextInt(endTime - startTime);
						int offerTime = startTime + 1 + offerDuration;
						String offer = "offer"+ "\n" + id + "\n" + offerTime;
												
						msgResponse.setContent(offer);
						msgResponse.addReceiver(auctionator);
						send(msgResponse);
						System.out.println(msgResponse.getContent());
						System.out.println((msgResponse.getSender().getName()));
						System.out.println();
						break;
					
					case "offer":
						offerCount++;
						AID offerSender = msg.getSender();
						String[] offerAsRows = msgContent.split("\n");
						offers.put(offerSender, offerAsRows);
						if (offerCount == 2) {
							for (Map.Entry<AID, String[]> e : offers.entrySet()) {
								offerAsRows = e.getValue();
								int timeToOffer = Integer.parseInt(offerAsRows[2]);
								if (bestOfferTime == 0 || bestOfferTime > timeToOffer) {  
									bestOfferTime = timeToOffer;
									bestOffer = e.getKey();
								}
							}
							System.out.println("best offer " + offerAsRows[1] + " " + bestOffer.getName() + " time: " + bestOfferTime);
							System.out.println("----------------------------------------------------");
							ACLMessage msgNewAuctionator = new ACLMessage(ACLMessage.INFORM);
							msgNewAuctionator.setContent("setAuctionator");
							msgNewAuctionator.addReceiver(bestOffer);
							send(msgNewAuctionator);
														
							bestOffer = null;
							bestOfferTime = 0;
							offerCount = 0;
							offers.clear();
							isAuctionator = false;
							msgResponse.setContent("start");
							msgResponse.addReceiver(adminAgent);
							send(msgResponse);
							break;
						}
					}
					doWait(5000);
				} else {
					block();
				}
			}
		});
	}
	
	/**
	 * the request for registration in auction for admin
	 * 
	 */
	private class RegistrationRequest extends OneShotBehaviour {		
		@Override
		public void action() {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(adminAgent);
			msg.setContent("rigistration");
			send(msg);
		}
	}
	
	
}

