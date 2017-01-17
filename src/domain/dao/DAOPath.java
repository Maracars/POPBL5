package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Path;
import hibernate.HibernateConnection;

/**
 * The Class DAOPath.
 */
public class DAOPath {

	/** The session. */
	private static Session session;

	/**
	 * Load all free paths.
	 *
	 * @return the list
	 */
	public static List<Path> loadAllPaths() {
		List<Path> pathList = null;
		try {

			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Path> query = session.createQuery("from Path");
			pathList = query.getResultList();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}

		return pathList.size() > 0 ? pathList : null;
	}
}
