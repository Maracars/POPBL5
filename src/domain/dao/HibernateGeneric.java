package domain.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Lane;
import domain.model.Plane;
import domain.model.Route;
import domain.model.User;
import helpers.MD5;
import hibernate.HibernateConnection;

public class HibernateGeneric {
	private static final int SECOND_TO_MIN = 60;
	private static final int MILIS_TO_SECOND = 1000;
	private static final int MIN_TO_HOURS = 60;
	private static final int HOURS_TO_DAY = 24;
	private static final int ARRIVAL_DAY_MARGIN = 2;
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	private static final String PARAMETER_SOON_DATE = "soonDate";
	private static final String QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID = "from Route as r "
			+ "where r.arrivalGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID;
	private static final String QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID = "from Route as r "
			+ "where r.departureGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID;
	private static final String QUERY_FREE_PLANE = "select f.plane from Flight as f right join f.plane as p "
			+ "with f.realArrivalDate < current_date";

	private static final String QUERY_ARRIVAL_PLANES_SOON = "select f.plane from Flight as f right join f.plane as p "
			+ "where f.expectedArrivalDate BETWEEN current_timestamp and :" + PARAMETER_SOON_DATE;
	private static final int DEPARTURE_DAY_MARGIN = 0;
	private static final String QUERY_DEPARTURING_PLANES_SOON = "select f.plane from Flight as f right join f.plane as p "
			+ "where f.expectedDepartureDate BETWEEN current_timestamp and :" + PARAMETER_SOON_DATE;
	private static final String QUERY_FREE_LANES = "from Lane as l "
			+ "where l.principal is true and l.status is true and l.airport.id = :" + PARAMETER_AIRPORT_ID;
	private static final String QUERY_PLANES_NEED_REVISE = "from Plane as p where p.status.name = 'NEEDS REVISION'";
	private static Session session;

	public static boolean insertObject(Object object) {
		boolean result = true;
		try {
			if (object instanceof User)
				((User) object).setPassword(MD5.encrypt(((User) object).getPassword()));
			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.save(object);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;
		} finally {
			session.close();
		}

		return result;

	}

	public static boolean deleteObject(Object object) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.delete(object);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			session.close();
		}

		return result;
	}

	public static List<Object> loadAllObjects(Object o) {
		List<Object> objectList = null;
		if (o != null) {
			try {

				session = HibernateConnection.getSessionFactory().openSession();
				@SuppressWarnings("unchecked")
				TypedQuery<Object> query = session.createQuery("from " + o.getClass().getSimpleName());
				objectList = query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
		}

		return objectList;
	}

	@SuppressWarnings("unchecked")
	public static List<Route> getRandomArrivalRouteFromAirport(int airportId) {
		// TODO hau ondo dabil, kontua da oindio randomena daola gehitzeko, eta
		// ez dau lista bat bueltauko
		List<Route> routeList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return routeList;
	}

	public static Plane getFreePlane() {

		Plane plane = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_FREE_PLANE);
			plane = (Plane) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return plane;
	}

	@SuppressWarnings("unchecked")
	public static Route selectDepartureRouteFromAirport(int airportId) {

		List<Route> routeList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return routeList.get(0);
	}

	@SuppressWarnings("unchecked")
	public static List<Plane> getArrivingPlanesSoon() {
		List<Plane> planeList = null;
		Date soon = new Date();
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			// TODO PLANESTATUS GEHITZEN DANIAN HAU INPLEMENTATZEKO GERATZEN DA
			Query query = session.createQuery(QUERY_ARRIVAL_PLANES_SOON);
			query.setParameter(PARAMETER_SOON_DATE, new Date(soon.getTime()
					+ (MILIS_TO_SECOND * SECOND_TO_MIN * MIN_TO_HOURS * HOURS_TO_DAY * ARRIVAL_DAY_MARGIN)));

			planeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return planeList;
	}

	@SuppressWarnings("unchecked")
	public static List<Plane> getDeparturingPlanesSoon() {
		List<Plane> planeList = null;
		Date soon = new Date();
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			// TODO PLANESTATUS GEHITZEN DANIAN HAU INPLEMENTATZEKO GERATZEN DA
			Query query = session.createQuery(QUERY_DEPARTURING_PLANES_SOON);
			query.setParameter(PARAMETER_SOON_DATE, new Date(soon.getTime()
					+ (MILIS_TO_SECOND * SECOND_TO_MIN * MIN_TO_HOURS * HOURS_TO_DAY * DEPARTURE_DAY_MARGIN)));

			planeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return planeList;
	}

	@SuppressWarnings("unchecked")
	public static List<Lane> getFreeLanes(int airportId) {
		List<Lane> laneList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_FREE_LANES);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			if (query.getResultList().size() > 0) {
				laneList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return laneList;
	}

	public static Plane selectPlaneNeedToRevise() {
		Plane plane = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_PLANES_NEED_REVISE);
			plane = (Plane) query.getSingleResult();

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
