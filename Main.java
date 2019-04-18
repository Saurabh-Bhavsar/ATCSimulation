import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	// Array to hold the objects of Airplane class
	public static Airplane[] tracker;

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		// Map to maintain objects which start at the same time
		HashMap<Integer, ArrayList<Airplane>> threadHashTable = new HashMap<>();
		// Unique set to maintain starttime which will be used later
		TreeSet<Integer> timeTracker = new TreeSet<>();
		System.out.println("Enter number of threads to spawn");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(reader.readLine());
		// SwingUI s =
		new SwingUI(n);
		tracker = new Airplane[n];
		// Thread[] threads = new Thread[n];
		Random r = new Random();
		for (int i = 0; i < n; i++) {
			// Making hashmap
			// this will help us choose spawn time of thread between 0 and 6 seconds
			int start = r.nextInt(7);
			// in the if else condition we create our hashmap tracker
			if (start == 0) {
				// this if condition is for threads which have timing 0, for them we either
				// start them at Gate resource or Runway resource
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
				// else condition is for threads which get non zero start time
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

		// SORTING AS PER PRIORITY, we want the threads which start at a particular time
		// to be sorted as per priority in descending order
		Iterator<Integer> value = timeTracker.iterator();
		while (value.hasNext()) {
			ArrayList<Airplane> temp = threadHashTable.get(value.next());
			Collections.sort(temp);
		}

		/*
		 * value = timeTracker.iterator(); while (value.hasNext()) { int time =
		 * value.next(); ArrayList<Airplane> temp = threadHashTable.get(time);
		 * System.out.println("Time = " + time + ",  No of Airplanes --> " +
		 * temp.size()); for (int counter = 0; counter < temp.size(); counter++) {
		 * Airplane traverse = temp.get(counter); System.out.println("Thread Name --> "
		 * + traverse.getName() + ",   State -->  " +
		 * traverse.getCurrentStateName(traverse.getCurrentState()) + ",  Priority --> "
		 * + traverse.getPriorityLevel()); } System.out.println();
		 * System.out.println(" *********** "); System.out.println(); }
		 */
		int deduct = 0;
		// this is the while loop where we actually spawn the threads, threads which
		// start at 0 either goto Runway or Gate others goto Runway
		while (!timeTracker.isEmpty()) {
			int curr = timeTracker.pollFirst();
			Thread.sleep(curr * 1000 - deduct * 1000);
			deduct = curr;
			ArrayList<Airplane> temp = threadHashTable.get(curr);
			for (int counter = 0; counter < temp.size(); counter++) {
				Airplane operateOn = temp.get(counter);
				operateOn.setBeginTime();
				DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

				SwingUI.updateStartTimeAndPriority(dateFormat.format(operateOn.getBeginTimeDateFormat()),
						operateOn.getPriorityLevel(), Integer.parseInt(operateOn.getName()));
				operateOn.start();
			}
		}
	}
}
