import java.text.SimpleDateFormat;
import java.util.*;

public class Airplane extends Thread implements Comparable<Airplane> {

	// int startTime;
	public static final Runway rw = new Runway();
	static final Gate g = new Gate();
	static final Gate1 g1 = new Gate1();
	private Date beginTime;
	private int state; // ["Airborne","Landing","Taxiing","Gate","TakingOff"]
	private int priorityLevel;
	String[] priorityLevels = { "International", "Domestic" };
	boolean emergency = false;
	String[] states = { "AirborneL", "Landing", "TaxiingToGate", "AtGate", "TaxiingToRunway", "TakingOff",
			"AirborneT" };

	Random r = new Random();

	public Airplane(int name, int start) {
		super(Integer.toString(name));
		this.state = 0;
		// this.startTime = start;
		// priorityLevel = priorityLevels[(int) Math.round(Math.random() * 10) % 2];
		priorityLevel = r.nextInt(10);
		/*
		 * if (Math.round(Math.random() * 10) % 2 == 1) this.emergency = true;
		 */
	}

	public Airplane(int name) {
		// this.startTime = 0;
		super(Integer.toString(name));
		switch (r.nextInt(3)) {
		case 0:
			this.state = 0;
			break;

		case 1:
			this.state = 2;
			break;

		default:
			this.state = 0;
			break;
		}
		priorityLevel = r.nextInt(5);
		// this.priorityLevel = priorityLevels[(int) Math.round(Math.random() * 10) %
		// 2];
		/*
		 * if (Math.round(Math.random() * 10) % 2 == 1) this.emergency = true;
		 */
	}

	public void setBeginTime() {
		beginTime = new Date();
	}

	public long getBeginTime() {
		return beginTime.getTime();
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}

	public int getCurrentState() {
		return state;
	}

	public void setCurrentState(int s) {
		state = s % 7;
	}

	public String getCurrentStateName(int i) {
		return states[i];
	}

	@Override
	public void run() {
		int AirplaneObjectId = Integer.parseInt(Thread.currentThread().getName());
		Airplane workOn = Main.tracker[AirplaneObjectId];
		if (workOn.state == 0) {
			try {
				rw.accessRunway();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				if (!Gate.lockG.isLocked())
					g.accessGate(new Random().nextInt(5));
				else if (!Gate1.lockG1.isLocked())
					g1.accessGate1(new Random().nextInt(5));
				else {
					System.out.println("Else");
					int curr_count_G = Gate.lockG.getQueueLength();
					int curr_count_G1 = Gate1.lockG1.getQueueLength();

					if (curr_count_G > curr_count_G1)
						g1.accessGate1(new Random().nextInt(5));
					else
						g.accessGate(new Random().nextInt(5));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Airplane o) {
		// TODO Auto-generated method stub
		return o.getPriorityLevel() - this.getPriorityLevel();
	}
}