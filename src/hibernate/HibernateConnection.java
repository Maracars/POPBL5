package hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnection {

	private static SessionFactory sessionFactory;
	private final static String HBN_LOCATION = "/resources/hibernate.cfg.xml";
	
	static {
		try{
		sessionFactory = new Configuration().configure(HBN_LOCATION).buildSessionFactory();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}


	
	@Override
	protected void finalize() throws Throwable {
		sessionFactory.close();
		super.finalize();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void setSessionFactory(SessionFactory sessionFactory) {
		HibernateConnection.sessionFactory = sessionFactory;
	}

}
