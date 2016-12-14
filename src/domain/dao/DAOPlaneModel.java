package domain.dao;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.PlaneModel;
import hibernate.HibernateConnection;

public class DAOPlaneModel {

	private static final String QUERY_GET_PLANEMODEL = "from PlaneModel order by rand()";

	private static Session session;

	public static PlaneModel getRandomPlaneModel() {
		PlaneModel planeModel = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_GET_PLANEMODEL);

			planeModel = (PlaneModel)query.setMaxResults(1).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return planeModel;
	}

}
