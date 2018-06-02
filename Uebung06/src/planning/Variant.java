package planning;

import java.util.ArrayList;

/**
 * @author IS Group 08
 *
 */
public class Variant implements Cloneable {

	ArrayList<Ressource> ressources;
	ArrayList<Integer> operationTimes;
	
	
	public Variant() {
		ressources = new ArrayList<Ressource>();
		operationTimes = new ArrayList<Integer>();
	}
	
	@Override
	public Variant clone() throws CloneNotSupportedException {
		 return (Variant) super.clone();
	}
	
	/**
	 * 
	 * @param operation times
	 * 			index i for the ressources where the operations will be done, index i=0 for the ressource for operations 1,
	 * 					index i=1 for the ressource for operation 2 and so on 
	 * 			i 		for the time that the ressource need for the operation
	 */
	public void addOperationTime(Integer operationTime) {
		operationTimes.add(operationTime);
	}

	public int getOperationTime(int index) {
		return operationTimes.get(index);
	}
	
	
	public void addRessource(Ressource ressource) {
		ressources.add(ressource);
	}
	
	public Ressource getRessource(int index) {
		return ressources.get(index);
	}

	public ArrayList<Ressource> getRessources() {
		return ressources;
	}

	public void setRessources(ArrayList<Ressource> ressources) {
		this.ressources = ressources;
	}

	public ArrayList<Integer> getOperationTimes() {
		return operationTimes;
	}

	public void setOperationTimes(ArrayList<Integer> operationTimes) {
		this.operationTimes = operationTimes;
	}

	
	
	
}

