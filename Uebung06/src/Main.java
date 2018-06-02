import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import planning.Order;
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

	
	public static void main (String[] args) throws IOException {
		
		
		
		Gson gson = new Gson(); 
		String filename = "test_data_small.json";
		JsonReader reader = new JsonReader(new FileReader(filename));
		JsonObject json = gson.fromJson(reader, JsonObject.class); 
		
		
		JsonArray ressources = json.getAsJsonArray("resources"); 
		// the resourceList holds all resources from the json array
		ArrayList<Ressource> ressourceList = new ArrayList<Ressource>();
		
			
		for (int i = 0; i < ressources.size(); i++) {
			ressourceList.add(new Ressource(ressources.get(i).getAsString()));
		}
				
		
		JsonArray products = json.getAsJsonArray("products"); 
		// the productList holds all products from the json array
		ArrayList<Product> productList = new ArrayList<Product>();
		
			
		for (int i = 0; i < products.size(); i++) {
			Product product = new Product();
			// get all variants from json 
			JsonArray variants = products.get(i).getAsJsonObject().getAsJsonArray("variants");
			for (int j = 0; j < variants.size(); j++) {
				Variant variant = new Variant();
				// get all operations from json and add it to variant to plan it
				JsonArray operations = variants.get(j).getAsJsonObject().getAsJsonArray("operations");
				for(int k = 0; k < operations.size(); k++) {
					JsonObject operationJsonObject = operations.get(k).getAsJsonObject();
					//int operationIndex = operationJsonObject.get("index").getAsInt();
					Integer operationTime = operationJsonObject.get("duration").getAsInt();
					variant.addOperationTime(operationTime);
					String ressourceName = operationJsonObject.get("resource").getAsString();
					for (Ressource ressource : ressourceList) {
						if (ressourceName.equals(ressource.getName())) 
							variant.addRessource(ressource);
					}
				}
				product.addVariant(variant);
			}
		// set product name and add it to the list 
		product.setName(products.get(i).getAsJsonObject().get("name").getAsString());
		productList.add(product);
		}

		
		JsonArray orders = json.getAsJsonArray("orders"); 
		// the ordersList holds all orders from the json array
		ArrayList<Order> orderList = new ArrayList<Order>();
		
			
		for (int i = 0; i < orders.size(); i++) {
			// create a new order and set all attributes
			Order order = new Order();
			JsonObject orderJsonObject = orders.get(i).getAsJsonObject();
			String productName = orderJsonObject.get("product").getAsString();
			for (Product product : productList) {
				if (productName.equals(product.getName()))
					try {
						order.setProduct(orderProduct);
					} catch (CloneNotSupportedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			order.setEnd(orderJsonObject.get("end").getAsInt());
			order.setPriority(orderJsonObject.get("priority").getAsInt());
			order.setStart(orderJsonObject.get("start").getAsInt());
			order.setQuantity(orderJsonObject.get("id").getAsInt());
			
			orderList.add(order);
		}
		
		
		Plan plan = new Plan();
		plan.setRessources(ressourceList);
		plan.setOrders(orderList);

		
		for (int i = 0; i < plan.getOrders().size(); i++) {
			plan.planningProductWithTimewindow(i, 0);
		}
		
		for (Ressource ressource : plan.getRessources()) {
			System.out.println(ressource.getName());
						
			int intervallIndex = 0;
			for (Product product : ressource.getProducts()) {
				int[] intervall = ressource.getIntervalls().get(intervallIndex);
				int start = intervall[0];
				int end = intervall[1];
				intervallIndex++;
			}
			System.out.println();
		}
			
		
	
	}
	

}
