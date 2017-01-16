package simulator;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import domain.dao.DAOAirport;
import domain.dao.Initializer;
import domain.model.Airport;
import domain.model.Lane;
import domain.model.Path;

// TODO: Auto-generated Javadoc
/**
 * The Class MainThread.
 */
public class MainThread {
	
	/** The Constant THREAD_NUM. */
	private static final int THREAD_NUM = 3;

	/** The thread pool. */
	private static ExecutorService threadPool;

	/**
	 * Creates the main thread.
	 * @param laneList 
	 * @param pathList 
	 *
	 * @param args the args
	 */
	public static void initSimulator(Airport airport, List<Path> pathList) {

		threadPool = Executors.newFixedThreadPool(THREAD_NUM);

		AirportController ac = new AirportController(airport,pathList);
		FlightCreator fc = new FlightCreator(airport, ac);
		AutomaticMaintenance am = new AutomaticMaintenance();
		System.out.println("Hasi de");

		threadPool.submit(ac);
		threadPool.submit(fc);
		threadPool.submit(am);

	}

	/**
	 * Finish threads.
	 */
	public static void finishSimulator() {
		threadPool.shutdownNow();
	}
	
	/**
	 * Gets the thread pool.
	 *
	 * @return the thread pool
	 */
	public static ExecutorService getThreadPool() {
		return threadPool;
	}	

	
	
	/**
	 * Initialize.
	 *
	 * @return the airport
	 */
	public static Airport initializeExample() {
		Airport myAirport = null;
		myAirport = DAOAirport.getLocaleAirport();
		
		if(myAirport == null) {
			myAirport = Initializer.initializeExampleOnDB();
		}
		
		return myAirport;
	}

}
