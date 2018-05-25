package planning;
import java.util.ArrayList;

/**
 * @author IS Group 08
 *
 */
public class Product {

	private String name;
	private ArrayList<Variant> variants;
	private int earliestStartTime;
	
	
	public Product() {
		earliestStartTime = 0;
	}
	
	public int getEarliestStartTime() {
		return earliestStartTime;
	}

	public void setEarliestStartTime(int earliestStartTime) {
		this.earliestStartTime = earliestStartTime;
	}

	public Product(String name) {
		this.name = name;
		variants = new ArrayList<Variant>();
	}

	public ArrayList<Variant> getVariants() {
		return variants;
	}

	public void setVariants(ArrayList<Variant> variants) {
		this.variants = variants;
	}
	
	public void addVariant(Variant variant) {
		this.variants.add(variant);
	}
	
	public Variant getVariant(int index) {
		return this.variants.get(index);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
