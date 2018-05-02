
public class Variant {

	String[] operations;
	int[] times;
	
	
	public Variant() {
		
	}
	
	/**
	 * 
	 * @param operations an array String[i]
	 * 			index i for the sequence of operations, index i=0 for operation 1, index i=1 for operation 2 and so on 
	 * 			i for the ressource where the operation will be done
	 */
	public void setOperations(String[] operations) {
		this.operations = operations;
	}
	
	/**
	 * 
	 * @param times an array int[i]
	 * 			index i for the ressources where the operations will be done, index i=0 for the ressource for operations 1,
	 * 					index i=1 for the ressource for operation 2 and so on 
	 * 			i for the time that the ressource need for an operation
	 */
	public void setTimes(int[] times) {
		this.times = times;
	}
}
