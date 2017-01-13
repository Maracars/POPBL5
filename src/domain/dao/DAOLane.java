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
			+ "where l.status is true and l.airport.id = :" + PARAMETER_AIRPORT_ID;
	
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
			session = HibernateConnection.getSession();
			Query query = session.createQuery(QUERY_FREE_LANES);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			if (query.getResultList().size() > 0) {
				laneList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.closeSession(session);
		}
		return laneList;
	}
}
