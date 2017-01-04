package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

// TODO: Auto-generated Javadoc
/**
 * The Class HibernateConnection.
 */
public class HibernateConnection {

	/** The session factory. */
	private static SessionFactory sessionFactory;
	
	/** The Constant HBN_LOCATION. */
	private final static String HBN_LOCATION = "/resources/hibernate.cfg.xml";

	/**
	 * Start.
	 */
	public static void start() {
		try {
			sessionFactory = new Configuration().configure(HBN_LOCATION).buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Stop.
	 */
	public static void stop() {
		try {
			sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the session factory.
	 *
	 * @return the session factory
	 */
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	/**
	 * Sets the session factory.
	 *
	 * @param sessionFactory the new session factory
	 */
	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateConnection.sessionFactory = sessionFactory;
	}

}
