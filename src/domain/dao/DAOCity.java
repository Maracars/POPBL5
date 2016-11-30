package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import domain.model.City;
import hibernate.HibernateConnection;

public class DAOCity {
	private static Session session;

	public static boolean insertCity(City city) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.save(city);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			session.close();
		}

		return result;

	}

	public static boolean deleteCity(City city) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.delete(city);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			session.close();
		}

		return result;
	}

	public static List<City> loadAllCities() {
		List<City> cityList = null;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<City> query = session.createQuery("from City");
			cityList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return cityList;
	}

}
