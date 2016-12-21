package domain.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Lane;
import hibernate.HibernateConnection;

public class DAOLane {

	private static final String PRINCIPAL = "PRINCIPAL";
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	private static final String PARAMETER_TYPE = "type";
	private static final String QUERY_FREE_LANES = "from Lane as l "
			+ "where l.type = :type and l.status is true and l.airport.id = :" + PARAMETER_AIRPORT_ID;
	private static Session session;

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
