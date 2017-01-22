package initialization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import hibernate.HibernateConnection;

/**
 * The Class HibernateInit. Class that initializes Hibernate on server start and
 * disposes it on close
 */
public class HibernateInit implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateConnection.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateConnection.start();
	}

}
