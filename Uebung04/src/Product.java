import java.util.ArrayList;

public class Product {

	private ArrayList<Variant> variants;
	
	
	public Product() {
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
	
}
