import de.competition.thesis.agents.defaultagents.BlackBoard;
import de.test.agents.*;
import de.test.evaluator.Evaluator;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

public class Main {

    public static void main(String[] args) {

        Runtime rt = Runtime.instance();

        // Exit the JVM when there are no more containers around
        rt.setCloseVM(true);
        System.out.print("runtime created\n");

        // Create a default profile
        Profile profile = new ProfileImpl(null, 1200, null);
        profile.setParameter("gui", "false");
        System.out.print("profile created\n");

        System.out.println("Launching a whole in-process platform..."+profile);
        jade.wrapper.AgentContainer mainContainer = rt.createMainContainer(profile);

        // now set the default Profile to start a container
        ProfileImpl pContainer = new ProfileImpl(null, 1200, null);
        jade.wrapper.AgentContainer cont = rt.createAgentContainer(pContainer);
        System.out.println("Launching the agent container \n\t"+pContainer);

        String blackBoardClassName = BlackBoard.class.getName();
        String problemLoaderClassName = ProblemLoader.class.getName();
        String evaluatorClassName = Evaluator.class.getName();
        String hillClassName = HillClimbingAgent.class.getName();
        String sAClassName = SimulatedAnnealingAgent.class.getName();
        String tAClassName = ThresholdAcceptingAgent.class.getName();
        String gDClassName = GreatDelugeAgent.class.getName();
        String tabuClassName = TabuSearchAgent.class.getName();

        AgentController probleamloader = null;
        AgentController blackboard = null;
        AgentController evaluator = null;
        AgentController hillclimbing = null;
        AgentController simulatedannealing = null;
        AgentController thresholdaccepting = null;
        AgentController greatdeluge = null;
        AgentController tabusearch = null;
        
        try {
            probleamloader = cont.createNewAgent("Problemloader", problemLoaderClassName, new Object[0]);
            evaluator = cont.createNewAgent("DefaultEvaluator", evaluatorClassName, new Object[0]);
            hillclimbing = cont.createNewAgent("Hillclimbing", hillClassName, new Object[0]);
            simulatedannealing = cont.createNewAgent("Simulated Annealing", sAClassName, new Object[0]);
            thresholdaccepting = cont.createNewAgent("Threshold Accepting", tAClassName, new Object[0]);
            greatdeluge = cont.createNewAgent("Great Deluge", gDClassName, new Object[0]);
            tabusearch = cont.createNewAgent("Tabusearch", tabuClassName, new Object[0]);
            blackboard = cont.createNewAgent("Blackboard", blackBoardClassName, new Object[0]);

            blackboard.start();
            probleamloader.start();
            hillclimbing.start();
            simulatedannealing.start();
            thresholdaccepting.start();
            greatdeluge.start();
            tabusearch.start();
            evaluator.start();

        } catch (StaleProxyException e) {
            System.out.println("Failure in main...");
        }

    }

}
