package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Route;
import hibernate.HibernateConnection;

public class DAORoute {
	private static Session session;

	public static boolean insertRoute(Route route) {
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.persist(route);
			session.getTransaction().commit();
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			return false;
		}

		return true;

	}

	public static boolean deleteRoute(Route route) {
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.delete(route);
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			return false;
		}

		return true;
	}

	public static List<Route> loadAllRoutes() {
		List<Route> routeList = null;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Route> query = session.createQuery("from Route");
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateConnection.after();

		return routeList;
	}

}