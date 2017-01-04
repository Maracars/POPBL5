package domain.dao;

import java.util.List;
import java.util.function.Predicate;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Path;
import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
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
	public static List<Path> loadAllFreePaths() {
		List<Path> pathList = null;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<Path> query = session.createQuery("from Path");
			pathList = query.getResultList();

			Predicate<Path> personPredicate = p -> p.getLaneList().contains(false);
			pathList.removeIf(personPredicate); // Listan falsen bat baldin
												// badago
												// borrau eitten da listatik

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return pathList.size() > 0 ? pathList : null;
	}
}
