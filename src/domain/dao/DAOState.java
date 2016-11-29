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
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(state);
			session.getTransaction().commit();
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			result = false;
		}

		return result;

	}

	public static boolean deleteState(State state) {
		boolean result = true;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.delete(state);
			HibernateConnection.after();
		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			result = false;
		}

		return result;
	}

	public static List<State> loadAllStates() {
		List<State> stateList = null;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<State> query = session.createQuery("from State");
			stateList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateConnection.after();

		return stateList;
	}

}
