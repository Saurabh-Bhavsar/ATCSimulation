import java.io.*;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/**
 * @author Shaifil
 *
 */
public class Main {

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		System.out.println("Enter number of threads to spawn");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(reader.readLine());
		Thread[] threads = new Thread[n];
		int[] startTime = new int[n];
		System.out.println("Enter the start time of threads in seconds");
		int deduct = 0;
		for (int i = 0; i < n; i++)
			startTime[i] = Integer.parseInt(reader.readLine());

		for (int t = 0; t < n; ++t) {
			Thread.sleep((1000 * startTime[t]) - (deduct * 1000));
			System.out.println("Creating thread " + t);
			deduct = startTime[t];
			System.out.println(deduct);
			threads[t] = new Thread(new Execute(), Integer.toString(t));
			// new Thread
			threads[t].start();
		}
	}
}

class Execute implements Runnable {
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */

	// AtomicReference<Runway> rw = new AtomicReference();
	static final Runway rw = new Runway();
	String[] states = { "About_to_land", "Runway", "Taxing1", "Gate", "Taxing2", "Runway", "Took_off" };

	public Execute(String state) {

	}

	public Execute() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			System.out.println("Current "+Thread.currentThread().getName());
			rw.accessRunway();
		} catch (Exception ex) {
			System.out.println(ex.getStackTrace());
		}

	}
}

/*
 * class Reminder { Timer timer;
 * 
 * Reminder(int seconds) { timer = new Timer(); timer.schedule(new RemindTask(),
 * seconds * 1000); }
 * 
 * class RemindTask extends TimerTask { public void run() {
 * System.out.println("Time's up!"); timer.cancel(); // Terminate the timer
 * thread } } }
 */