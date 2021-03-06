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

	/** The Constant STRING_ORDER_BY. */
	private static final String STRING_ORDER_BY = " order by rand()";

	/** The Constant QUERY_ROUTE. */
	private static final String QUERY_ROUTE = "from Route as r ";

	/** The Constant PARAMETER_AIRPORT_ID. */
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	
	/** The Constant PARAMETER_AIRLINE_ID. */
	private static final String PARAMETER_AIRLINE_ID = "airlineId";

	/** The Constant QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID. */
	private static final String QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID = QUERY_ROUTE

			+ "where r.arrivalTerminal.airport.id = :" + PARAMETER_AIRPORT_ID + STRING_ORDER_BY;

	/** The Constant QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID. */
	private static final String QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID = QUERY_ROUTE

			+ "where r.departureTerminal.airport.id = :" + PARAMETER_AIRPORT_ID + STRING_ORDER_BY;
	
	/** The Constant QUERY_ROUTES_FROM_AIRLINE. */
	private static final String QUERY_ROUTES_FROM_AIRLINE = "select r from Flight as f join f.route as r where f.plane.airline.id = :"
			+ PARAMETER_AIRLINE_ID;

	/** The session. */
	private static Session session;

	/**
	 * Gets the random arrival route from airport.
	 *
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

	/**
	 * Gets the route list from airline.
	 *
	 * @param airlineId the airline id
	 * @return the route list from airline
	 */
	@SuppressWarnings("unchecked")
	public static List<Route> getRouteListFromAirline(int airlineId) {
		List<Route> routeList = null;
		session = HibernateConnection.getSessionFactory().openSession();
		try {
			Query query = session.createQuery(QUERY_ROUTES_FROM_AIRLINE);
			query.setParameter(PARAMETER_AIRLINE_ID, airlineId);
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return routeList;
	}

}
