import java.util.Random;

public class Announcement {

	private int orderID;
	private int startTime;
	private int endTime;
		
	
	public Announcement() {
		orderID++;
		Random randomTimer = new Random(); 
		startTime = 0;
		endTime = randomTimer.nextInt(20) + 1;
	}
	
	public String getAsString() {
		String announcement = "orderID: " + orderID + "\n" + startTime + "\n" + endTime;
		return announcement;
	}
	
	
}
