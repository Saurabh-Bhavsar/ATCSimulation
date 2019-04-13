import java.util.*;

public class Airplane extends Thread {

    int startTime;
    String state; // ["Airborne","Landing","Taxiing","Gate","TakingOff"]
    String priorityLevel;
    String[] priorityLevels= {"International","Domestic"};
    boolean emergency=false;
    String[] states={"Airborne","Landing","TaxiingToGate","AtGate","TaxiingToRunway","TakingOff","Airborne"};
    public Airplane(int start)
    {
        this.state="Airborne";
        this.startTime=start;
        priorityLevel=priorityLevels[(int)Math.round(Math.random()*10)%2];
        if(Math.round(Math.random()*10)%2==1) this.emergency=true;

    }
    public Airplane()
    {
        this.startTime=0;
        Random r = new Random();
        switch(r.nextInt(2))
        {
            case 0: this.state="Airborne";break;

            case 1: this.state="Taxiing";break;

            default: this.state="Airborne";break;
        }
        this.priorityLevel=priorityLevels[(int)Math.round(Math.random()*10)%2];
        if(Math.round(Math.random()*10)%2==1) this.emergency=true;
    }
}
