package domain.dao;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.users.Airline;
import domain.model.users.User;
import hibernate.HibernateConnection;

public class DAOUser {
	private static final String PARAMETER_USERNAME = "username";
	private static final String USERNAME_QUERY = "from User U where U.username = :" + PARAMETER_USERNAME;
	private static Session session;

	public static User getUser(String username) {
		User user = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(USERNAME_QUERY);
			query.setParameter(PARAMETER_USERNAME, username);
			user = (User) query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return user;

	}

	public static boolean deleteUserWithUsername(User user) {
		int result;
		try {
			if (user instanceof Airline) {
				Airline airline = (Airline) user;
				DAOFlight.setNullAirlineFlights(airline.getUsername());

			}

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			System.out.println(user.getClass().getSimpleName());

			Query query = session.createQuery("delete " + user.getClass().getSimpleName() + " where username = :" + PARAMETER_USERNAME);
			query.setParameter(PARAMETER_USERNAME, user.getUsername());
			result = query.executeUpdate();
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();

			result = 0;
		} finally {
			session.close();
		}

		return result > 0 ? true : false;

	}
	

}
