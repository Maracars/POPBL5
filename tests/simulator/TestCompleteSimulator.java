package simulator;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;

import org.junit.Test;

public class TestCompleteSimulator {

	@SuppressWarnings("static-access")
	@Test
	public void finishAllThreads() {
		MainThread main = new MainThread();
		try {
			main.createMainThread(null);
			Thread.sleep(60000);
			main.finishThreads();
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ExecutorService threadPool = main.getThreadPool();
		assertTrue("aa",threadPool.isTerminated());
	}
}
