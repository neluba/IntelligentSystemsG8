package planning;
import java.util.ArrayList;

/**
 * @author IS Group 08
 *
 */
public class Product {

	private String name;
	private String orderIndex;
	private ArrayList<Variant> variants;
	public String getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}

	private int variantIndex;
	private int earliestStartTime;
	
	
	public int getEarliestStartTime() {
		return earliestStartTime;
	}

	public void setEarliestStartTime(int earliestStartTime) {
		this.earliestStartTime = earliestStartTime;
	}

	public int getVariantIndex() {
		return variantIndex;
	}

	public void setVariantIndex(int variantIndex) {
		this.variantIndex = variantIndex;
	}

	public Product() {
		variants = new ArrayList<Variant>();
		earliestStartTime = 0;
	}
	
	public Product(Product product, String orderIndex) throws CloneNotSupportedException {
		name = product.getName();
		this.orderIndex = orderIndex; 
		variants = new ArrayList<Variant>();
				
		for (Variant variant : product.getVariants()) {
			variants.add(variant.clone());	
		}
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
