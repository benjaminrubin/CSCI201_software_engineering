
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer implements Runnable {

	@SerializedName("order")
	@Expose
	public ArrayList<String> order = null;
	public transient ArrayList<OrderItem> orderItems;
	public int[] orderItemsQuantity; // this stores the quantity of items for each type
	public int customerNumber;
	public Menu menu;
	private Boolean customerServed = false;
	public Timetable TT;

	
	
	public ArrayList<String> getOrder() {
		return order;
	}
	public Boolean getCustomerServed() {
		return this.customerServed;
	}
	
	public int getCustomerNumber() {
		return this.customerNumber;
	}
	
	public void createOrderItemsArray(PreparingOrders PO, int customerNumber) {
		this.TT = new Timetable();
		this.customerNumber = customerNumber;
		// System.out.println("Creating new order array");
		orderItems = new ArrayList<OrderItem>();
		for (int i = 0; i < this.order.size(); i++) {
			OrderItem addme = new OrderItem(this.order.get(i), PO, this);
			this.orderItems.add(addme);
			// System.out.println("\t The order is: " + this.orderItems.get(i).orderName());
		}
		
		
		this.orderItemsQuantity = new int[PO.allItemsOnMenu.size()];
		for(int i = 0; i < this.orderItems.size(); i++) {
			for(int j = 0; j < PO.allItemsOnMenu.size(); j++) {
				if(this.orderItems.get(i).getName().equals(PO.allItemsOnMenu.get(j))) {
					this.orderItemsQuantity[j] += 1;
				}
			}
		}
		
		//order breakdown
//		for(int i = 0; i < PO.allItemsOnMenu.size(); i++) {
//			System.out.println(PO.allItemsOnMenu.get(i) + ": " + this.orderItemsQuantity[i]);
//		}		
//		System.out.println("\n\n\n");
		
		
	}

	public void updateCustomerStatus() {
		for(int i = 0; i < this.orderItems.size(); i++) {
			if(!this.orderItems.get(i).itemIsPrepared()) {
//				System.out.println("item: " + this.orderItems.get(i).getName() + " is still not prepared");
				return;
			}
		}
		this.customerServed = true;
	}
	
	public void run() {

		ExecutorService executors = Executors.newCachedThreadPool();
				
		//Start the threads for each item on the customer's order
		for (int i = 0; i < this.orderItems.size(); i++) {
			executors.execute(this.orderItems.get(i));
		}
		executors.shutdown();
		
		//wait until all orders are complete and change the customer's status
		while(!this.customerServed) {
			updateCustomerStatus();
			System.out.print("");
		}
		Util.printMessage("Completed order " + this.customerNumber + "!");
		TT.setEndTime();
		
//		if (this.customerServed) {
//			System.out.println("in hereEEEEEE?");
//			Util.printMessage("Completed order " + this.customerNumber);
//		}

	}

}