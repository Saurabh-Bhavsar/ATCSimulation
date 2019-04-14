import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class Gate1 {

	public static final ReentrantLock lockG1 = new ReentrantLock();
	static BlockingQueue<Gate1> Gate1Queue = new BlockingQueue<Gate1>() {
		@Override
		public boolean add(Gate1 o) {
			return false;
		}

		@Override
		public boolean offer(Gate1 o) {
			return false;
		}

		@Override
		public void put(Gate1 o) throws InterruptedException {

		}

		@Override
		public boolean offer(Gate1 o, long timeout, TimeUnit unit) throws InterruptedException {
			return false;
		}

		@Override
		public Gate1 take() throws InterruptedException {
			return null;
		}

		@Override
		public Gate1 poll(long timeout, TimeUnit unit) throws InterruptedException {
			return null;
		}

		@Override
		public int remainingCapacity() {
			return 0;
		}

		@Override
		public boolean remove(Object o) {
			return false;
		}

		@Override
		public boolean contains(Object o) {
			return false;
		}

		@Override
		public int drainTo(Collection c) {
			return 0;
		}

		@Override
		public int drainTo(Collection c, int maxElements) {
			return 0;
		}

		@Override
		public Gate1 remove() {
			return null;
		}

		@Override
		public Gate1 poll() {
			return null;
		}

		@Override
		public Gate1 element() {
			return null;
		}

		@Override
		public Gate1 peek() {
			return null;
		}

		@Override
		public int size() {
			return 0;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public Iterator iterator() {
			return null;
		}

		@Override
		public Object[] toArray() {
			return new Object[0];
		}

		@Override
		public Object[] toArray(Object[] a) {
			return new Object[0];
		}

		@Override
		public boolean containsAll(Collection c) {
			return false;
		}

		@Override
		public boolean addAll(Collection c) {
			return false;
		}

		@Override
		public boolean removeAll(Collection c) {
			return false;
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			return false;
		}

		@Override
		public void clear() {

		}
	};

	public void accessGate1(int time) throws InterruptedException {
		int AirplaneObjectId = Integer.parseInt(Thread.currentThread().getName());
		Airplane workOn = Main.tracker[AirplaneObjectId];
		lockG1.lock();
		workOn.setCurrentState(3);
		SwingUI.model.setValueAt(workOn.getCurrentStateName(workOn.getCurrentState()), AirplaneObjectId, 1);
		// System.out.println("In GATE RESOURCE 2");
		try {
			Thread.sleep(time * 1000);
		} finally {
			System.out.println(" IN GATE RESOURCE 1,   Thread -- " + workOn.getName() + ",  State -- "
					+ workOn.getCurrentStateName(workOn.getCurrentState()));
			lockG1.unlock();
			workOn.setCurrentState(4);
			SwingUI.model.setValueAt(workOn.getCurrentStateName(workOn.getCurrentState()), AirplaneObjectId, 1);
			Airplane.rw.accessRunway();
		}
	}
}