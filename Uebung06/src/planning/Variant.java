package planning;

import java.util.ArrayList;


public class Variant {
	
	public ArrayList<Operation> operations;
	
	public Variant() {
		operations = new ArrayList<Operation>();
	}

	public ArrayList<Operation> getOperation() {
		return operations;
	}

	public void setOperation(ArrayList<Operation> operations) {
		this.operations = operations;
	}
	
	public void addOperation(Operation operation) {
		operations.add(operation);
	}
	
	public void removeOperation(int index) {
		operations.remove(index);
	}
	
	public void removeOperation(Operation object) {
		operations.remove(object);
	}
	
	
}
