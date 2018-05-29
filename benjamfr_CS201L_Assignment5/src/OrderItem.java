
public class OrderItem implements Runnable {
	private String name;
	private PreparingOrders PO;
	private Customer customer;
	private Boolean itemPrepared;

	// constructor
	public OrderItem(String name, PreparingOrders PO, Customer customer) {
		this.name = name;
		this.PO = PO;
		this.customer = customer;
		this.itemPrepared = false;
	}

	public String getName() {
		return this.name;
	}

	public Boolean itemIsPrepared() {
		return this.itemPrepared;
	}
	
	
	public void itemFinishedPreparation() {
		this.itemPrepared = true;
	}
	
	public void run() {
//		System.out.println("Order is " + this.name);

		if (this.PO.menu.getDeepFryer().contains(this.name)) {
//			System.out.println("\tAt the fryer");
			this.PO.df.use(this);
			// break;
		} else if (this.PO.menu.getDrinkMachine().contains(this.name)) {
//			System.out.println("\tAt the drinkmachine");
			this.PO.dm.use(this);
		} else if (this.PO.menu.getGrill().contains(this.name)) {
//			System.out.println("\tAt the grill");
			this.PO.g.use(this);
		} else if (this.PO.menu.getMilkshakeMaker().contains(this.name)) {
//			System.out.println("\tAt the milkshaker");
			this.PO.mm.use(this);
		}
		if(this.itemIsPrepared()) {
//			Util.printMessage(this.getName() + " is prepared for customer number " + this.customer.getCustomerNumber());	
		}
		
	}

}
