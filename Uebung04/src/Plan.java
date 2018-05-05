import java.util.ArrayList;

public class Plan {

	private ArrayList<Product> products;
	
	
	public Plan() {
		products = new ArrayList<>();
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	
	public void addProduct(Product product) {
		this.products.add(product);
	}
	
	public Product getProduct(int index) {
		return this.products.get(index);
	}
	
}
