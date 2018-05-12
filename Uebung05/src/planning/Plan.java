package planning;
import java.util.ArrayList;

/**
 * @author IS Group 08
 *
 */
public class Plan {

	private ArrayList<Product> products;
	private ArrayList<Ressource> ressources;
	
	
	public Plan() {
		products = new ArrayList<>();
		ressources = new ArrayList<Ressource>();
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
	
	
	public ArrayList<Ressource> getRessources() {
		return ressources;
	}

	public void setRessources(ArrayList<Ressource> ressources) {
		this.ressources = ressources;
	}

	public void addRessource(Ressource ressource) {
		ressources.add(ressource);
	}
	
	public void planningProduct(int productIndex, int variant) {
		Product product = products.get(productIndex);
		String productName = product.getName();
		Variant productVariant = product.getVariant(variant);
		Ressource[] ressources = productVariant.getRessources();
		int[] operationTimes = productVariant.getOperationTimes();
		
		int ressourcesIndex = 0;
		for(Ressource ressource : ressources) {
			int operationTime = operationTimes[ressourcesIndex];
			ressource.addIntervall(operationTime, productName);
			ressourcesIndex++;
		}
	}
	
}
