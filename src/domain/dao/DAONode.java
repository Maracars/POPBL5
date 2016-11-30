package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import domain.model.Node;
import hibernate.HibernateConnection;

public class DAONode {
	private static Session session;
	private static SessionFactory sessionFactory;

	public static boolean insertNode(Node node) {
		boolean result = true;
		try {
			HibernateConnection.before();
			sessionFactory = HibernateConnection.getSessionFactory();
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			session.save(node);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		}finally {
			
			HibernateConnection.after();
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
