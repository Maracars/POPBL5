package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import domain.model.City;
import hibernate.HibernateConnection;

public class DAOCity {
	private static Session session;

	public static boolean insertCity(City city) {
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.persist(city);
			session.getTransaction().commit();
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			return false;
		}

		return true;

	}

	public static boolean deleteCity(City city) {
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.delete(city);
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			return false;
		}

		return true;
	}

	public static List<City> loadAllCities() {
		List<City> cityList = null;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<City> query = session.createQuery("from City");
			cityList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateConnection.after();

		return cityList;
	}

}
