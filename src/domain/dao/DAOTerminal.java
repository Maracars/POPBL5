package domain.dao;

import java.util.ArrayList;

import org.hibernate.Session;
import domain.model.Terminal;
import hibernate.HibernateConnection;

public class DAOTerminal {
	private static Session session;

	public static boolean insertTerminal(Terminal terminal) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(terminal);
			session.getTransaction().commit();
			

		} catch (Exception e) {
			session.getTransaction().rollback();
			
			result = false;
		}

		return result;

	}

	public static boolean deleteTerminal(Terminal terminal) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(terminal);
			session.getTransaction().commit();
			

		} catch (Exception e) {
			session.getTransaction().rollback();
			
			result = false;
		}

		return result;
	}

	public static ArrayList<Terminal> loadAllGatesFromTerminal(int airportId) {
		// TODO Auto-generated method stub
		return null;
	}

}
