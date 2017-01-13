package domain.dao;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Airport;
import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOAirport.
 */
public class DAOAirport {
	
	/** The Constant QUERY_LOCALE_AIRPORT. */
	private static final String QUERY_LOCALE_AIRPORT = "from Airport as a "
			+ "where a.locale is true";
	
	/** The session. */
	private static Session session;
	
	/**
	 * Gets the locale airport.
	 * <p>Gets the local airport</p>
	 * @return the locale airport
	 */
	public static Airport getLocaleAirport() {
		Airport localeAirport = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_LOCALE_AIRPORT);
			if (query.getResultList().size() > 0) {
				localeAirport = (Airport) query.getResultList().get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}
		return localeAirport;
	}

}
