package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Node;
import hibernate.HibernateConnection;

public class DAONode {
	private static Session session;

	public static boolean insertNode(Node node) {
		boolean result = true;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(node);
			session.getTransaction().commit();
			HibernateConnection.after();

		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			result = false;
		}

		return result;

	}

	public static boolean deleteNode(Node node) {
		boolean result = true;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.delete(node);
			HibernateConnection.after();
		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			result = false;
		}

		return result;
	}

	public static List<Node> loadAllNodes() {
		List<Node> nodeList = null;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Node> query = session.createQuery("from Node");
			nodeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateConnection.after();

		return nodeList;
	}


}
