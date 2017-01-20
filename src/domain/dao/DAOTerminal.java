package domain.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Terminal;
import hibernate.HibernateConnection;

public class DAOTerminal {
	
	private static Session session;
	
	@SuppressWarnings("unchecked")
	public static List<Terminal> loadTerminalsFromAirport(int airportID) {
		List<Terminal> list = new ArrayList<>();
		try {
			session = HibernateConnection.getSession();
			Query query = session.createQuery("from Terminal as t where t.airport.id = :airportId");
			query.setParameter("airportId", airportID);
			list = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			HibernateConnection.closeSession(session);
		}
		
		return list;

	}
	

}
