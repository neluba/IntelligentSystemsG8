package planning;
import java.util.ArrayList;

/**
 * @author IS Group 08
 *
 */
public class Plan {

	// the list of orders to plan in plan
	private ArrayList<Order> orders;
	// the list of ressources which use in plan 
	private ArrayList<Ressource> ressources;
	
	
	public Plan() {
		orders = new ArrayList<>();
		ressources = new ArrayList<Ressource>();
	}

	public ArrayList<Order> getOrders() {
		return orders;
	}

	public void setOrders(ArrayList<Order> orders) {
		this.orders = orders;
	}
	
	public void addOrder(Order order) {
		this.orders.add(order);
	}
	
	public Order getOrder(int index) {
		return orders.get(index);
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
	public void planningProduct(int orderIndex, int productVariant) {
		Order order = orders.get(orderIndex);
		Product product = order.getProduct();
		Variant variant = product.getVariant(productVariant);
		ArrayList<Ressource> ressources = variant.getRessources();
		ArrayList<Integer> operationTimes = variant.getOperationTimes();
		
		int ressourcesIndex = 0;
		for(Ressource ressource : ressources) {
			int operationTime = operationTimes.get(ressourcesIndex);
			ressource.addIntervall(operationTime, product);
			ressourcesIndex++;
		}
	}
	
	/**
	 * Planning a product on the setted ressource. The ressource was setting in the variant of product.
	 * All products in the list products can be plan.
	 * 
	 * @param productIndex 	the index of product in the list products to plan
	 * @param variant the 	variant of product to plan
	 */
	public void planningProductWithTimewindow(int orderIndex, int productVariantIndex) {
		Order order = orders.get(orderIndex);
		Product product = order.getProduct();
		Variant variant = product.getVariant(productVariantIndex);
		product.setVariantIndex(productVariantIndex);
		ArrayList<Ressource> ressources = variant.getRessources();
		ArrayList<Integer> operationTimes = variant.getOperationTimes();
		
		int ressourcesIndex = 0;
		for(Ressource ressource : ressources) {
			int operationTime = operationTimes.get(ressourcesIndex);
			int startTime = order.getStart();
			ressource.addIntervallWithTimewindow(operationTime, startTime, product);
			ressourcesIndex++;
		}
	}
	
}
