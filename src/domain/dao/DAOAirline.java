package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Airline;
import hibernate.HibernateConnection;

public class DAOAirline {
	
	private static Session session;

	public static boolean insertAirline(Airline airline) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.persist(airline);
			session.getTransaction().commit();
			

		} catch (Exception e) {
			session.getTransaction().rollback();
			
			result = false;
		}

		return result;

	}

	public static boolean deleteAirline(Airline airline) {
		boolean result = true;
		try {

			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.delete(airline);
			session.getTransaction().commit();


		} catch (Exception e) {
			session.getTransaction().rollback();
			
			result = false;
		}
		
		return result;
	}

	public static List<Airline> loadAllAirlines() {
		List<Airline> airlineList = null;
		try {
			
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Airline> query = session.createQuery("from Airline");
			airlineList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return airlineList;
	}

}
