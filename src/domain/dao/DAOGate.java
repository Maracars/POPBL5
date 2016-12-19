package domain.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Gate;
import hibernate.HibernateConnection;

public class DAOGate {
	private static Session session;

	public static boolean insertGate(Gate gate) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(gate);
			session.getTransaction().commit();
			

		} catch (Exception e) {
			session.getTransaction().rollback();
			
			result = false;
		}

		return result;

	}

	public static boolean deleteGate(Gate gate) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.delete(gate);
			session.getTransaction().commit();

			

		} catch (Exception e) {
			session.getTransaction().rollback();
			
			result = false;
		}

		return result;
	}
	public static List<Gate> loadAllGates() {
		List<Gate> gateList = null;
		try {
			 
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Gate> query = session.createQuery("from Gate");
			gateList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 

		return gateList;
	}

	public static ArrayList<Gate> loadAllGatesFromTerminal(int terminalId) {

		return null;
	}

}
