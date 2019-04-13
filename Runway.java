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

	public static final Gate g = new Gate();
	public static final Gate1 g1 = new Gate1();
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
		int AirplaneObjectId = Integer.parseInt(Thread.currentThread().getName());
		Airplane workOn = Main.tracker[AirplaneObjectId];
		if (workOn.getCurrentState() == 0) {
			workOn.setCurrentState(1);
			lock.lock();

		/*	DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
			Date date = new Date();
			// lock.
			System.out.println("Started execution --> " + Thread.currentThread().getName() + "  Time -- "
					+ dateFormat.format(date)); // 2016/11/16 */
			try {
				Thread.sleep(3000);
			} finally {
				if (!Gate.lockG.isLocked()) {
					// System.out.println("In if condition");
					lock.unlock();
					g.accessGate(new Random().nextInt(5));
				} else if (!Gate1.lockG1.isLocked()) {
					// System.out.println("Else if");
					lock.unlock();
					g1.accessGate1(new Random().nextInt(5));
				} else {
					System.out.println("Else");
					int curr_count_G = Gate.lockG.getQueueLength();
					int curr_count_G1 = Gate1.lockG1.getQueueLength();

					if (curr_count_G > curr_count_G1) {
						lock.unlock();
						g1.accessGate1(new Random().nextInt(5));
					} else {
						lock.unlock();
						g.accessGate(new Random().nextInt(5));
					}
				}

			}
			System.out.println("Completed -- "+Thread.currentThread().getName());
		} else {
			/* date = new Date();
			System.out.println("Completed execution - " + Thread.currentThread().getName() + "  Time --"
					+ dateFormat.format(date)); */
			lock.lock();
			try
			{
				Thread.sleep(3000);
			}
			finally
			{
				lock.unlock();
			}
			
			System.out.println("Completed -- "+Thread.currentThread().getName());
		}

	}
}