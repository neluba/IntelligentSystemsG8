
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
		
		Product p11 = new Product();
		Product p12 = new Product();
		Product p13 = new Product();
		Product p21 = new Product();
		Product p22 = new Product();
		Product p23 = new Product();
		Product p31 = new Product();
		Product p32 = new Product();
		Product p33 = new Product();
		
		String[] o1 = 	{"M1","M2","M3"};
		String[] o2 = 	{"M1","M3","M2"};
		String[] o3=  	{"M2","M1","M3"};
		String[] o4 = 	{"M1","M3","M4"};
		String[] o5 =	{"M2","M4","M3"};
		String[] o6 =	{"M4","M2","M3"};
						
		int[] t1 =	{1,2,3};
		int[] t2 =	{2,2,3};
		int[] t3 =	{3,3,2};
		int[] t4 =	{5,2,3};
		int[] t5 =	{1,5,5};
		int[] t6 = 	{3,2,1};

		Variant vP11 = new Variant();
		vP11.setOperations(o1);
		vP11.setTimes(t1);
		Variant vP12 = new Variant();
		vP12.setOperations(o4);
		vP12.setTimes(t2);
		
		Variant vP21 = new Variant();
		vP21.setOperations(o2);
		vP21.setTimes(t3);
		Variant vP22 = new Variant();
		vP22.setOperations(o5);
		vP22.setTimes(t4);
		
		Variant vP31 = new Variant();
		vP31.setOperations(o3);
		vP31.setTimes(t5);
		Variant vP32 = new Variant();
		vP32.setOperations(o6);
		vP12.setTimes(t6);
		
		p11.getVariants().add(vP11);
		p12.getVariants().add(vP12);
		p13.getVariants().add(vP11);
		p13.getVariants().add(vP12);
		
		p21.getVariants().add(vP21);
		p22.getVariants().add(vP22);
		p23.getVariants().add(vP21);
		p23.getVariants().add(vP22);
		
		p31.getVariants().add(vP31);
		p32.getVariants().add(vP32);
		p33.getVariants().add(vP31);
		p33.getVariants().add(vP32);
		
		
		plan1.getProducts().add(p11);
		
		plan2.getProducts().add(p12);
		
		plan3.getProducts().add(p13);
		
		plan4.getProducts().add(p11);
		plan4.getProducts().add(p21);
		
		plan5.getProducts().add(p12);
		plan5.getProducts().add(p22);
		
		plan6.getProducts().add(p11);
		plan6.getProducts().add(p31);
		
		plan7.getProducts().add(p31);
		plan7.getProducts().add(p11);
		
		plan8.getProducts().add(p11);
		plan8.getProducts().add(p21);
		plan8.getProducts().add(p31);
		
		plan9.getProducts().add(p13);
		plan9.getProducts().add(p22);
		plan9.getProducts().add(p32);
		
		plan10.getProducts().add(p13);
		plan10.getProducts().add(p23);
		plan10.getProducts().add(p33);
				

	}
	
	
}
