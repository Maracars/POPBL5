package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Airport;
import hibernate.HibernateConnection;

public class DAOAirport {
	private static Session session;

	public static boolean insertAirport(Airport airport) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.save(airport);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			session.close();
		}
		return result;

	}

	public static boolean deleteAirport(Airport airport) {
		boolean result = true;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.delete(airport);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			session.close();
		}

		return result;
	}

	public static List<Airport> loadAllAirports() {
		List<Airport> airportList = null;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Airport> query = session.createQuery("from Airport");
			airportList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return airportList;
	}

}
