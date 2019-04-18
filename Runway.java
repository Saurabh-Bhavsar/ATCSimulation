import java.util.Date;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

public class Runway {

	public static final Gate g = new Gate();
	public static final Gate1 g1 = new Gate1();
	public static final ReentrantLock lock = new ReentrantLock();

	public void accessRunway() throws InterruptedException {
		int AirplaneObjectId = Integer.parseInt(Thread.currentThread().getName());
		Airplane workOn = Main.tracker[AirplaneObjectId];
		
		if (workOn.getCurrentState() == Constants.AIRBORNE_LANDING) {
			SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
					workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
			lock.lock();
			workOn.setCurrentState(Constants.LANDING);
			SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
					workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
			SwingUI.updateResourceUsedBy("R", AirplaneObjectId);

			try {
				Thread.sleep(3000);
			} finally {
				if (!Gate.lockG.isLocked()) {
					lock.unlock();
					g.accessGate(new Random().nextInt(Constants.START_TIME_RANGE));
				} else if (!Gate1.lockG1.isLocked()) {
					lock.unlock();
					g1.accessGate1(new Random().nextInt(Constants.START_TIME_RANGE));
				} else {
					workOn.setCurrentState(Constants.TAXIING_TO_GATE);
					SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
							workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
					int curr_count_G = Gate.lockG.getQueueLength();
					int curr_count_G1 = Gate1.lockG1.getQueueLength();

					if (curr_count_G > curr_count_G1) {
						lock.unlock();
						g1.accessGate1(new Random().nextInt(6)+1);
					} else {
						lock.unlock();
						SwingUI.resetResourceTable(0);
						g.accessGate(new Random().nextInt(6)+1);
					}
				}

			}
		} else {
			lock.lock();
			workOn.setCurrentState(Constants.TAKEOFF);
			SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
					workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
			SwingUI.updateResourceUsedBy("R", AirplaneObjectId);
			try {
				Thread.sleep(3000);
			} finally {
				lock.unlock();
				SwingUI.resetResourceTable(0);
				workOn.setCurrentState(Constants.AIRBORNE_TAKEOFF);
			}
			long time_taken = (new Date().getTime() - workOn.getBeginTimeLongFormat());

			System.out.println("Thread -- " + Thread.currentThread().getName()
					+ "  Took off successfully,  Total Time Taken --- " + time_taken / 1000 + " seconds");

			String finish = "Took off successfully, Total time taken " + time_taken / 1000 + " seconds";
			SwingUI.updateGUIState(finish, "--", AirplaneObjectId);
			SwingUI.updateEndTime(new Date(), AirplaneObjectId);
		}

	}
}