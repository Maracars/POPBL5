package simulator;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;

import org.junit.Before;
import org.junit.Test;

import domain.dao.HibernateGeneric;
import domain.model.Flight;
import domain.model.Plane;

public class TestCompleteSimulator {
	
	private static final int TIME_TO_FINISH_THREADS = 20000;
	private static final int TIME_TO_EXECUTE_ALL = 60000;
	private static final String ERROR_FINISH_THREADS = "Error finishing all threads";

	@Before
	public void initialize() {
		HibernateGeneric.deleteAllObjects(new Flight());
		HibernateGeneric.deleteAllObjects(new Plane());
	}

	@SuppressWarnings("static-access")
	@Test
	public void finishAllThreads() {
		MainThread main = new MainThread();
		try {
			main.createMainThread(null);
			Thread.sleep(TIME_TO_EXECUTE_ALL);
			main.finishThreads();
			Thread.sleep(TIME_TO_FINISH_THREADS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ExecutorService threadPool = main.getThreadPool();
		assertTrue(ERROR_FINISH_THREADS,threadPool.isTerminated());
	}
}
