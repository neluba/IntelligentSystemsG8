import java.util.Random;

/**
 * The Announcement for an order bye the auctionator in an auction.
 * 
 * @author IS Group 08
 *
 */
public class Announcement {

	private static int orderID = 0;
	private int startTime;
	private int endTime;
		
	
	/**
	 * Increment the order-ID and generate a random order with time between 0 and 20.
	 * 
	 */
	public Announcement() {
		orderID++;
		Random randomTimer = new Random(); 
		startTime = 0;
		endTime = randomTimer.nextInt(20) + 1;
	}
	
	/**
	 * Get the order from announcement as String.
	 * 
	 * @return	the order as String 
	 * 
	 */
	public String getAsString() {
		String announcement = "order" + "\n" + "ID: " + orderID + "\n" + startTime + "\n" + endTime;
		return announcement;
	}
	
	
}
