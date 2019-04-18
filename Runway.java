import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Runway {

	// Atomic object variables to access different resources
	public static final Gate1 g = new Gate1();
	public static final Gate2 g1 = new Gate2();
	public static final ReentrantLock lock = new ReentrantLock();
	static int completed = 0;

	// this is the method where we make use of fine grained locking using Reentrant
	// lock
	public void accessRunway() throws InterruptedException {
		int AirplaneObjectId = Integer.parseInt(Thread.currentThread().getName());
		Airplane workOn = Main.tracker[AirplaneObjectId];
		/*
		 * System.out.println(" Thread -- " + workOn.getName() + ",  State -- " +
		 * workOn.getCurrentStateName(workOn.getCurrentState()));
		 */
		// if condition is when the Plane is landing
		if (workOn.getCurrentState() == 0) {
			SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
					workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
			// SwingUI.model.setValueAt(workOn.getCurrentStateName(workOn.getCurrentState()),
			// AirplaneObjectId, 1);
			lock.lock();
			workOn.setCurrentState(1);
			// SwingUI.model.setValueAt(workOn.getCurrentStateName(workOn.getCurrentState()),
			// AirplaneObjectId, 1);
			SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
					workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
			SwingUI.updateResourceUsedBy("R", AirplaneObjectId);

			try {
				// once a thread acquires the lock, we make it sleep for 3 seconds, i.e time
				// given for our Runway
				Thread.sleep(3000);
			} finally {
				// Once the thread wakes up, we make a call to acquire either of the Gate
				// resource depending on the resource which is free
				// or has less threads waiting for it
				if (!Gate1.lockG.isLocked()) {
					// System.out.println("In if condition");
					lock.unlock();
					g.accessGate(new Random().nextInt(7));
				} else if (!Gate2.lockG1.isLocked()) {
					// System.out.println("Else if");
					lock.unlock();
					g1.accessGate1(new Random().nextInt(7));
				} else {
					// System.out.println("Else");
					workOn.setCurrentState(2);
					// SwingUI.model.setValueAt(workOn.getCurrentStateName(workOn.getCurrentState()),
					// AirplaneObjectId, 1);
					SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
							workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
					int curr_count_G = Gate1.lockG.getQueueLength();
					int curr_count_G1 = Gate2.lockG1.getQueueLength();

					if (curr_count_G > curr_count_G1) {
						lock.unlock();
						g1.accessGate1(new Random().nextInt(6) + 1);
					} else {
						lock.unlock();
						SwingUI.resetResourceTable(0);
						g.accessGate(new Random().nextInt(6) + 1);
					}
				}

			}
			// System.out.println("Completed -- " + Thread.currentThread().getName());
		} else { // else condition is for when the thread is returning from gate and ready for
					// takeoff
			/*
			 * date = new Date(); System.out.println("Completed execution - " +
			 * Thread.currentThread().getName() + "  Time --" + dateFormat.format(date));
			 */
			// System.out.println(" Taking OFF Thread -- " +
			// Thread.currentThread().getName());
			lock.lock();
			workOn.setCurrentState(5);
			// SwingUI.model.setValueAt(workOn.getCurrentStateName(workOn.getCurrentState()),
			// AirplaneObjectId, 1);
			SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
					workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
			SwingUI.updateResourceUsedBy("R", AirplaneObjectId);
			try {
				Thread.sleep(3000);
			} finally {
				lock.unlock();
				SwingUI.resetResourceTable(0);
				workOn.setCurrentState(6);
			}
			long time_taken = (new Date().getTime() - workOn.getBeginTimeLongFormat());
			// Here the process is completed
			System.out.println("Thread -- " + Thread.currentThread().getName()
					+ "  Took off successfully,  Total Time Taken --- " + time_taken / 1000 + " seconds");

			String finish = "Took off successfully, Total time taken " + time_taken / 1000 + " seconds";

			// SwingUI.resetResourceTable();
			// SwingUI.model.setValueAt(finish, AirplaneObjectId, 1);
			SwingUI.updateGUIState(finish, "--", AirplaneObjectId);
			SwingUI.updateEndTime(new Date(), AirplaneObjectId);
			/*
			 * completed++; if(completed == Main.tracker.length) System.exit(0);
			 */
		}

	}
}