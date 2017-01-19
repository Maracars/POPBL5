package domain.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Route;
import hibernate.HibernateConnection;

/**
 * The Class DAORoute.
 */
public class DAORoute {

	/** The Constant QUERY_ROUTE. */
	private static final String QUERY_ROUTE = "from Route as r ";

	/** The Constant PARAMETER_AIRPORT_ID. */
	private static final String PARAMETER_AIRPORT_ID = "airportId";

	/** The Constant QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID. */
	private static final String QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID = QUERY_ROUTE
			+ "where r.arrivalTerminal.airport.id = :" + PARAMETER_AIRPORT_ID + " order by rand()";

	/** The Constant QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID. */
	private static final String QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID = QUERY_ROUTE
			+ "where r.departureTerminal.airport.id = :" + PARAMETER_AIRPORT_ID + " order by rand()";

	/** The session. */
	private static Session session;

	/**
	 * Gets the random arrival route from airport.
	 *
	 * @param airportId
	 *            the airport id
	 * @return the random arrival route from airport
	 */
	@SuppressWarnings("unchecked")
	public static List<Route> getRandomArrivalRouteFromAirport(int airportId) {

		List<Route> routeList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return routeList;
	}

	/**
	 * Select departure route from airport.
	 *
	 * @param airportId
	 *            the airport id
	 * @return the route
	 */
	@SuppressWarnings("unchecked")
	public static Route selectDepartureRouteFromAirport(int airportId) {

		List<Route> routeList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return routeList.get(0);
	}

}
