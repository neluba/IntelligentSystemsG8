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
			
			@Override
			public void action() {
				doWait(5000);
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
						Iterator receiverIterator = msg.getAllReceiver();
						while (receiverIterator.hasNext()) {
							AID member = (AID) receiverIterator.next();
							members.add(member);
						}
						if (isAuctionator == true) {
							String announcement = new Announcement().getAsString();
							msgResponse.setContent(announcement);
							String auctionatorName = this.getAgent().getName();
							System.out.println(announcement);
							System.out.println(auctionatorName);
							System.out.println();
								for (AID member : members) {
									if (!member.getName().contains(auctionatorName))
											msgResponse.addReceiver(member);
								}
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
						
						Random randomTimer = new Random();
						int offerDuration = randomTimer.nextInt(endTime - startTime);
						int offerTime = startTime + 1 + offerDuration;
						String offer = "offer"+ "\n" + id + "\n" + offerTime;
												
						msgResponse.setContent(offer);
						msgResponse.addReceiver(auctionator);
						break;
					
					case "offer":
						offerCount++;
						AID offerSender = msg.getSender();
						System.out.println(msgContent);
						System.out.println(offerSender.getName());
						System.out.println();
												
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
							System.out.println("");
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
							break;
						}
					}
					send(msgResponse);
				} else {
					block();
				}
			}
		});
	}
	
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
