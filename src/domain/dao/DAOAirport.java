package domain.dao;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Airport;
import hibernate.HibernateConnection;

public class DAOAirport {
	
	private static final String QUERY_LOCALE_AIRPORT = "from Airport as a "
			+ "where a.locale is true";
	private static Session session;
	
	public static Airport getLocaleAirport() {
		Airport localeAirport = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_LOCALE_AIRPORT);
			if (query.getResultList().size() > 0) {
				localeAirport = (Airport) query.getResultList().get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return localeAirport;
	}

}
