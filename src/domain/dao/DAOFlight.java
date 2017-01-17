package domain.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import action.airline.RouteStatsAction;
import action.airline.RouteStatsAction.FlightView;
import domain.model.Flight;
import domain.model.users.Passenger;
import hibernate.HibernateConnection;

/**
 * The Class DAOFlight.
 */
public class DAOFlight {

	private static final String UNCHECKED = "unchecked";

	/** The Constant PARAMETER_AIRLINE_USERNAME. */
	private static final String PARAMETER_AIRLINE_USERNAME = "airlineUsername";

	/** The Constant LOAD_AIRLINE_FLIGHTS. */
	private static final String LOAD_AIRLINE_FLIGHTS = "from Flight as f where f.plane.airline.username = :"
			+ PARAMETER_AIRLINE_USERNAME;

	private static final String LOAD_TABLE_FLIGHTS = "from Flight as f order by f.";

	private static final String LOAD_AIRLINE_ROUTE_FLIGHTS = "select count(*), f.route.id from Flight as f where f.plane.airline.username =:"
			+ PARAMETER_AIRLINE_USERNAME + " group by f.route";

	private static final String LOAD_WEEK_FLIGHTS = "from Flight as f where DATE(f.expectedArrivalDate) = "
			+ "current_date or DATE(f.expectedDepartureDate) = current_date";

	private static final String LOAD_DAY_FLIGHTS_ARRIVE_ON_TIME = "from Flight as f where f.realArrivalDate < "
			+ "f.expectedArrivalDate and DATE(expectedArrivalDate) = current_date";

	private static final String LOAD_DAY_FLIGHTS_DEPARTURE_ON_TIME = "from Flight as f where f.realDepartureDate < "
			+ "f.expectedDepartureDate and DATE(expectedDepartureDate) = current_date";

	private static final String LOAD_DAY_FLIGHTS_NOT_ARRIVE_ON_TIME = "from Flight as f where (f.realArrivalDate > "
			+ "f.expectedArrivalDate or f.realArrivalDate is NULL) and DATE(expectedArrivalDate) = current_date";

	private static final String LOAD_DAY_FLIGHTS_NOT_DEPARTURE_ON_TIME = "from Flight as f where (f.realDepartureDate > "
			+ "f.expectedDepartureDate or f.realDepartureDate is NULL) and DATE(expectedDepartureDate) = current_date";

	private static final String PARAMETER_PLANE_ID = "planeId";

	private static final String LOAD_AIRPLANE_FLIGHT_HOURS = "select sum(abs(extract(epoch from f.expectedArrivalDate - f.expectedDepartureDate)/3600)) "
			+ "from Flight as f join f.plane where f.plane.id = :" + PARAMETER_PLANE_ID;

	/** The session. */
	private static Session session;

	/**
	 * Sets the null airline flights.
	 *
	 * 
	 * @param airlineUsername
	 *            the airline username
	 * @return true, if successful
	 */
	@SuppressWarnings(UNCHECKED)
	public static boolean setNullAirlineFlights(String airlineUsername) {
		Boolean result = true;
		List<Flight> planeList = new ArrayList<>();
		try {

			session = HibernateConnection.getSession();
			session.beginTransaction();
			Query query = session.createQuery(LOAD_AIRLINE_FLIGHTS);
			query.setParameter(PARAMETER_AIRLINE_USERNAME, airlineUsername);
			planeList = query.getResultList();
			for (Flight plane : planeList) {
				if (plane.getPassengerList() != null) {

					plane.setPassengerList(new ArrayList<Passenger>());
					session.saveOrUpdate(plane);
				}

			}
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;

		} finally {

			HibernateConnection.closeSession(session);
		}

		return result;
	}

	@SuppressWarnings(UNCHECKED)
	public static List<Flight> loadFlightsForTable(String orderCol, String orderDir, int start, int length) {
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_TABLE_FLIGHTS + orderCol + " " + orderDir);

			if (query.getResultList().size() > 0) {
				query.setFirstResult(start);
				query.setMaxResults(length);
				flightList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return flightList;
	}

	public static List<FlightView> loadFlightsOfAirlineByRoute(String airlineUser) {
		List<FlightView> flightViewList = new ArrayList<>();
		RouteStatsAction rsa = new RouteStatsAction();
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_AIRLINE_ROUTE_FLIGHTS);
			query.setParameter(PARAMETER_AIRLINE_USERNAME, airlineUser);
			List<?> list = query.getResultList();
			if (list != null && list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Object[] row = (Object[]) list.get(i);

					FlightView fv = rsa.newFlightView((String) row[1], ((Integer) row[0]).toString());
					flightViewList.add(fv);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return flightViewList;

	}

	/**
	 * Sets the null passenger flights.
	 * 
	 * @return true, if successful
	 */
	@SuppressWarnings(UNCHECKED)
	public static boolean setNullPassengerFlights(String passengerUsername) {
		Boolean result = true;
		List<Flight> flightList = new ArrayList<>();
		try {

			session = HibernateConnection.getSession();
			session.beginTransaction();
			TypedQuery<Flight> query = session.createQuery("from Flight");
			flightList = query.getResultList();
			for (Flight flight : flightList) {

				if (flight.getPassengerList() != null) {
					for (Passenger passenger : flight.getPassengerList()) {
						if (passenger.getUsername().equals(passengerUsername)) {
							flight.getPassengerList().remove(passenger);
						}
						session.saveOrUpdate(flight);
						break;
					}

				}

			}
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;

		} finally {

			HibernateConnection.closeSession(session);
		}

		return result;
	}

	@SuppressWarnings(UNCHECKED)
	public static List<Flight> loadOneWeekFlights() {
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_WEEK_FLIGHTS);
			if (query.getResultList().size() > 0) {
				flightList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return flightList;
	}

	@SuppressWarnings(UNCHECKED)
	public static int loadDayFlightsArriveOnTime() {
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_DAY_FLIGHTS_ARRIVE_ON_TIME);
			flightList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return flightList.size();
	}

	@SuppressWarnings(UNCHECKED)
	public static int loadDayFlightsDepartureOnTime() {
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_DAY_FLIGHTS_DEPARTURE_ON_TIME);
			flightList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return flightList.size();
	}

	@SuppressWarnings(UNCHECKED)
	public static int loadDayFlightsArriveOnNotTime() {
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_DAY_FLIGHTS_NOT_ARRIVE_ON_TIME);
			flightList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return flightList.size();
	}

	@SuppressWarnings(UNCHECKED)
	public static int loadDayFlightsDepartureOnNotTime() {
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_DAY_FLIGHTS_NOT_DEPARTURE_ON_TIME);
			flightList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return flightList.size();
	}

	public static long loadPlaneFlightHours(int planeId) {

		long flightHours = 0;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_AIRPLANE_FLIGHT_HOURS);
			query.setParameter(PARAMETER_PLANE_ID, planeId);
			if (query.getSingleResult() != null)
				flightHours = (long) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return flightHours;

	}

}
