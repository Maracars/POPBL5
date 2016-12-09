package domain.dao;

import java.util.ArrayList;
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
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	private static final String QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID = "from Route as r "
			+ "where r.arrivalGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID;
	private static final String QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID = "from Route as r "
			+ "where r.departureGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID;
	private static final String QUERY_FREE_PLANE = "select f.plane from Flight as f right join f.plane as p "
			+ "with f.realArrivalDate < current_date";

	private static final String QUERY_SOON_ARRIVAL_PLANE = "select f.plane from Flight as f join f.plane as p "
			+ "with f.expectedArrivalDate < current_date";
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
		// TODO hau ondo dabil, kontua da oindio randomena daola gehitzeko, eta
		// ez dau lista bat bueltauko
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
		// TODO hau ondo dabil, kontua da oindio randomena daola gehitzeko, eta
		// ez dau lista bat bueltauko
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

	public static ArrayList<Plane> getArrivingPlanesSoon() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Plane> getDeparturingPlanesSoon() {
		// TODO Auto-generated method stub
		return null;
	}

	public static ArrayList<Lane> getFreeLanes() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Plane selectPlaneNeedToRevise() {
		// TODO Auto-generated method stub
		return null;
	}

	public static void revisePlane(Plane planeToRevise) {
		// TODO Auto-generated method stub

	}

}
