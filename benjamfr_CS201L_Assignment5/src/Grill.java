import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Grill extends Thread{
	private static Semaphore semaphore = new Semaphore(5);

	public void use(OrderItem item) {
		try {
			semaphore.acquire();
//			Util.printMessage("\t" + item + " added to the grill");
			Thread.sleep(5000);
		} catch (InterruptedException ie) {
			System.out.println("MyThread.run IE: " + ie.getMessage());
		} finally {
			semaphore.release();
			item.itemFinishedPreparation();
//			Util.printMessage("\t" + item + " finished at the grill");
		}
	}
}
