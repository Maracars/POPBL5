package domain.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Flight;
import domain.model.Plane;
import hibernate.HibernateConnection;

/**
 * The Class DAOPlane.
 */
public class DAOPlane {

	/** The Constant UNCHECKED. */
	private static final String UNCHECKED = "unchecked";

	/** The Constant MAX_RESULTS. */
	private static final int MAX_RESULTS = 1;

	/** The Constant SECOND_TO_MIN. */
	private static final int SECOND_TO_MIN = 60;

	/** The Constant MILIS_TO_SECOND. */
	private static final int MILIS_TO_SECOND = 1000;

	/** The Constant MIN_TO_HOURS. */
	private static final int MIN_TO_HOURS = 60;
	// private static final int HOURS_TO_DAY = 24;
	// private static final int MILIS_TO_DAYS = MILIS_TO_SECOND * SECOND_TO_MIN
	/** The Constant ARRIVAL_HOUR_MARGIN. */
	// * MIN_TO_HOURS * HOURS_TO_DAY;
	private static final int ARRIVAL_HOUR_MARGIN = 2;

	/** The Constant PARAMETER_AIRPORT_ID. */
	private static final String PARAMETER_AIRPORT_ID = "airportId";

	/** The Constant PARAMETER_SOON_DATE. */
	private static final String PARAMETER_SOON_DATE = "soonDate";

	/** The Constant PARAMETER_AIRLINE_ID. */
	private static final String PARAMETER_AIRLINE_ID = "airlineId";

	/** The Constant SELECT_PLANE. */
	private static final String SELECT_PLANE_JOIN_FLIGHT = "select f.plane from Flight as f right join f.plane as p ";

	/** The Constant SELECT_FLIGHT_JOIN_PLANE. */
	private static final String SELECT_FLIGHT_JOIN_PLANE = "select f from Flight as f right join f.plane as p ";

	/** The Constant QUERY_ARRIVAL_PLANES_SOON. */
	private static final String QUERY_ARRIVAL_FLIGHTS_SOON = SELECT_FLIGHT_JOIN_PLANE
			+ "where f.expectedArrivalDate BETWEEN current_timestamp and :" + PARAMETER_SOON_DATE

			+ " and p.status.positionStatus = 'ARRIVING'" + "and f.route.arrivalTerminal.airport.id = :"
			+ PARAMETER_AIRPORT_ID;

	/** The Constant DEPARTURE_HOUR_MARGIN. */
	private static final int DEPARTURE_HOUR_MARGIN = 3;

	/** The Constant QUERY_DEPARTURING_PLANES_SOON. */
	private static final String QUERY_DEPARTURING_FLIGHTS_SOON = SELECT_FLIGHT_JOIN_PLANE
			+ "where f.expectedDepartureDate BETWEEN current_timestamp and :" + PARAMETER_SOON_DATE
			+ " and p.status.positionStatus = 'ON AIRPORT' and p.status.technicalStatus = 'OK' "
			+ "and f.route.departureTerminal.airport.id = :" + PARAMETER_AIRPORT_ID;

	/** The Constant QUERY_PLANES_NEED_REVISE. */
	private static final String QUERY_PLANES_NEED_REVISE = "from Plane as p "
			+ "where p.status.technicalStatus = 'NEEDS REVISION'";

	/** The Constant QUERY_FREE_PLANE. */
	private static final String QUERY_FREE_PLANE = SELECT_PLANE_JOIN_FLIGHT
			+ "where f.realArrivalDate < current_timestamp or f is null";

	/** The Constant PARAMETER_SERIAL_NUMBER. */
	private static final String PARAMETER_SERIAL_NUMBER = "serialNumber";

	/** The Constant QUERY_PLANE_WITH_SERIAL. */
	private static final String QUERY_PLANE_WITH_SERIAL = "from Plane as p join p.airline as a where p.serial = :"
			+ PARAMETER_SERIAL_NUMBER + " and a.id = :" + PARAMETER_AIRLINE_ID;

	/** The Constant MILIS_TO_HOURS. */
	private static final int MILIS_TO_HOURS = MILIS_TO_SECOND * SECOND_TO_MIN * MIN_TO_HOURS;

	/** The Constant LOAD_TABLE_PLANES. */
	private static final String LOAD_TABLE_PLANES = "from Plane as p join p.airline as a where a.id = :"
			+ PARAMETER_AIRLINE_ID + " order by p.";

	/** The Constant LOAD_ALL_PLANES_FROM_AIRLINE. */
	private static final String LOAD_ALL_PLANES_FROM_AIRLINE = "from Plane as p join p.airline as a where a.id = :"
			+ PARAMETER_AIRLINE_ID;

	/** The session. */
	private static Session session;

	/**
	 * Gets the arriving planes soon.
	 *
	 * 
	 * @param airportId
	 *            the airport id
	 * @return the arriving planes soon
	 */

