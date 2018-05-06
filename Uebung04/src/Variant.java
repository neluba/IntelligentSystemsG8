
/**
 * @author IS Group 08
 *
 */
public class Variant {

	String[] ressources;
	int[] operationTimes;
	
	
	public Variant() {
		
	}
	
	/**
	 * index i stands for the operation
	 * @param ressources
	 */
	public void setRessources(String[] ressources) {
		this.ressources = ressources;
	}
	
	
	/**
	 * 
	 * @param operation times
	 * 			index i for the ressources where the operations will be done, index i=0 for the ressource for operations 1,
	 * 					index i=1 for the ressource for operation 2 and so on 
	 * 			i for the time that the ressource need for the operation
	 */
	public void setOperationTimes(int[] operationTimes) {
		this.operationTimes = operationTimes;
	}
}
