package hibernate;

import java.util.concurrent.Semaphore;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * The Class HibernateConnection.
 */
public class HibernateConnection {

	/** The session factory. */
	private static SessionFactory sessionFactory;

	/** The Constant HBN_LOCATION. */
	private final static String HBN_LOCATION = "/resources/hibernate.cfg.xml";

	/** The mutex. */
	private static Semaphore mutex;

	/**
	 * Start. Initializes the connection to the dvb
	 */
	public static void start() {
		try {
			sessionFactory = new Configuration().configure(HBN_LOCATION).buildSessionFactory();
			mutex = new Semaphore(1, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Stop. Stops the connection to the DB
	 */
	public static void stop() {
		try {
			sessionFactory.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	public static Session getSession() {
		Session session;
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		session = sessionFactory.openSession();

		return session;

	}

	/**
	 * Close session.
	 *
	 * @param sessionToRelease the session to release
	 */
	public static void closeSession(Session sessionToRelease) {
		sessionToRelease.close();
		mutex.release();

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
	 * @param sessionFactory
	 *            the new session factory
	 */
	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateConnection.sessionFactory = sessionFactory;
	}

}
