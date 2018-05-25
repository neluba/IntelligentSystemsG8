package planning;
import java.util.ArrayList;

/**
 * @author IS Group 08
 *
 */
public class Plan {

	// the list of products to plan in plan
	private ArrayList<Product> products;
	// the list of ressources which use in plan 
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
	
	/**
	 * Planning a product on the setted ressource. The ressource was setting in the variant of product.
	 * All products in the list products can be plan.
	 * 
	 * @param productIndex 	the index of product in the list products to plan
	 * @param variant the 	variant of product to plan
	 */
	public void planningProduct(int productIndex, int variant) {
		Product product = products.get(productIndex);
		Variant productVariant = product.getVariant(variant);
		Ressource[] ressources = productVariant.getRessources();
		int[] operationTimes = productVariant.getOperationTimes();
		
		int ressourcesIndex = 0;
		for(Ressource ressource : ressources) {
			int operationTime = operationTimes[ressourcesIndex];
			ressource.addIntervall(operationTime, product, variant);
			ressourcesIndex++;
		}
	}
	
}
