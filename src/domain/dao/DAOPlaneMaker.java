package domain.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import domain.model.PlaneMaker;
import hibernate.HibernateConnection;

public class DAOPlaneMaker {
	private static Session session;

	public static boolean insertPlaneMaker(PlaneMaker planeMaker) {
		boolean result = true;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			session.getTransaction().begin();
			session.save(planeMaker);
			session.getTransaction().commit();

		} catch (Exception e) {
			session.getTransaction().rollback();

			result = false;
		}

		return result;

	}

	public static boolean deletePlaneMaker(PlaneMaker planeMaker) {
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

	public static List<PlaneMaker> loadAllPlaneMakers() {
		List<PlaneMaker> planeMakerList = null;
		try {

			session = HibernateConnection.getSessionFactory().openSession();
			@SuppressWarnings("unchecked")
			TypedQuery<PlaneMaker> query = session.createQuery("from PlaneMaker");
			planeMakerList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return planeMakerList;
	}

}
