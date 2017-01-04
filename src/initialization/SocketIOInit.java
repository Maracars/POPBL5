package initialization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import notification.Notification;

public class SocketIOInit implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			Notification.stop();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			Notification.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

}
