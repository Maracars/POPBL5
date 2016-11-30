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
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.persist(planeModel);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		} finally {
			

		}

		return result;

	}

	public static boolean deletePlaneModel(PlaneModel planeMaker) {
		boolean result = true;
		try {
			
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.delete(planeMaker);
			session.getTransaction().commit();
		} catch (Exception e) {
			session.getTransaction().rollback();
			result = false;
		} finally {
			

		}

		return result;
	}

	public static List<PlaneModel> loadAllPlaneModels() {
		List<PlaneModel> PlaneModelList = null;
		try {
			
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<PlaneModel> query = session.createQuery("from PlaneModel");
			PlaneModelList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			

		}

		return PlaneModelList;
	}

}
