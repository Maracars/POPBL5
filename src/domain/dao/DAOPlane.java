package domain.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Plane;
import hibernate.HibernateConnection;

public class DAOPlane {
	private static final int MAX_RESULTS = 1;
	private static final int SECOND_TO_MIN = 60;
	private static final int MILIS_TO_SECOND = 1000;
	private static final int MIN_TO_HOURS = 60;
	private static final int HOURS_TO_DAY = 24;
	private static final int MILIS_TO_DAYS = MILIS_TO_SECOND * SECOND_TO_MIN * MIN_TO_HOURS * HOURS_TO_DAY;
	private static final int ARRIVAL_DAY_MARGIN = 2;
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	private static final String PARAMETER_SOON_DATE = "soonDate";

	private static final String SELECT_PLANE = "select f.plane from Flight as f right join f.plane as p ";
	private static final String QUERY_ARRIVAL_PLANES_SOON = SELECT_PLANE
			+ "where f.expectedArrivalDate BETWEEN current_timestamp and :" + PARAMETER_SOON_DATE
			+ " and p.status.positionStatus = 'ARRIVING'" + "and f.route.departureGate.terminal.airport.id = :"
			+ PARAMETER_AIRPORT_ID;
	private static final int DEPARTURE_DAY_MARGIN = 2;
	private static final String QUERY_DEPARTURING_PLANES_SOON = SELECT_PLANE
			+ "where f.expectedDepartureDate BETWEEN current_timestamp and :" + PARAMETER_SOON_DATE
			+ " and p.status.positionStatus = 'ON AIRPORT' and p.status.technicalStatus = 'OK' "
			+ "and f.route.departureGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID;
	
	private static final String QUERY_PLANES_NEED_REVISE = "from Plane as p "
			+ "where p.status.technicalStatus = 'NEEDS REVISION'";

	private static final String QUERY_FREE_PLANE = SELECT_PLANE 
			+ "with f.realArrivalDate < current_date";
	
	private static Session session;

	@SuppressWarnings("unchecked")
	public static List<Plane> getArrivingPlanesSoon(int airportId) {
		List<Plane> planeList = null;
		Date soon = new Date();
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_ARRIVAL_PLANES_SOON);
			query.setParameter(PARAMETER_SOON_DATE, new Date(soon.getTime() 
					+ (MILIS_TO_DAYS * ARRIVAL_DAY_MARGIN)));
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);

			planeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return planeList;
	}

	@SuppressWarnings("unchecked")
	public static List<Plane> getDeparturingPlanesSoon(int airportId) {
		List<Plane> planeList = null;
		Date soon = new Date();
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			// TODO PLANESTATUS GEHITZEN DANIAN HAU INPLEMENTATZEKO GERATZEN DA
			Query query = session.createQuery(QUERY_DEPARTURING_PLANES_SOON);
			query.setParameter(PARAMETER_SOON_DATE, new Date(soon.getTime() 
					+ (MILIS_TO_DAYS * DEPARTURE_DAY_MARGIN)));
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);

			planeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return planeList;
	}

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

	public static Plane getFreePlane() {

		Plane plane = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_FREE_PLANE);
			plane = (Plane) query.setMaxResults(MAX_RESULTS).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return plane;
	}

	public static boolean revisePlane(Plane planeToRevise) {
		return false;
		// TODO Auto-generated method stub

	}

}
