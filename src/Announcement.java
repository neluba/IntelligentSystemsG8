import java.util.Random;

public class Announcement {

	private static int orderID = 0;
	private int startTime;
	private int endTime;
		
	
	public Announcement() {
		orderID++;
		Random randomTimer = new Random(); 
		startTime = 0;
		endTime = randomTimer.nextInt(20) + 1;
	}
	
	public String getAsString() {
		String announcement = "order" + "\n" + "ID: " + orderID + "\n" + startTime + "\n" + endTime;
		return announcement;
	}
	
	
}
