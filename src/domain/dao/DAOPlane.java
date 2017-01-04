package domain.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Plane;
import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOPlane.
 */
public class DAOPlane {
	
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

	/** The Constant SELECT_PLANE. */
	private static final String SELECT_PLANE = "select f.plane from Flight as f right join f.plane as p ";
	
	/** The Constant QUERY_ARRIVAL_PLANES_SOON. */
	private static final String QUERY_ARRIVAL_PLANES_SOON = SELECT_PLANE
			+ "where f.expectedArrivalDate BETWEEN current_timestamp and :" + PARAMETER_SOON_DATE
			+ " and p.status.positionStatus = 'ARRIVING'" + "and f.route.departureGate.terminal.airport.id = :"
			+ PARAMETER_AIRPORT_ID;
	
	/** The Constant DEPARTURE_HOUR_MARGIN. */
	private static final int DEPARTURE_HOUR_MARGIN = 3;
	
	/** The Constant QUERY_DEPARTURING_PLANES_SOON. */
	private static final String QUERY_DEPARTURING_PLANES_SOON = SELECT_PLANE
			+ "where f.expectedDepartureDate BETWEEN current_timestamp and :" + PARAMETER_SOON_DATE
			+ " and p.status.positionStatus = 'ON AIRPORT' and p.status.technicalStatus = 'OK' "
			+ "and f.route.departureGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID;

	/** The Constant QUERY_PLANES_NEED_REVISE. */
	private static final String QUERY_PLANES_NEED_REVISE = "from Plane as p "
			+ "where p.status.technicalStatus = 'NEEDS REVISION'";

	/** The Constant QUERY_FREE_PLANE. */
	private static final String QUERY_FREE_PLANE = SELECT_PLANE
			+ "where f.realArrivalDate < current_timestamp or f is null";
	
	/** The Constant MILIS_TO_HOURS. */
	private static final int MILIS_TO_HOURS = MILIS_TO_SECOND * SECOND_TO_MIN * MIN_TO_HOURS;

	/** The session. */
	private static Session session;

	/**
	 * Gets the arriving planes soon.
	 *
	 * @param airportId the airport id
	 * @return the arriving planes soon
	 */
	@SuppressWarnings("unchecked")
	public static List<Plane> getArrivingPlanesSoon(int airportId) {
		List<Plane> planeList = null;
		Date soon = new Date();
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_ARRIVAL_PLANES_SOON);
			query.setParameter(PARAMETER_SOON_DATE, new Date(soon.getTime() + (MILIS_TO_HOURS * ARRIVAL_HOUR_MARGIN)));
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);

			planeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return planeList;
	}

	/**
	 * Gets the departuring planes soon.
	 *
	 * @param airportId the airport id
	 * @return the departuring planes soon
	 */
	@SuppressWarnings("unchecked")
	public static List<Plane> getDeparturingPlanesSoon(int airportId) {
		List<Plane> planeList = null;
		Date soon = new Date();
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			// TODO PLANESTATUS GEHITZEN DANIAN HAU INPLEMENTATZEKO GERATZEN DA
			Query query = session.createQuery(QUERY_DEPARTURING_PLANES_SOON);
			query.setParameter(PARAMETER_SOON_DATE,
					new Date(soon.getTime() + (MILIS_TO_HOURS * DEPARTURE_HOUR_MARGIN)));
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);

			planeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return planeList;
	}

	/**
	 * Select plane need to revise.
	 *
	 * @return the plane
	 */
	public static Plane selectPlaneNeedToRevise() {
		Plane plane = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_PLANES_NEED_REVISE);
			plane = (Plane) query.setMaxResults(MAX_RESULTS).getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return plane;
	}

	/**
	 * Gets the free plane.
	 *
	 * @return the free plane
	 */
	@SuppressWarnings("unchecked")
	public static Plane getFreePlane() {

		List<Plane> planeList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_FREE_PLANE);
			planeList = query.setMaxResults(MAX_RESULTS).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
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

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Object> query = session.createQuery("from Plane");
			objectList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return objectList;
	}
}
