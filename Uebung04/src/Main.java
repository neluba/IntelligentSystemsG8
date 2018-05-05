import java.util.ArrayList;

public class Main {

	
	public static void main (String[] args) {
		
		
		Plan plan = new Plan();
		
		// product 1 
		Product product1 = new Product();
		plan.addProduct(product1);
		 
		Variant product1Variant = new Variant();
		product1.addVariant(product1Variant);
		product1Variant.setRessources(new String[]{"M1", "M2", "M3"});
		product1Variant.setOperationTimes(new int[]{2,2,3});
		
		// product 2
		Product product2 = new Product();
		plan.addProduct(product2);
		 
		Variant product2Variant = new Variant();
		product2.addVariant(product2Variant);
		product2Variant.setRessources(new String[]{"M1", "M4", "M2"});
		product2Variant.setOperationTimes(new int[]{3,2,3});
		
		// product 3
		Product product3 = new Product();
		plan.addProduct(product3);
		 
		Variant product3Variant = new Variant();
		product3.addVariant(product3Variant);
		product3Variant.setRessources(new String[]{"M2", "M4", "M3"});
		product3Variant.setOperationTimes(new int[]{1,2,1});
		
		
		// product 4
		Product product4 = new Product();
		plan.addProduct(product4);
		 
		Variant product4Variant = new Variant();
		product4.addVariant(product4Variant);
		product4Variant.setRessources(new String[]{"M4", "M2", "M1"});
		product4Variant.setOperationTimes(new int[]{4,1,3});
		
		
		// product 5
		Product product5 = new Product();
		plan.addProduct(product5);
		 
		Variant product5Variant = new Variant();
		product5.addVariant(product5Variant);
		product5Variant.setRessources(new String[]{"M4", "M5", "M1"});
		product5Variant.setOperationTimes(new int[]{5,1,3});
		
		// product 6
		Product product6 = new Product();
		plan.addProduct(product6);
		 
		Variant product6Variant = new Variant();
		product6.addVariant(product6Variant);
		product6Variant.setRessources(new String[]{"M2", "M6", "M3"});
		product6Variant.setOperationTimes(new int[]{2,5,1});
		
		
		// product 7
		Product product7 = new Product();
		plan.addProduct(product7);
		 
		Variant product7Variant = new Variant();
		product7.addVariant(product7Variant);
		product7Variant.setRessources(new String[]{"M1", "M6", "M2"});
		product7Variant.setOperationTimes(new int[]{3,7,5});
		
		
		// product 8
		Product product8 = new Product();
		plan.addProduct(product8);
		 
		Variant product8Variant = new Variant();
		product8.addVariant(product8Variant);
		product8Variant.setRessources(new String[]{"M3", "M2", "M5"});
		product8Variant.setOperationTimes(new int[]{5,1,3});
		
		
		// product 9
		Product product9 = new Product();
		plan.addProduct(product9);
		 
		Variant product9Variant = new Variant();
		product9.addVariant(product9Variant);
		product9Variant.setRessources(new String[]{"M4", "M1", "M5"});
		product9Variant.setOperationTimes(new int[]{2,5,3});
		
		
		// product 10
		Product product10 = new Product();
		plan.addProduct(product10);
		 
		Variant product10Variant = new Variant();
		product10.addVariant(product10Variant);
		product10Variant.setRessources(new String[]{"M2", "M1", "M6"});
		product10Variant.setOperationTimes(new int[]{3,6,5});
				
		
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
