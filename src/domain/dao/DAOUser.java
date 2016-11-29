package domain.dao;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.User;
import hibernate.HibernateConnection;

public class DAOUser {
	private static Session session;
	private final static String SALT = "Kappa123&/()==?%#@|º¡¿@$@4&#%^$*";

	public static String md5(String input) {

		String md5 = null;

		if (null == input)
			return null;

		try {
			input = input  + SALT;
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(input.getBytes(), 0, input.length());
			md5 = new BigInteger(1, digest.digest()).toString(16);

		} catch (NoSuchAlgorithmException e) {

			e.printStackTrace();
		}
		return md5;
	}

	public static boolean insertUser(User user) {
		user.setPassword(md5(user.getPassword()));
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(user);
			session.getTransaction().commit();
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			return false;
		}

		return true;

	}
	
	public static User getUser(String username) {
		List<User> userList = null;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<User> query = session
									.createQuery("from User U where U.username = " + username);
			userList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateConnection.after();
		
		return userList.isEmpty() ? null : userList.get(0);

	}

	public static boolean deleteUser(User user) {
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.delete(user);
			HibernateConnection.after();
		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			return false;
		}

		return true;
	}

	public static List<User> loadAllUsers() {
		List<User> userList = null;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<User> query = session.createQuery("from User");
			userList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateConnection.after();

		return userList;
	}
}
