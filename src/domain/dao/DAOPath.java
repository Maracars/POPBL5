package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Path;
import hibernate.HibernateConnection;

public class DAOPath {

	private static Session session;

	public static List<Path> loadAllFreePaths() {
		List<Path> pathList = null;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Path> query = session.createQuery("select p from Path p inner join p.laneList l where l.status is not false");
			pathList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return pathList;
	}
}
