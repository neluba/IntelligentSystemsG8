
public class Main {

	
	public static void main (String[] args) {
		
		Plan plan1 = new Plan();
		Plan plan2 = new Plan();
		Plan plan3 = new Plan();
		Plan plan4 = new Plan();
		Plan plan5 = new Plan();
		Plan plan6 = new Plan();
		Plan plan7 = new Plan();
		Plan plan8 = new Plan();
		Plan plan9 = new Plan();
		Plan plan10 = new Plan();
		
		Product p1 = new Product();
		Product p2 = new Product();
		Product p3 = new Product();
		
		Variant vP1 = new Variant();
		String[] oVP1 = {"M1","M2","M3"};
		int[] tVP1 = {2,2,3};
		vP1.setOperations(oVP1);
		vP1.setTimes(tVP1);
		p1.getVariants().add(vP1);
				
		Variant vP2 = new Variant();
		String[] oVP2 = {"M1","M4","M2"};
		int[] tVP2 = {3,2,3};
		vP2.setOperations(oVP2);
		vP2.setTimes(tVP2);
		p2.getVariants().add(vP2);		
		
		Variant vP3 = new Variant();
		String[] oVP3 = {"M2","M4","M3"};
		int[] tVP3 = {1,2,1};
		vP3.setOperations(oVP3);
		vP3.setTimes(tVP3);
		p3.getVariants().add(vP3);
		
		
		plan1.getProducts().add(p1);
		plan2.getProducts().add(p2);
		plan3.getProducts().add(p3);
		
		

	}
	
	
}
