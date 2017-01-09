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
	
	private static AirportController ac;
	private static FlightCreator fc;
	private static AutomaticMaintenance am;

	/**
	 * Creates the main thread.
	 *
	 * @param args the args
	 */
	public static void createMainThread(String[] args) {

		threadPool = Executors.newFixedThreadPool(THREAD_NUM);

		
		Airport airport = initialize();
		ac = new AirportController(airport);
		fc = new FlightCreator(airport, ac);
		am = new AutomaticMaintenance();

		ac.run();
		fc.run();
		am.run();

	}

	/**
	 * Finish threads.
	 */
	public static void finishThreads() {
		ac.interrupt();
		fc.interrupt();
		am.interrupt();
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
	private static Airport initialize() {
		Airport myAirport = null;
		myAirport = DAOAirport.getLocaleAirport();
		
		if(myAirport == null) {
			myAirport = Initializer.initializeExampleOnDB();
		}
		
		return myAirport;
	}

}
