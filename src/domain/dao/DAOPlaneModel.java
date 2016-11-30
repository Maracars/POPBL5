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
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.persist(planeModel);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		} finally {
			HibernateConnection.after();

		}

		return result;

	}

	public static boolean deletePlaneModel(PlaneModel planeMaker) {
		boolean result = true;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.delete(planeMaker);
		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		} finally {
			HibernateConnection.after();

		}

		return result;
	}

	public static List<PlaneModel> loadAllPlaneModels() {
		List<PlaneModel> PlaneModelList = null;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<PlaneModel> query = session.createQuery("from PlaneModel");
			PlaneModelList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			HibernateConnection.after();

		}

		return PlaneModelList;
	}

}
