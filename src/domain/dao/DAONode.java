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
			
			sessionFactory = HibernateConnection.getSessionFactory();
			session = sessionFactory.openSession();
			session.getTransaction().begin();
			session.save(node);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		}

		return result;

	}

	public static boolean deleteNode(Node node) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.delete(node);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			
			result = false;
		}

		return result;
	}

	public static List<Node> loadAllNodes() {
		List<Node> nodeList = null;
		try {
			
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Node> query = session.createQuery("from Node");
			nodeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

		return nodeList;
	}


}
