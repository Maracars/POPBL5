package domain.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;

import domain.model.users.User;
import helpers.MD5;
import hibernate.HibernateConnection;


/**
 * The Class HibernateGeneric.
 */
public class HibernateGeneric {

	/** The session. */
	private static Session session;

	/**
	 * Save or update object.
	 *
	 * @param object the object
	 * @return true, if successful
	 */
	public static boolean saveObject(Object object) {
		boolean result = true;
		try {
			if (object instanceof User)
				((User) object).setPassword(MD5.encrypt(((User) object).getPassword()));
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(object);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;
		} finally {
			HibernateConnection.closeSession(session);
		}

		return result;

	}

	/**
	 * Delete object.
	 *
	 * @param object the object
	 * @return true, if successful
	 */
	public static boolean deleteObject(Object object) {
		boolean result = true;
		try {

			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.delete(object);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			HibernateConnection.closeSession(session);
		}

		return result;
	}

	/**
	 * Load all objects.
	 * Loads all objects of class of the object o
	 * @param o the object that defines the requested type of the objects
	 * @return the list
	 */
	public static List<Object> loadAllObjects(Object o) {
		List<Object> objectList = null;
		if (o != null) {
			try {

				session = HibernateConnection.getSession();
				@SuppressWarnings("unchecked")
				TypedQuery<Object> query = session.createQuery("from " + o.getClass().getSimpleName());
				objectList = query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				HibernateConnection.closeSession(session);
			}
		}

		return objectList;
	}

	/**
	 * Update object.
	 *
	 * @param object the object
	 * @return true, if successful
	 */
	public static boolean updateObject(Object object) {
		boolean result = true;
		Transaction transaction = null;
		try {
			session = HibernateConnection.getSession();
			transaction = session.getTransaction();
			transaction.begin();
			session.update(object);
			transaction.commit();

		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			result = false;
		} finally {
			HibernateConnection.closeSession(session);
		}

		return result;
	}

	/**
	 * Delete all objects.
	 *
	 * @param object the object
	 * @return true, if successful
	 */
	public static boolean deleteAllObjects(Object object) {
		boolean result = true;
		try {
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			Query query = session.createQuery("delete from " + object.getClass().getSimpleName());
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;
		} finally {
			HibernateConnection.closeSession(session);
		}
		return result;
	}

}
