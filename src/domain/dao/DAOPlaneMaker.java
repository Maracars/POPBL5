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
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.getTransaction().begin();
			session.save(planeMaker);
			session.getTransaction().commit();
			HibernateConnection.after();
		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			result = false;
		}

		return result;

	}

	public static boolean deletePlaneMaker(PlaneMaker planeMaker) {
		boolean result = true;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			session.delete(planeMaker);
			HibernateConnection.after();
		} catch (Exception e) {
			session.getTransaction().rollback();
			HibernateConnection.after();
			result = false;
		}

		return result;
	}

	public static List<PlaneMaker> loadAllPlaneMakers() {
		List<PlaneMaker> planeMakerList = null;
		try {
			HibernateConnection.before();
			session = HibernateConnection.getSession();
			@SuppressWarnings("unchecked")
			TypedQuery<PlaneMaker> query = session.createQuery("from PlaneMaker");
			planeMakerList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateConnection.after();

		return planeMakerList;
	}

}
