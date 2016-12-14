package domain.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.PlaneModel;
import hibernate.HibernateConnection;

public class DAOPlaneModel {

	private static final String QUERY_GET_PLANEMODEL = "from PlaneModel";

	private static Session session;

	@SuppressWarnings("unchecked")
	public static PlaneModel getRandomPlaneModel() {
		List<PlaneModel> planeModelList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_GET_PLANEMODEL);

			planeModelList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		// TODO random aukeratu, hemen edo flightcreatorren
		return planeModelList.get(0);
	}

}
