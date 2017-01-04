package domain.dao;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.users.Airline;
import domain.model.users.Passenger;
import domain.model.users.User;
import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOUser.
 */
public class DAOUser {
	
	/** The Constant PARAMETER_USERNAME. */
	private static final String PARAMETER_USERNAME = "username";
	
	/** The Constant USERNAME_QUERY. */
	private static final String USERNAME_QUERY = "from User U where U.username = :" + PARAMETER_USERNAME;
	
	/** The session. */
	private static Session session;

	/**
	 * Gets the user.
	 *
	 * @param username the username
	 * @return the user
	 */
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

	/**
	 * Delete user with username.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	public static boolean deleteUserWithUsername(User user) {
		int result = 0;
		try {
			if (user instanceof Airline) {
				DAOFlight.setNullAirlineFlights(((Airline) user).getUsername());

			}
			if (user instanceof Passenger) {
				DAOFlight.setNullPassengerFlights(((Passenger) user).getUsername());


			}

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();

			Query query = session.createQuery(
					"delete " + user.getClass().getSimpleName() + " where username = :" + PARAMETER_USERNAME);
			query.setParameter(PARAMETER_USERNAME, user.getUsername());
			result = query.executeUpdate();
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();

		} finally {
			session.close();
		}

		return result > 0 ? true : false;

	}

	/**
	 * Check username exists.
	 *
	 * @param username the username
	 * @return true, if successful
	 */
	public static boolean checkUsernameExists(String username) {
		long result = 0;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery("select count(*) " + USERNAME_QUERY);
			query.setParameter(PARAMETER_USERNAME, username);
			result = (long) query.getSingleResult();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result > 0 ? true : false;

	}

}
