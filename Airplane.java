import java.text.SimpleDateFormat;
import java.util.*;

public class Airplane extends Thread implements Comparable<Airplane> {
	// This is the Airplane class, we create individual thread for each Airplane
	// object
	public static final Runway rw = new Runway();
	// public Runway rw = new Runway();
	static final Gate1 g = new Gate1();
	// atomic objects for accessing various resources
	static final Gate2 g1 = new Gate2();
	private Date beginTime;
	private int state; // This is the current state of the airplane, represented by int which
						// corresponds to index of one of the following
	// ["AirborneLanding", "Landing", "TaxiingToGate", "AtGate", "TaxiingToRunway",
	// "TakingOff", "AirborneTookOff"]
	private int priorityLevel; // Priority of the Airplane
	// String[] priorityLevels = { "International", "Domestic" };
	// boolean emergency = false;
	String[] states = { "AirborneLanding", "Landing", "TaxiingToGate", "AtGate", "TaxiingToRunway", "TakingOff",
			"AirborneTookOff" };

	Random r = new Random();

	public Airplane(int name, int start) {
		// this constructor is for airplanes which have starttime > 0
		super(Integer.toString(name));
		this.state = 0;
		// this.startTime = start;
		// priorityLevel = priorityLevels[(int) Math.round(Math.random() * 10) % 2];
		priorityLevel = r.nextInt(10) + 1;
		/*
		 * if (Math.round(Math.random() * 10) % 2 == 1) this.emergency = true;
		 */
	}

	public Airplane(int name) {
		// this constructor is for airplanes which have starttime = 0
		// this.startTime = 0;
		super(Integer.toString(name));
		// switch case to give starting position for these threads, start position could
		// be Runway or Gate
		switch (r.nextInt(3)) {
		case 0:
			this.state = 0;
			break;

		case 1:
			this.state = 2;
			break;

		default:
			this.state = 2;
			break;
		}
		priorityLevel = r.nextInt(10) + 1;
	}

// Getters and Setters
	public void setBeginTime() {
		beginTime = new Date();
	}

	public long getBeginTimeLongFormat() {
		return beginTime.getTime();
	}

	public Date getBeginTimeDateFormat() {
		return beginTime;
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
		// Depending on the state, we either call the runway resource or the gate
		// resource
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
				if (!Gate1.lockG.isLocked())
					g.accessGate(new Random().nextInt(6) + 1);
				else if (!Gate2.lockG1.isLocked())
					g1.accessGate1(new Random().nextInt(6) + 1);
				else {
					// System.out.println("Else");
					int curr_count_G = Gate1.lockG.getQueueLength();
					int curr_count_G1 = Gate2.lockG1.getQueueLength();

					if (curr_count_G > curr_count_G1)
						g1.accessGate1(new Random().nextInt(6) + 1);
					else
						g.accessGate(new Random().nextInt(6) + 1);
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
	// Overriding so that priority is sorted in descending order
	@Override
	public int compareTo(Airplane o) {
		// TODO Auto-generated method stub
		return o.getPriorityLevel() - this.getPriorityLevel();
	}
}