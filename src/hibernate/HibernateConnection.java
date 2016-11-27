package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnection {

	private static SessionFactory sessionFactory;
	private static Session session;
	private final static String HBN_LOCATION = "/resources/hibernate.cfg.xml";

	public static void before() {

		sessionFactory = new Configuration().configure(HBN_LOCATION).buildSessionFactory();
		session = sessionFactory.openSession();

	}

	public static void after() {

		session.close();
		sessionFactory.close();

	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateConnection.sessionFactory = sessionFactory;
	}

	public static Session getSession() {
		return session;
	}

	public static void setSession(Session session) {
		HibernateConnection.session = session;
	}

}
