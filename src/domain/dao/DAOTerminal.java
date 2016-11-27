package domain.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import domain.model.Terminal;
import hibernate.HibernateConnection;

public class DAOTerminal {
	private static Session session;

	public static boolean insertTerminal(Terminal terminal) {
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(terminal);
			session.getTransaction().commit();
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			return false;
		}

		return true;

	}

	public static boolean deleteTerminal(Terminal terminal) {
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.delete(terminal);
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			return false;
		}

		return true;
	}

	public static ArrayList<Terminal> loadAllGatesFromTerminal(int airportId) {
		// TODO Auto-generated method stub
		return null;
	}

}
