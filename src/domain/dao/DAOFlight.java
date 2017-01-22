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

	/** The Constant UNCHECKED. */
	private static final String UNCHECKED = "unchecked";

	/** The Constant PARAMETER_AIRLINE_USERNAME. */
	private static final String PARAMETER_AIRLINE_USERNAME = "airlineUsername";

	/** The Constant LOAD_AIRLINE_FLIGHTS. */
	private static final String LOAD_AIRLINE_FLIGHTS = "from Flight as f where f.plane.airline.username = :"
			+ PARAMETER_AIRLINE_USERNAME;

	/** The Constant LOAD_TABLE_FLIGHTS. */
	private static final String LOAD_TABLE_FLIGHTS = "from Flight as f order by f.";

	/** The Constant LOAD_TABLE_FLIGHT_USER. */
	private static final String LOAD_TABLE_FLIGHT_USER = "select f from Flight f, Passenger p FETCH ALL PROPERTIES where p.id = :passengerId"
			+ " and p in elements(f.passengerList) and (lower(f.route.arrivalTerminal.airport.address.city) like :search or"
			+ " lower(f.route.departureTerminal.airport.name) like :search or "
			+ " lower(f.plane.airline.name) like :search or lower(f.plane.serial) like :search) and lower(f.route.departureTerminal.airport.name) like :origin and "
			+ "	lower(f.route.arrivalTerminal.airport.address.city) like :destination order by f.";

	/** The Constant LOAD_AIRLINE_ROUTE_FLIGHTS. */
	private static final String LOAD_AIRLINE_ROUTE_FLIGHTS = "select count(*), f.route.id from Flight as f where f.plane.airline.username =:"
			+ PARAMETER_AIRLINE_USERNAME + " group by f.route";

	/** The Constant LOAD_WEEK_FLIGHTS. */
	private static final String LOAD_WEEK_FLIGHTS = "from Flight as f where DATE(f.expectedArrivalDate) = "
			+ "current_date or DATE(f.expectedDepartureDate) = current_date";

	/** The Constant LOAD_DAY_FLIGHTS_ARRIVE_ON_TIME. */
	private static final String LOAD_DAY_FLIGHTS_ARRIVE_ON_TIME = "from Flight as f where f.realArrivalDate < "
			+ "f.expectedArrivalDate and DATE(expectedArrivalDate) = current_date";

	/** The Constant LOAD_DAY_FLIGHTS_DEPARTURE_ON_TIME. */
	private static final String LOAD_DAY_FLIGHTS_DEPARTURE_ON_TIME = "from Flight as f where f.realDepartureDate < "
			+ "f.expectedDepartureDate and DATE(expectedDepartureDate) = current_date";

	/** The Constant LOAD_DAY_FLIGHTS_NOT_ARRIVE_ON_TIME. */
	private static final String LOAD_DAY_FLIGHTS_NOT_ARRIVE_ON_TIME = "from Flight as f where (f.realArrivalDate > "
			+ "f.expectedArrivalDate or f.realArrivalDate is NULL) and DATE(expectedArrivalDate) = current_date";

	/** The Constant LOAD_DAY_FLIGHTS_NOT_DEPARTURE_ON_TIME. */
	private static final String LOAD_DAY_FLIGHTS_NOT_DEPARTURE_ON_TIME = "from Flight as f where (f.realDepartureDate > "
			+ "f.expectedDepartureDate or f.realDepartureDate is NULL) and DATE(expectedDepartureDate) = current_date";

	/** The Constant PARAMETER_PLANE_ID. */
	private static final String PARAMETER_PLANE_ID = "planeId";

	/** The Constant LOAD_ALL_DEPARTURE_FLIGHTS. */
	private static final String LOAD_ALL_DEPARTURE_FLIGHTS = "from Flight as f where f.expectedDepartureDate > current_timestamp "
			+ "and f.realDepartureDate is NULL and f.route.departureTerminal.airport.locale = true";

	/** The Constant LOAD_DEPARTURE_FLIGHTS_TABLE. */
	private static final String LOAD_DEPARTURE_FLIGHTS_TABLE = LOAD_ALL_DEPARTURE_FLIGHTS + " order by f.";

	/** The Constant PARAMETER_FLIGHT_ID. */
	private static final String PARAMETER_FLIGHT_ID = "flightId";

	/** The Constant LOAD_FLIGHT_BY_ID. */
	private static final String LOAD_FLIGHT_BY_ID = "from Flight as f where f.id = :" + PARAMETER_FLIGHT_ID;
	

	/** The Constant LOAD_FLIGH_PASSENGERS. */
	private static final String LOAD_FLIGH_PASSENGERS = "select f.passengerList from Flight as f.id = :"
			+ PARAMETER_FLIGHT_ID;

	/** The Constant LOAD_AIRPLANE_FLIGHT_HOURS. */
	private static final String LOAD_AIRPLANE_FLIGHT_HOURS = "select sum(abs(extract(epoch from f.expectedArrivalDate - "

			+ "f.expectedDepartureDate)/3600)) from Flight as f join f.plane where f.plane.id = :" + PARAMETER_PLANE_ID;
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

	/**
	 * Load flights for table pasenger.
	 *
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param search the search
	 * @param destination the destination
	 * @param origin the origin
	 * @param passengerId the passenger id
	 * @return the list
	 */
	@SuppressWarnings(UNCHECKED)
	public static List<Flight> loadFlightsForTablePasenger(String orderCol, String orderDir, String search, String destination, String origin,
			int passengerId) {
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_TABLE_FLIGHT_USER + orderCol + " " + orderDir);
			query.setParameter("passengerId", passengerId);
			query.setParameter("search", "%" + search.toLowerCase() + "%");
			query.setParameter("destination", "%" + destination.toLowerCase() + "%");
			query.setParameter("origin", "%" + origin.toLowerCase() + "%");
			flightList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateConnection.closeSession(session);
		}

		return flightList;
	}

	/**
	 * Load flights for table.
	 *
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @return the list
	 */
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

	/**
	 * Load flights of airline by route.
	 *
	 * @param airlineUser the airline user
	 * @return the list
	 */
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

					FlightView fv = rsa.newFlightView((String) row[1].toString(), ((Long) row[0]).toString());
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
	 * @param passengerUsername the passenger username
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

	/**
	 * Load one week flights.
	 *
	 * @return the list
	 */
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

	/**
	 * Load day flights arrive on time.
	 *
	 * @return the int
	 */
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

	/**
	 * Load day flights departure on time.
	 *
	 * @return the int
	 */
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

	/**
	 * Load day flights arrive on not time.
	 *
	 * @return the int
	 */
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

	/**
	 * Load day flights departure on not time.
	 *
	 * @return the int
	 */
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

	/**
	 * Load plane flight hours.
	 *
	 * @param planeId the plane id
	 * @return the long
	 */
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

	
	/**
	 * Load next departure flights.
	 *
	 * @return the list
	 */
	@SuppressWarnings(UNCHECKED)
	public static List<Flight> loadNextDepartureFlights() {
		List<Flight> flightList = null;
		try {
			
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_ALL_DEPARTURE_FLIGHTS);
			flightList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		
			HibernateConnection.closeSession(session);
		}

		return flightList;
	}

	/**
	 * Load flight by id.
	 *
	 * @param flightId the flight id
	 * @return the flight
	 */
	public static Flight loadFlightById(int flightId){
		Flight flight = null;
		try {

			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_FLIGHT_BY_ID);
			query.setParameter(PARAMETER_FLIGHT_ID, flightId);
			flight = (Flight) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
	
			HibernateConnection.closeSession(session);
		}

		return flight;

	}

	
	/**
	 * Load next departure flights for table.
	 *
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public static List<Flight> loadNextDepartureFlightsForTable(String orderCol, String orderDir, int start,
			int length) {
		List<Flight> flightList = null;
		try {
		
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_DEPARTURE_FLIGHTS_TABLE + orderCol + " " + orderDir);
		
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

	/**
	 * Filter departuring flights.
	 *
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @param search the search
	 * @return the list
	 */
	@SuppressWarnings(UNCHECKED)
	public static List<Flight> filterDeparturingFlights(String orderCol, String orderDir, int start, int length,
			String search) {
		List<Flight> flightList = null;
		String searchToLower = search.toLowerCase();
		try {
			session = HibernateConnection.getSession();
			Query query = session
					.createQuery("from Flight as f where lower(f.route.arrivalTerminal.airport.name) LIKE :destination "
							+ "or lower(f.plane.serial) LIKE :serial or lower(f.plane.status.positionStatus) LIKE :positionStatus"
							+ " and f.expectedDepartureDate > current_timestamp "
							+ "and f.realDepartureDate is NULL and f.route.departureTerminal.airport.locale = true");
			query.setParameter("serial", "%" + searchToLower + "%");
			query.setParameter("positionStatus", "%" + searchToLower + "%");
			query.setParameter("destination", "%" + searchToLower + "%");

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
	
	/**
	 * Filter arrival flights.
	 *
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @param search the search
	 * @return the list
	 */
	@SuppressWarnings(UNCHECKED)
	public static List<Flight> filterArrivalFlights(String orderCol, String orderDir, int start, int length,
			String search) {
		List<Flight> flightList = null;
		String searchToLower = search.toLowerCase();
		try {
			session = HibernateConnection.getSession();
			Query query = session
					.createQuery("from Flight as f where lower(f.route.departureTerminal.airport.name) LIKE :origin "
							+ "or lower(f.plane.serial) LIKE :serial or lower(f.plane.status.positionStatus) LIKE :positionStatus"
							+ " and f.expectedArrivalDate > current_timestamp "
							+ "and f.realArrivalDate is NULL and f.route.arrivalTerminal.airport.locale = true");
			query.setParameter("serial", "%" + searchToLower + "%");
			query.setParameter("positionStatus", "%" + searchToLower + "%");
			query.setParameter("origin", "%" + searchToLower + "%");

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

}
