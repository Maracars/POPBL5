package simulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import domain.dao.Initializer;
import domain.model.Airport;

public class MainThread {
	private static final int THREAD_NUM = 3;

	private static ExecutorService threadPool;

	public static void createMainThread(String[] args) {

		threadPool = Executors.newFixedThreadPool(THREAD_NUM);

		Airport airport = Initializer.initializeExampleOnDB();
		AirportController ac = new AirportController(airport);
		FlightCreator fc = new FlightCreator(airport, ac);
		AutomaticMaintenance am = new AutomaticMaintenance();

		threadPool.submit(ac);
		threadPool.submit(fc);
		threadPool.submit(am);

	}

	public static void finishThreads() {
		threadPool.shutdownNow();
	}
	
	public static ExecutorService getThreadPool() {
		return threadPool;
	}	

}
