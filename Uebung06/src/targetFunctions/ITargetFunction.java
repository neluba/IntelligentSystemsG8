package targetFunctions;

import planning.Plan;

/**
 * @author IS Group 08
 *
 */
public interface ITargetFunction {

	/**
	 * Checks a target in a plan.
	 * 
	 * @param plan		the plan where checked the target
	 * @param target	the target what checked in plan
	 * @return			true, if target was true
	 */
	public boolean checkPlan(Plan plan, ITarget target);

	/**
	 *  
	 * @param 	plan
	 * @return	a valuation of the plan 
	 */
	public int getValuation(Plan plan);
	
}
