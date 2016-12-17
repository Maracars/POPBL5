package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnection {

	private static SessionFactory sessionFactory;
	private final static String HBN_LOCATION = "/resources/hibernate.cfg.xml";

	public static void start() {
		try {
			sessionFactory = new Configuration().configure(HBN_LOCATION).buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void stop() {
		try{
			sessionFactory.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateConnection.sessionFactory = sessionFactory;
	}

}
