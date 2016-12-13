package domain.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.users.User;
import hibernate.HibernateConnection;

public class DAOUser {
	private static final String USERNAME_QUERY = "from User U where U.username = '";
	private static Session session;

	public static User getUser(String username) {
		List<User> userList = null;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<User> query = session.createQuery(USERNAME_QUERY + username + "'");
			userList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return userList.isEmpty() ? null : userList.get(0);

	}

	public static boolean deleteUserWithUsername(User user) {
		int result;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();

			Query query = session.createQuery("delete User where username = :username");
			query.setParameter("username", user.getUsername());
			result = query.executeUpdate();
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = 0;
		} finally {
			session.close();
		}

		return result > 0 ? true : false;

	}
}
