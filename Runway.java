import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
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
		//lock.
		System.out.println(
				"Started execution --> " + Thread.currentThread().getName() + "  Time -- " + dateFormat.format(date)); // 2016/11/16
		/*
		 * while (isLocked) // 12:08:43 {
		 * System.out.println(Thread.currentThread().getName()); }
		 */
		try {
			Thread.sleep(1000);
		} finally {
			if (!Gate.lockG.isLocked()) 
			{
				System.out.println("In if condition");
				lock.unlock();
				g.accessGate(4);
			}
			else if(!Gate1.lockG1.isLocked())
			{
				System.out.println("Else if");
				lock.unlock();
				g1.accessGate1(4);
			}
			else
			{
				System.out.println("Else");
				int curr_count_G = Gate.lockG.getQueueLength();
				int curr_count_G1 = Gate1.lockG1.getQueueLength();

				
				if (curr_count_G > curr_count_G1)
				{
					lock.unlock();
					g1.accessGate1(4);
				}
				else if (curr_count_G == curr_count_G1)
				{
					lock.unlock();
					g.accessGate(4);
				}
				else
				{
					lock.unlock();
					g.accessGate(4);
				}
			}
					
				/*System.out.println("Hello");
				if (!Gate1.lockG1.isLocked())
					g1.accessGate1(4);
				else {
					int curr_count_G = Gate.lockG.getQueueLength();
					int curr_count_G1 = Gate1.lockG1.getQueueLength();

					if (curr_count_G > curr_count_G1)
						g1.accessGate1(4);
					else if (curr_count_G == curr_count_G1)
						g.accessGate(4);
					else
						g.accessGate(4);

				}
			} else  {
				System.out.println("WORLD");
				if (!Gate.lockG.isLocked())
					g.accessGate(4);
				else {
					int curr_count_G = Gate.lockG.getQueueLength();
					int curr_count_G1 = Gate1.lockG1.getQueueLength();

					if (curr_count_G > curr_count_G1)
						g1.accessGate1(4);
					else if (curr_count_G == curr_count_G1)
						g.accessGate(4);
					else
						g.accessGate(4);

				}
			} */
			//lock.unlock();
			//isLocked = false;
		}
		date = new Date();
		System.out.println(
				"Completed execution - " + Thread.currentThread().getName() + "  Time --" + dateFormat.format(date));
	}
}