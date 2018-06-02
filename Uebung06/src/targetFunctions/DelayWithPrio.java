package targetFunctions;

import planning.Order;
import planning.Plan;
import planning.Product;

public class DelayWithPrio implements ITargetFunction {

	@Override
	public boolean checkPlan(Plan plan, ITarget target) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public int getValuation(Plan plan) {
		int valuation = 0;
		for (Order order : plan.getOrders()) {
			int orderPriority = order.getPriority();
			int orderEndtime = order.getEnd();
			// endtime last product in order after planning
			Product product = order.getProduct();
			int productEndtime = product.getEarliestStartTime();
			
			int endtimeDifference = productEndtime - orderEndtime;
			if (endtimeDifference < 0)
				endtimeDifference = 0;
			int orderValuation = endtimeDifference * orderPriority;
			valuation += orderValuation;			
		}
		return valuation;
	}

}
