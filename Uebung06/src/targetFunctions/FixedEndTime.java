package targetFunctions;

import java.util.ArrayList;

import planning.Plan;
import planning.Ressource;

/**
 * The function that check a plan for keeping a fixed endtime.
 * 
 * @author IS Group 08
 *
 */
public class FixedEndTime implements ITargetFunction {

	
	@Override
	public boolean checkPlan(Plan plan, ITarget target) {
		TargetEndTime targetEndTime = (TargetEndTime) target;
		int fixedEndtime = targetEndTime.getEndTime();
		
		int planEndTime = 0;
		for (Ressource ressource : plan.getRessources()) {
			ArrayList<int[]> intervalls = ressource.getIntervalls();
			int[] lastIntervall = intervalls.get(intervalls.size() - 1);
			int lastTime = lastIntervall[1];
			if (planEndTime < lastTime)
				planEndTime = lastTime;
		}
		
		if (planEndTime < fixedEndtime) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int getValuation(Plan plan) {
		// TODO Auto-generated method stub
		return 0;
	}


}
