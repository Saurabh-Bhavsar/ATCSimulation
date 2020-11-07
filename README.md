# ATC Simulation
Simulation of an Air Traffic Control (ATC) System using Concurrent Programming concepts.

Team:

Saurabh Bhavsar (8553-7314) - Saurabh-bhavsar </br>
Shaifil Maknojia (7805-9466) - saifilmaknojia </br>
Shashank Mayekar (5005-9142) - shashanksm94

Contribution:

Saurabh Bhavsar - Concept, system design, Main class, Poster <br/>
Shaifil Maknojia - Airplane Resource, Gate resources, Multi Threading, Priority sorting </br>
Shashank Mayekar - User interface, Concurrent updates </br>
Git Hub repo: https://github.com/Saurabh-Bhavsar/ATCSimulation/ </br>
Final commit is on "saifilmaknojia-patch-1" branch

Description:

The goal of this project is to implement a simulation of an ATC (Air Traffic Controller) system to schedule incoming and outgoing aircrafts on a small-size airport.
The airplanes are represented as Threads and resources to be utilized, viz. A Runway and Two Gates are represented by Java Classes.
Airplanes request to access resources as follows:
Runway is locked during Take-off and Landing.
Gates are locked during boarding/de-boarding. 
Airplanes (Threads) are spawned randomly and the number of threads to be spawned is taken from the user.
Priorities are assigned to the threads in case of same start-time.
Threads go into waiting state when the runway or neither of the gates are lock-ready using intrinsic lock queues.

Instructions to run:

Extract the zip file and perform the following:
1. Go to the folder
2. If make utility is pre-installed, then run
    > make
    // This will compile all java files
	// run the file using
    > java Main
2.1 If make is not installed, compile all files using java *.java
	// run the file using
	> java Main
3. You will be prompted to input the number of threads.
4. Simulation begins with the UI shown

