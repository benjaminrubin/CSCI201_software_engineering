import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class MilkshakeMaker extends Thread{
	private static Semaphore semaphore = new Semaphore(2);

	public void use(OrderItem item) {
		try {
			semaphore.acquire();
//			Util.printMessage("\t" + item + " added to the milkshake maker");
			Thread.sleep(3000);
		} catch (InterruptedException ie) {
			System.out.println("MyThread.run IE: " + ie.getMessage());
		} finally {
			semaphore.release();
			item.itemFinishedPreparation();
//			Util.printMessage("\t" + item + " finished with the milkshake maker");
		}
	}
}
