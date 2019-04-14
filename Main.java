import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

/**
 * @author Shaifil
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static Airplane[] tracker;

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		HashMap<Integer, ArrayList<Airplane>> threadHashTable = new HashMap<>();
		TreeSet<Integer> timeTracker = new TreeSet<>();
		System.out.println("Enter number of threads to spawn");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(reader.readLine());
		SwingUI s = new SwingUI(n);
		tracker = new Airplane[n];
		// Thread[] threads = new Thread[n];
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			// Making hashmap
			int start = r.nextInt(5);
			if (start == 0) {
				tracker[i] = new Airplane(i);
				if (!threadHashTable.containsKey(start)) {
					timeTracker.add(0);
					ArrayList<Airplane> threadList = new ArrayList<>();
					threadList.add(tracker[i]);
					threadHashTable.put(start, threadList);
				} else {
					threadHashTable.get(start).add(tracker[i]);
				}
			} else {
				tracker[i] = new Airplane(i, start);
				{
					if (!threadHashTable.containsKey(start)) {
						timeTracker.add(start);
						ArrayList<Airplane> threadList = new ArrayList<>();
						threadList.add(tracker[i]);
						threadHashTable.put(start, threadList);
					} else {
						threadHashTable.get(start).add(tracker[i]);
					}
				}
			}
		}

		// SORTING AS PER PRIORITY
		Iterator<Integer> value = timeTracker.iterator();
		while (value.hasNext()) {
			ArrayList<Airplane> temp = threadHashTable.get(value.next());
			Collections.sort(temp);
		}

		/*
		 * value = timeTracker.iterator(); while (value.hasNext()) {
		 * 
		 * }
		 */
		value = timeTracker.iterator();
		while (value.hasNext()) {
			int time = value.next();
			ArrayList<Airplane> temp = threadHashTable.get(time);
			System.out.println("Time = " + time + ",  No of Airplanes --> "+temp.size());
			for (int counter = 0; counter < temp.size(); counter++) {
				Airplane traverse = temp.get(counter);
				System.out.println("Thread Name --> " + traverse.getName() + ",   State -->  "
						+ traverse.getCurrentStateName(traverse.getCurrentState()) + ",  Priority --> "
						+ traverse.getPriorityLevel());
			}
			System.out.println();
			System.out.println(" *********** ");
			System.out.println();
		}

		/*
		 * if (timeTracker.contains(0)) { timeTracker.pollFirst(); ArrayList<Airplane>
		 * temp = threadHashTable.get(0); for (int counter = 0; counter < temp.size();
		 * counter++) { Airplane operateOn = temp.get(counter); int toCall =
		 * operateOn.getCurrentState(); if (toCall == 0) operateOn.start(); else {
		 * 
		 * }
		 * 
		 * } }
		 */

		int deduct = 0;
		while (!timeTracker.isEmpty()) {
			int curr = timeTracker.pollFirst();
			Thread.sleep(curr * 1000 - deduct * 1000);
			deduct = curr;
			ArrayList<Airplane> temp = threadHashTable.get(curr);
			for (int counter = 0; counter < temp.size(); counter++) {
				Airplane operateOn = temp.get(counter);
				operateOn.setBeginTime();
				operateOn.start();
			}
		}
	}
}

/*
 * import java.io.*; import java.io.IOException; import
 * java.util.concurrent.BlockingQueue;
 * 
 * /**
 * 
 * @author Shaifil
 *
 * 
 * public class Main {
 * 
 *//**
	 * @param args
	 * @throws InterruptedException
	 *//*
		 * public static void main(String[] args) throws IOException,
		 * InterruptedException { // TODO Auto-generated method stub
		 * System.out.println("Enter number of threads to spawn"); BufferedReader reader
		 * = new BufferedReader(new InputStreamReader(System.in)); int n =
		 * Integer.parseInt(reader.readLine()); // Thread[] threads = new Thread[n];
		 * Execute[] tracker = new Execute[n]; int[] startTime = new int[n];
		 * System.out.println("Enter the start time of threads in seconds"); int deduct
		 * = 0; for (int i = 0; i < n; i++) startTime[i] =
		 * Integer.parseInt(reader.readLine());
		 * 
		 * for (int t = 0; t < n; ++t) { Thread.sleep((1000 * startTime[t]) - (deduct *
		 * 1000)); // System.out.println("Creating thread " + t); deduct = startTime[t];
		 * // System.out.println(deduct); tracker[t] = new Execute(t); // new Thread
		 * tracker[t].start(); } } }
		 * 
		 * class Execute extends Thread {
		 * 
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 * 
		 * 
		 * // AtomicReference<Runway> rw = new AtomicReference(); static final Runway rw
		 * = new Runway(); String[] states = { "About_to_land", "Runway", "Taxing1",
		 * "Gate", "Taxing2", "Runway", "Took_off" };
		 * 
		 * public Execute(String state) {
		 * 
		 * }
		 * 
		 * public Execute(int x) { super(Integer.toString(x)); }
		 * 
		 * @Override public void run() { // TODO Auto-generated method stub
		 * 
		 * try { // System.out.println("Current "+Thread.currentThread().getName());
		 * rw.accessRunway(); } catch (Exception ex) {
		 * System.out.println(ex.getStackTrace()); }
		 * 
		 * } }
		 */
