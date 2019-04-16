import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Gate {

	public static final ReentrantLock lockG = new ReentrantLock();

	public void accessGate(int time) throws InterruptedException {
		int AirplaneObjectId = Integer.parseInt(Thread.currentThread().getName());
		Airplane workOn = Main.tracker[AirplaneObjectId];
		lockG.lock();
		workOn.setCurrentState(3);
		// SwingUI.model.setValueAt(workOn.getCurrentStateName(workOn.getCurrentState()),
		// AirplaneObjectId, 1);
		SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
				workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
		// System.out.println("In GATE RESOURCE 1");
		SwingUI.updateResourceUsedBy("G1", AirplaneObjectId);
		try {
			Thread.sleep(time * 1000);
		} finally {
			/*
			 * System.out.println(" IN GATE RESOURCE 1,   Thread -- " + workOn.getName() +
			 * ",  State -- " + workOn.getCurrentStateName(workOn.getCurrentState()));
			 */
			lockG.unlock();
			SwingUI.resetResourceTable(1);
			workOn.setCurrentState(4);
			// SwingUI.model.setValueAt(workOn.getCurrentStateName(workOn.getCurrentState()),
			// AirplaneObjectId, 1);
			SwingUI.updateGUIState(workOn.getCurrentStateName(workOn.getCurrentState()),
					workOn.getCurrentStateName(workOn.getCurrentState() + 1), AirplaneObjectId);
			Airplane.rw.accessRunway();
		}
	}
}