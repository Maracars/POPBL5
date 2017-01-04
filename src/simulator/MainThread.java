package simulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import domain.dao.DAOAirport;
import domain.dao.Initializer;
import domain.model.Airport;

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
	 *
	 */
	public static void createMainThread(String[] args) {

		threadPool = Executors.newFixedThreadPool(THREAD_NUM);

		
		Airport airport = initialize();
		AirportController ac = new AirportController(airport);
		FlightCreator fc = new FlightCreator(airport, ac);
		AutomaticMaintenance am = new AutomaticMaintenance();

		threadPool.submit(ac);
		threadPool.submit(fc);
		threadPool.submit(am);

	}

	/**
	 * Finish threads.
	 */
	public static void finishThreads() {
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

	
	
	private static Airport initialize() {
		Airport myAirport = null;
		myAirport = DAOAirport.getLocaleAirport();
		
		if(myAirport == null) {
			myAirport = Initializer.initializeExampleOnDB();
		}
		
		return myAirport;
	}

}
