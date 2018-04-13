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

public class MemberAgent extends Agent {
		
	private static final long serialVersionUID = 1L;
	private AID adminAgent;
	private boolean isAuctionator;
	private ArrayList<AID> members;
	private HashMap<AID, String[]> offers;
	private int offerCount;
	private int bestOfferTime;
	private AID bestOffer;
				
		
	protected void setup() {
		adminAgent = new AID("admin", AID.ISLOCALNAME);
		members = new ArrayList<AID>();
		offers = new HashMap<AID, String[]>();
		
		RegistrationRequest registrationRequest = new RegistrationRequest();
		registrationRequest.action();
		
		addBehaviour(new CyclicBehaviour(this) {
			
			private static final long serialVersionUID = 1L;
			@Override
			public synchronized void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					String msgContent = msg.getContent();
					ACLMessage msgResponse = new ACLMessage(ACLMessage.INFORM);	
										
					if (msgContent.contains("setAuctionator")) {
						isAuctionator = true;
						System.out.println("Auctionator is setting");
					}
					
					if (msgContent.contains("startAuction")) {
						Iterator receiverIterator = msg.getAllReceiver();
						while (receiverIterator.hasNext()) {
							AID member = (AID) receiverIterator.next();
							members.add(member);
						}
						
						if (isAuctionator == true) {
							String announcement = new Announcement().getAsString();
							System.out.println(announcement);
							msgResponse.setContent(announcement);
							for (AID member : members) {
								msgResponse.addReceiver(member);
							}
						}
						send(msgResponse);
					}
					
					if (msgContent.contains("orderID")) {
						AID auctionator = msg.getSender();
						String[] msgContentAsRows = msgContent.split("\n");
						String id = msgContentAsRows[0];
						String startTimeAsString = msgContentAsRows[1];
						String endTimeAsString = msgContentAsRows[2];
						
						int startTime = Integer.parseInt(startTimeAsString);
						int endTime = Integer.parseInt(endTimeAsString);
						
						Random randomTimer = new Random();
						int offerDuration = randomTimer.nextInt(endTime - startTime + 1);
						int offerTime = startTime + offerDuration;
						String offer = "offerID: "+ id + "\n" + offerTime;
												
						msgResponse.setContent(offer);
						msgResponse.addReceiver(auctionator);
						send(msgResponse);
					}
					
					if (msgContent.contains("offerID")) {
						offerCount++;
						AID offer = msg.getSender();
						System.out.println(offer);
						System.out.println(msgContent);
						String[] msgContentAsRows = msgContent.split("\n");
						offers.put(offer, msgContentAsRows);
						if (offerCount == 2) {
							for (Map.Entry<AID, String[]> e : offers.entrySet()) {
								msgContentAsRows = e.getValue();
								int offerTime = Integer.parseInt(msgContentAsRows[1]);
								if (bestOfferTime == 0 || bestOfferTime > offerTime) {  
									bestOfferTime = offerTime;
									bestOffer = e.getKey();
								}
							}
							msgResponse.setContent("setAuctionator");
							msgResponse.addReceiver(bestOffer);
							System.out.println("Best Offer: " + bestOffer.getName() + "Offer Time: " + bestOfferTime);
							send(msgResponse);
							bestOffer = null;
							bestOfferTime = 0;
							offerCount = 0;
							offers.clear();
							isAuctionator = false;
						}
					}
				} else {
					block();
				}
			}
		});
	}
	
	private class RegistrationRequest extends OneShotBehaviour {
		
		private static final long serialVersionUID = 1L;
		@Override
		public void action() {
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(adminAgent);
			msg.setContent("rigistration");
			send(msg);
		}
	}
	

}
