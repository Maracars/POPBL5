package domain.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.users.User;
import helpers.MD5;
import hibernate.HibernateConnection;

public class HibernateGeneric {

	private static Session session;

	public static boolean saveOrUpdateObject(Object object) {
		boolean result = true;
		try {
			if (object instanceof User)
				((User) object).setPassword(MD5.encrypt(((User) object).getPassword()));
			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.save(object);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;
		} finally {
			session.close();
		}

		return result;

	}

	public static boolean deleteObject(Object object) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.delete(object);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			session.close();
		}

		return result;
	}

	public static List<Object> loadAllObjects(Object o) {
		List<Object> objectList = null;
		if (o != null) {
			try {

				session = HibernateConnection.getSessionFactory().openSession();
				@SuppressWarnings("unchecked")
				TypedQuery<Object> query = session.createQuery("from " + o.getClass().getSimpleName());
				objectList = query.getResultList();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
		}

		return objectList;
	}

	public static boolean updateObject(Object object) {
		boolean result = true;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.update(object);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;
		} finally {
			session.close();
		}

		return result;

	}

	public static boolean deleteAllObjects(Object object) {
		boolean result = true;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			Query query = session.createQuery("delete from " + object.getClass().getSimpleName());
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;
		} finally {
			session.close();
		}
		return result;
	}

}
