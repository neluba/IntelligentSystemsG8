package planning;
import java.util.ArrayList;

/**
 * 
 * @author IS Group 08
 * 
 * The ressource with a list of time-intervalls and a list of products for the time-intervalls.
 * In each time-intervall the ressource will do an operation for a product.
 * The index of time-intervalls mapped the index of products.
 *
 */
public class Ressource {
	
	private String name;
	private ArrayList<int[]> intervalls;
	private ArrayList<String> products;
	
	
	public Ressource(String name) {
		this.name = name;
		intervalls = new ArrayList<int[]>();
		products = new ArrayList<String>();
	}
	
	/**
	 * Add an time-intervall to the list of time-intervalls and
	 * a product to the list of products for the time-intervall. 
	 * 
	 * @param time	the time for the time-intervall of an operation  
	 * @param product the product for the time-intervall
	 */
	public void addIntervall(int time, String product) {
		int[] intervall = new int[2];
		if (intervalls.isEmpty()) {
			intervall[0] = 0;
			intervall[1] = time;
		} else {
			int[] lastIntervall = intervalls.get(intervalls.size() - 1);
			int lastIntervallTime = lastIntervall[1];
			intervall[0] = lastIntervallTime;
			intervall[1] = lastIntervallTime + time;
		}
		intervalls.add(intervall);
		products.add(product);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<int[]> getIntervalls() {
		return intervalls;
	}

	public void setIntervalls(ArrayList<int[]> intervalls) {
		this.intervalls = intervalls;
	}
	
	public int[] getIntervall(int index) {
		return intervalls.get(index);
	}
	
	public void setIntervall(int index, int[] intervall) {
		intervalls.add(index, intervall);
	}

	public ArrayList<String> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<String> products) {
		this.products = products;
	}
	
	public String getProduct(int index) {
		return products.get(index);
	}
	
	public void setProduct(int index, String product) {
		products.add(index, product);
	}
	
	/*
	public boolean addIntervallWithTimewindow(String product, int start, int end) {
		int operationTime = end - start;
		int[] intervall = new int[2];
				
		int intervallIndex = 0;
		for (int[] currentIntervall : intervalls) {
			int currentIntervallEnd = currentIntervall[1];
			if (currentIntervallEnd >= start) {
				
				if (intervalls.size() > intervallIndex + 1) {
					int[] nextIntervall = intervalls.get(intervallIndex + 1); 
					int nextIntervallStart = nextIntervall[0];
					int windowTime = nextIntervallStart - currentIntervallEnd;
					operationTime = end - start;
					if (windowTime >= operationTime) {
						intervall[0] = currentIntervallEnd;
						intervall[1] = currentIntervallEnd + operationTime;
						intervalls.add(intervall);
						products.add(intervallIndex, product);
						break;
					}
				} else {
					intervall[0] = currentIntervallEnd;
					intervall[1] = currentIntervallEnd + operationTime;
					intervalls.add(intervall);
					products.add(product);
				}
			}
			intervallIndex++;
		}
		
		if (intervall[1] <= end) {
			return true;
		} else {
			return false;
		}
	}
	*/
	
	
	
	
}
