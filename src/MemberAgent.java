import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

public class MemberAgent extends Agent {
	
	private AID adminAgent;
	private RegistrationRequest registrationRequest;
	
	
	public MemberAgent() {
		registrationRequest = new RegistrationRequest();
		setup();
	}
	
	protected void setup() {
		System.out.println(getAID().getName());
	}
	
	public void registration(AID admin) {
		this.adminAgent = adminAgent;
		registrationRequest.action();
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
