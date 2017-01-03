package domain.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Lane;
import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOLane.
 */
public class DAOLane {

	/** The Constant PRINCIPAL. */
	private static final String PRINCIPAL = "PRINCIPAL";
	
	/** The Constant PARAMETER_AIRPORT_ID. */
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	
	/** The Constant PARAMETER_TYPE. */
	private static final String PARAMETER_TYPE = "type";
	
	/** The Constant QUERY_FREE_LANES. */
	private static final String QUERY_FREE_LANES = "from Lane as l "
			+ "where l.type = :type and l.status is true and l.airport.id = :" + PARAMETER_AIRPORT_ID;
	
	/** The session. */
	private static Session session;

	/**
	 * Gets the free lanes.
	 *
	 * @param airportId the airport id
	 * @return the free lanes
	 */
	@SuppressWarnings("unchecked")
	public static List<Lane> getFreeLanes(int airportId) {
		List<Lane> laneList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_FREE_LANES);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			query.setParameter(PARAMETER_TYPE, PRINCIPAL);
			if (query.getResultList().size() > 0) {
				laneList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return laneList;
	}

	/**
	 * Update lane.
	 *
	 * @param lane the lane
	 * @return true, if successful
	 */
	public static boolean updateLane(Lane lane) {
		boolean result = true;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.update(lane);
			session.getTransaction().commit();

		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			result = false;
		} finally {
			session.close();
		}

		return result;

	}
}
