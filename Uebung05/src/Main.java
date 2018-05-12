import java.util.ArrayList;

import planning.Plan;
import planning.Product;
import planning.Ressource;
import planning.Variant;
import targetFunctions.FixedEndTime;
import targetFunctions.TargetEndTime;

/**
 * @author IS Group 08
 *
 */
public class Main {

	
	public static void main (String[] args) {
		
		
		Plan plan = new Plan();
		Ressource m1 = new Ressource("m1");
		plan.addRessource(m1);
		Ressource m2 = new Ressource("m2");
		plan.addRessource(m2);
		Ressource m3 = new Ressource("m3");
		plan.addRessource(m3);
		Ressource m4 = new Ressource("m4");
		plan.addRessource(m4);
		
		
		// product 1 
		Product product1 = new Product("p1");
		plan.addProduct(product1);
		 
		Variant product1Variant1 = new Variant();
		product1.addVariant(product1Variant1);
		product1Variant1.setRessources(new Ressource[]{m1, m2, m3});
		product1Variant1.setOperationTimes(new int[]{2,2,3});
		
		Variant product1Variant2 = new Variant();
		product1.addVariant(product1Variant2);
		product1Variant2.setRessources(new Ressource[]{m4, m2, m1});
		product1Variant2.setOperationTimes(new int[]{4,1,3});
		
		Variant product1Variant3 = new Variant();
		product1.addVariant(product1Variant3);
		product1Variant3.setRessources(new Ressource[]{m4, m1, m1});
		product1Variant3.setOperationTimes(new int[]{5,1,3});
		
		Variant product1Variant4 = new Variant();
		product1.addVariant(product1Variant4);
		product1Variant4.setRessources(new Ressource[]{m2, m1, m3});
		product1Variant4.setOperationTimes(new int[]{2,5,1});
		
		// product 2
		Product product2 = new Product("p2");
		plan.addProduct(product2);
		 
		Variant product2Variant1 = new Variant();
		product2.addVariant(product2Variant1);
		product2Variant1.setRessources(new Ressource[]{m1, m4, m2});
		product2Variant1.setOperationTimes(new int[]{3,2,3});
		
		Variant product2Variant2 = new Variant();
		product2.addVariant(product2Variant2);
		product2Variant2.setRessources(new Ressource[]{m1, m1, m2});
		product2Variant2.setOperationTimes(new int[]{3,7,5});
		
		Variant product2Variant3 = new Variant();
		product2.addVariant(product2Variant3);
		product2Variant3.setRessources(new Ressource[]{m3, m2, m4});
		product2Variant3.setOperationTimes(new int[]{5,1,3});
		
		// product 3
		Product product3 = new Product("p3");
		plan.addProduct(product3);
		 
		Variant product3Variant1 = new Variant();
		product3.addVariant(product3Variant1);
		product3Variant1.setRessources(new Ressource[]{m2, m4, m3});
		product3Variant1.setOperationTimes(new int[]{1,2,1});
		
		Variant product3Variant2 = new Variant();
		product3.addVariant(product3Variant2);
		product3Variant2.setRessources(new Ressource[]{m4, m1, m2});
		product3Variant2.setOperationTimes(new int[]{2,5,3});
		
		Variant product3Variant3 = new Variant();
		product3.addVariant(product3Variant3);
		product3Variant3.setRessources(new Ressource[]{m2, m1, m4});
		product3Variant3.setOperationTimes(new int[]{3,6,5});
		
		
		ArrayList<Product> productList = plan.getProducts();
		
		for(int i = 0; i < plan.getProducts().size(); i++) {
			Product currentProduct = productList.get(i);
			for(int j = 0; j < currentProduct.getVariants().size(); j++) {
				Variant currentVariant = currentProduct.getVariant(j);

				for(int k = 0; k < currentVariant.getRessources().length; k++) {
					System.out.println("Produkt: " + (i+1) 
							+ " | Variante: " + (j+1) 
							+ " | Operation: " + (k+1) 
							+ " | Ressource: " + currentVariant.getRessources()[k].getName() 
							+ " | Dauer: " + currentVariant.getOperationTimes()[k]);
				}
				
			}
		}
		
		
		// plan all variants of products in the plan
		plan.planningProduct(0, 0);
		plan.planningProduct(0, 1);
		//plan.planningProduct(0, 2);
		//plan.planningProduct(0, 3);
		plan.planningProduct(1, 0);
		//plan.planningProduct(1, 1);
		//plan.planningProduct(1, 2);
		plan.planningProduct(2, 0);
		//plan.planningProduct(2, 1);
		//plan.planningProduct(2, 2);
		
		System.out.println("\nPlanning: Produkt 1, Variante 1 & 2; Produkt 2, Variante 1; Produkt 3, Variante 1\n");
		
		System.out.println("m1");
		for (int i = 0; i < m1.getProducts().size(); i++) {
			System.out.print(m1.getProduct(i) + ": " + m1.getIntervall(i)[0] + "-" + m1.getIntervall(i)[1] + " | "); 
		}
		System.out.println("\n----------------------------------------");
		
		System.out.println("m2");
		for (int i = 0; i < m2.getProducts().size(); i++) {
			System.out.print(m2.getProduct(i) + ": " + m2.getIntervall(i)[0] + "-" + m2.getIntervall(i)[1] + " | "); 
		}
		System.out.println("\n ---------------------------------------");
		
		System.out.println("m3");
		for (int i = 0; i < m3.getProducts().size(); i++) {
			System.out.print(m3.getProduct(i) + ": " + m3.getIntervall(i)[0] + "-" + m3.getIntervall(i)[1] + " | "); 
		}
		System.out.println("\n----------------------------------------");
		
		System.out.println("m4");
		for (int i = 0; i < m4.getProducts().size(); i++) {
			System.out.print(m4.getProduct(i) + ": " + m4.getIntervall(i)[0] + "-" + m4.getIntervall(i)[1] + " | "); 
		}
		System.out.println("\n----------------------------------------");
			
		FixedEndTime fixedEndTime = new FixedEndTime();
		TargetEndTime targetEndTime = new TargetEndTime(10);
		boolean target = fixedEndTime.checkPlan(plan, targetEndTime);
		System.out.println("All products are finished before time 10: " + target);
		
	}
	

}
