package initialization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import hibernate.HibernateConnection;

public class HibernateInit implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		HibernateConnection.stop();
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		HibernateConnection.start();		
	}

}
