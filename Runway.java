import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Runway {

	public static final ReentrantLock lock = new ReentrantLock();
	static BlockingQueue<Runway> RunwayQueue = new BlockingQueue<Runway>() {
		@Override
		public boolean add(Runway o) {
			return false;
		}

		@Override
		public boolean offer(Runway o) {
			return false;
		}

		@Override
		public void put(Runway o) throws InterruptedException {

		}

		@Override
		public boolean offer(Runway o, long timeout, TimeUnit unit) throws InterruptedException {
			return false;
		}

		@Override
		public Runway take() throws InterruptedException {
			return null;
		}

		@Override
		public Runway poll(long timeout, TimeUnit unit) throws InterruptedException {
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
		public Runway remove() {
			return null;
		}

		@Override
		public Runway poll() {
			return null;
		}

		@Override
		public Runway element() {
			return null;
		}

		@Override
		public Runway peek() {
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

	public void accessRunway() throws InterruptedException {
		boolean isLocked = true;

		/*
		 * while(lock.isLocked()) {
		 * System.out.println("Thread "+Thread.currentThread().getName()
		 * +" is waiting for the lock"); }
		 */
		// System.out.println("WAITING --> "+lock.getQueueLength());
		// lock.
		// lock.tr
		lock.lock();
		System.out.println();
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		System.out.println(
				"Started execution --> " + Thread.currentThread().getName() + "  Time -- " + dateFormat.format(date)); // 2016/11/16
		/*
		 * while (isLocked) // 12:08:43 {
		 * System.out.println(Thread.currentThread().getName()); }
		 */
		try {
			Thread.sleep(10000);
		} finally {
			lock.unlock();
			isLocked = false;
		}
		date = new Date();
		System.out.println(
				"Completed execution - " + Thread.currentThread().getName() + "  Time --" + dateFormat.format(date));
	}
}