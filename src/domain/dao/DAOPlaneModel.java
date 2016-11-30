package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.PlaneModel;
import hibernate.HibernateConnection;

public class DAOPlaneModel {
	private static Session session;

	public static boolean insertPlaneModel(PlaneModel planeModel) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.save(planeModel);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		} finally {
			session.close();
		}

		return result;

	}

	public static boolean deletePlaneModel(PlaneModel planeMaker) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.delete(planeMaker);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		} finally {
			session.close();
		}

		return result;
	}

	public static List<PlaneModel> loadAllPlaneModels() {
		List<PlaneModel> PlaneModelList = null;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<PlaneModel> query = session.createQuery("from PlaneModel");
			PlaneModelList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return PlaneModelList;
	}

}
