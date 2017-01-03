package initialization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class HibernateInit.
 */
public class HibernateInit implements ServletContextListener {

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateConnection.stop();
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateConnection.start();
	}

}
