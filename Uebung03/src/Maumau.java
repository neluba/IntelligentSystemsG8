import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Maumau {

	public static void main(String[] args) {

		String host = "localhost";
		int port = -1; // default-port 1099
		String platform = null; // default name
		boolean isMain = true;
		
		int numberOfPlayers = 3;

		Profile profile = new ProfileImpl(host, port, platform, isMain);
		profile.setParameter("gui", "true");

		// create container
		Runtime runtime = Runtime.instance();
		jade.wrapper.AgentContainer container = runtime.createMainContainer(profile);

		// create agents, 1 gamemaster and several players
		try {
			AgentController agent = container.createNewAgent("Gamemaster", GamemasterAgent.class.getName(), args);
			agent.start();
			
			// one player
			agent = container.createNewAgent("Player", PlayerAgent.class.getName(), args);
			agent.start();
						
			// two dummies
			for(int i = 1; i <= numberOfPlayers - 1; i++) {
				agent = container.createNewAgent("Dummy "+i, DummyAgent.class.getName(), args);
				agent.start();
			}
					
		} catch (StaleProxyException e) {
			throw new RuntimeException(e);
		}
	}
	
}
