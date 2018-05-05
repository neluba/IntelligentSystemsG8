import java.util.ArrayList;

public class Main {

	
	public static void main (String[] args) {
		
		
		Plan plan = new Plan();
		
		// product 1 
		Product product1 = new Product();
		plan.addProduct(product1);
		 
		Variant product1Variant1 = new Variant();
		product1.addVariant(product1Variant1);
		product1Variant1.setRessources(new String[]{"M1", "M2", "M3"});
		product1Variant1.setOperationTimes(new int[]{2,2,3});
		
		Variant product1Variant2 = new Variant();
		product1.addVariant(product1Variant2);
		product1Variant2.setRessources(new String[]{"M4", "M2", "M1"});
		product1Variant2.setOperationTimes(new int[]{4,1,3});
		
		Variant product1Variant3 = new Variant();
		product1.addVariant(product1Variant3);
		product1Variant3.setRessources(new String[]{"M4", "M5", "M1"});
		product1Variant3.setOperationTimes(new int[]{5,1,3});
		
		Variant product1Variant4 = new Variant();
		product1.addVariant(product1Variant4);
		product1Variant4.setRessources(new String[]{"M2", "M6", "M3"});
		product1Variant4.setOperationTimes(new int[]{2,5,1});
		
		// product 2
		Product product2 = new Product();
		plan.addProduct(product2);
		 
		Variant product2Variant1 = new Variant();
		product2.addVariant(product2Variant1);
		product2Variant1.setRessources(new String[]{"M1", "M4", "M2"});
		product2Variant1.setOperationTimes(new int[]{3,2,3});
		
		Variant product2Variant2 = new Variant();
		product2.addVariant(product2Variant2);
		product2Variant2.setRessources(new String[]{"M1", "M6", "M2"});
		product2Variant2.setOperationTimes(new int[]{3,7,5});
		
		Variant product2Variant3 = new Variant();
		product2.addVariant(product2Variant3);
		product2Variant3.setRessources(new String[]{"M3", "M2", "M5"});
		product2Variant3.setOperationTimes(new int[]{5,1,3});
		
		// product 3
		Product product3 = new Product();
		plan.addProduct(product3);
		 
		Variant product3Variant1 = new Variant();
		product3.addVariant(product3Variant1);
		product3Variant1.setRessources(new String[]{"M2", "M4", "M3"});
		product3Variant1.setOperationTimes(new int[]{1,2,1});
		
		Variant product3Variant2 = new Variant();
		product3.addVariant(product3Variant2);
		product3Variant2.setRessources(new String[]{"M4", "M1", "M5"});
		product3Variant2.setOperationTimes(new int[]{2,5,3});
		
		Variant product3Variant3 = new Variant();
		product3.addVariant(product3Variant3);
		product3Variant3.setRessources(new String[]{"M2", "M1", "M6"});
		product3Variant3.setOperationTimes(new int[]{3,6,5});
		
		
		ArrayList<Product> productList = plan.getProducts();
		
		for(int i = 0; i < plan.getProducts().size(); i++) {
			Product currentProduct = productList.get(i);
			for(int j = 0; j < currentProduct.getVariants().size(); j++) {
				Variant currentVariant = currentProduct.getVariant(j);

				for(int k = 0; k < currentVariant.ressources.length; k++) {
					System.out.println("Produkt: " + (i+1) 
							+ " | Variante: " + (j+1) 
							+ " | Operation: " + (k+1) 
							+ " | Ressource: " + currentVariant.ressources[k] 
							+ " | Dauer: " + currentVariant.operationTimes[k]);
				}
				
			}
		}
				

	}
	
	
}
