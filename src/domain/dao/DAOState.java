package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import domain.model.State;
import hibernate.HibernateConnection;

public class DAOState {
	private static Session session;

	public static boolean insertState(State state) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.save(state);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			session.close();
		}

		return result;

	}

	public static boolean deleteState(State state) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.delete(state);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		} finally {
			session.close();
		}

		return result;
	}

	public static List<State> loadAllStates() {
		List<State> stateList = null;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<State> query = session.createQuery("from State");
			stateList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return stateList;
	}

}
