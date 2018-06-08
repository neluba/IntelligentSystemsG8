package planning;

public class Order implements Cloneable {
	
	private Product product;
	private int end, priority, start, id, quantity;
	
	public Product getProduct() {
		return product;
	}
	
	@Override
	public Order clone() throws CloneNotSupportedException {
		 return (Order) super.clone();
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
