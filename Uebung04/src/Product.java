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
	
	
}
