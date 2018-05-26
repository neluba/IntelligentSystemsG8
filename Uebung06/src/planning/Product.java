package planning;
import java.util.ArrayList;

/**
 * @author IS Group 08
 *
 */
public class Product {

	private String name;
	private ArrayList<Variant> variants;
	
	
	public Product() {
		variants = new ArrayList<Variant>();
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