	@SuppressWarnings(UNCHECKED)
	public static List<Flight> getArrivingFlightsSoon(int airportId) {
		List<Flight> flightList = null;
		Date soon = new Date();
		try {

			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_ARRIVAL_FLIGHTS_SOON);
			query.setParameter(PARAMETER_SOON_DATE, new Date(soon.getTime() + (MILIS_TO_HOURS * ARRIVAL_HOUR_MARGIN)));
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);

			flightList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateConnection.closeSession(session);
		}

		return flightList;
	}

	/**
	 * Gets the departuring planes soon.
	 *
	 * 
	 * @param airportId
	 *            the airport id
	 * @return the departuring planes soon
	 */

	@SuppressWarnings(UNCHECKED)
	public static List<Flight> getDeparturingFlightsSoon(int airportId) {
		List<Flight> flightList = null;
		Date soon = new Date();
		try {

			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_DEPARTURING_FLIGHTS_SOON);
			query.setParameter(PARAMETER_SOON_DATE,
					new Date(soon.getTime() + (MILIS_TO_HOURS * DEPARTURE_HOUR_MARGIN)));
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);

			flightList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateConnection.closeSession(session);
		}

		return flightList;
	}

	/**
	 * Select plane need to revise.
	 *
	 * @return the plane
	 */
	@SuppressWarnings(UNCHECKED)
	public static Plane selectPlaneNeedToRevise() {
		List<Plane> planeList = null;
		try {

			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_PLANES_NEED_REVISE);
			planeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateConnection.closeSession(session);
		}
		return planeList.size() > 0 ? planeList.get(0) : null;
	}

	/**
	 * Gets the free plane.
	 *
	 * @return the free plane
	 */

	@SuppressWarnings(UNCHECKED)
	public static Plane getFreePlane() {

		List<Plane> planeList = null;
		try {

			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_FREE_PLANE);
			planeList = query.setMaxResults(MAX_RESULTS).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateConnection.closeSession(session);
		}

		return planeList.size() > 0 ? planeList.get(0) : null;
	}

	/**
	 * Gets the plane position.
	 *
	 * @return the plane position
	 */
	public static List<Object> getPlanePosition() {
		List<Object> objectList = null;
		try {

			session = HibernateConnection.getSession();
			@SuppressWarnings(UNCHECKED)
			TypedQuery<Object> query = session.createQuery("from Plane");
			objectList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateConnection.closeSession(session);
		}

		return objectList;
	}

	/**
	 * Load all airplanes from airline.
	 *
	 * @param airlineId the airline id
	 * @return the list
	 */
	@SuppressWarnings(UNCHECKED)
	public static List<Plane> loadAllAirplanesFromAirline(int airlineId) {
		List<Object[]> objectList = null;
		List<Plane> planeList = new ArrayList<Plane>();
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_ALL_PLANES_FROM_AIRLINE);
			query.setParameter(PARAMETER_AIRLINE_ID, airlineId);
			if (query.getResultList().size() > 0) {
				objectList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		if (objectList != null) {

			for (Object[] planeObjectList : objectList) {
				Plane plane = (Plane) planeObjectList[0];
				planeList.add(plane);
			}
		}

		return planeList;
	}

	/**
	 * Load airplanes for table.
	 *
	 * @param airlineId the airline id
	 * @param orderCol the order col
	 * @param orderDir the order dir
	 * @param start the start
	 * @param length the length
	 * @return the list
	 */
	@SuppressWarnings(UNCHECKED)
	public static List<Plane> loadAirplanesForTable(int airlineId, String orderCol, String orderDir, int start,
			int length) {
		List<Object[]> objectList = null;
		List<Plane> planeList = new ArrayList<Plane>();
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(LOAD_TABLE_PLANES + orderCol + " " + orderDir);
			query.setParameter(PARAMETER_AIRLINE_ID, airlineId);
			if (query.getResultList().size() > 0) {
				query.setFirstResult(start);
				query.setMaxResults(length);
				objectList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		if (objectList != null) {
			for (Object[] planeObjectList : objectList) {
				Plane plane = (Plane) planeObjectList[0];
				planeList.add(plane);
			}
		}

		return planeList;
	}

	/**
	 * Load airplane with serial.
	 *
	 * @param airlineId the airline id
	 * @param serial the serial
	 * @return the plane
	 */
	@SuppressWarnings(UNCHECKED)
	public static Plane loadAirplaneWithSerial(int airlineId, String serial) {
		Plane plane = null;
		List<Object[]> objectList = null;

		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_PLANE_WITH_SERIAL);
			query.setParameter(PARAMETER_SERIAL_NUMBER, serial);
			query.setParameter(PARAMETER_AIRLINE_ID, airlineId);
			objectList = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}
		if (objectList != null) {
			for (Object[] planeObjectList : objectList) {
				plane = (Plane) planeObjectList[0];
			}
		}
		return plane;
	}
}
