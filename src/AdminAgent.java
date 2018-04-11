import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;


public class AdminAgent extends Agent  {

	private RequestServer requestServer;
	private AID auctionator;
	private ArrayList<AID> members;
		
	
	protected void setup() {
		members = new ArrayList<AID>();
		requestServer = new RequestServer();
		requestServer.action();
		System.out.println("Admin starting" + getAID().getName());
	}
				
	private class RequestServer extends CyclicBehaviour {
		public void action() {
			ACLMessage msg = receive();
			if (msg != null) {
				String msgContent = msg.getContent();
				AID msgSender = msg.getSender();
				ACLMessage msgReply = msg.createReply();
				
				if (msgContent.contains("rigistration")) {
					if (auctionator == null) {
						auctionator = msgSender;
						msgReply.setContent("auctionator was set");
					
					} else {
						members.add(msgSender);
						msgReply.setContent("member was set");
					}
				}
				send(msgReply);
			} else {
				block();
			}
		}
	}
		
		
		
		
}
