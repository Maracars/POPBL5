package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Route;
import hibernate.HibernateConnection;

public class DAORoute {
	private static Session session;

	public static boolean insertRoute(Route route) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(route);
			session.getTransaction().commit();
			

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			
			result = false;
		}

		return result;

	}

	public static boolean deleteRoute(Route route) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.delete(route);
			session.getTransaction().commit();
			

		} catch (Exception e) {
			session.getTransaction().rollback();
			
			result = false;
		}

		return result;
	}

	public static List<Route> loadAllRoutes() {
		List<Route> routeList = null;
		try {
			
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Route> query = session.createQuery("from Route");
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return routeList;
	}

}
