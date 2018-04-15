import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * The agent to start and admin the auction.
 * 
 * @author IS Group 08
 *
 */
public class AdminAgent extends Agent  {

	private ArrayList<AID> members;
		
	
	@Override
	protected void setup() {
		members = new ArrayList<AID>();
		
		addBehaviour(new CyclicBehaviour(this) {	
			
			/**
			 * Receive the start-message from user to start the system.
			 * Receive and response the registration-messages from auction-members to registration in an auction.
			 * 
			 */
			@Override
			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					String msgContent = msg.getContent();
					AID msgSender = msg.getSender();
					ACLMessage msgResponse = new ACLMessage(ACLMessage.INFORM);
				
					if (msgContent.contains("start")) {
						msgResponse.setContent("startAuction");
						for (AID member : members) {
							msgResponse.addReceiver(member);
						}
					}
					
					if (msgContent.contains("rigistration")) {
						msgResponse.addReceiver(msgSender);
						if (members.isEmpty()) {
							msgResponse.setContent("setAuctionator");
							System.out.println(msgSender.getName() + " is auctionator");
						} else {
							msgResponse.setContent("setMember");
							System.out.println(msgSender.getName() + " is member");
						}
						members.add(msgSender);
					}
					send(msgResponse);
				} else {
					block();
				}
			}
		});
	}
	
		
}
