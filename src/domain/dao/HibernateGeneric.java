package domain.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Airline;
import domain.model.Lane;
import domain.model.Plane;
import domain.model.Route;
import domain.model.User;
import helpers.MD5;
import hibernate.HibernateConnection;

public class HibernateGeneric {
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

	public static Route selectRandomArrivalRoute() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Plane getFreePlane() {
		// TODO Auto-generated method stub
		return null;
	}

	public static Route selectDepartureRouteFromAirline(Airline airline) {
		// TODO Auto-generated method stub
		return null;
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
