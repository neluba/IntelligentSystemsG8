package targetFunctions;

/**
 * The target to check a plan for keeping a fixed endtime.
 * 
 * @author IS Group 08
 *
 */
public class TargetEndTime implements ITarget {

	private int endTime;
	
		
	public TargetEndTime(int endTime) {
		this.endTime = endTime;
	}


	public int getEndTime() {
		return endTime;
	}


	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	
}
