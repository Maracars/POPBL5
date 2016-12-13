package domain.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Flight;
import domain.model.users.Passenger;
import hibernate.HibernateConnection;

public class DAOFlight {
	private static final String PARAMETER_AIRLINE_USERNAME = "airlineUsername";
	private static final String LOAD_AIRLINE_FLIGHTS = "from Flight as f where f.plane.airline.username = :"
			+ PARAMETER_AIRLINE_USERNAME;
	private static Session session;

	@SuppressWarnings("unchecked")
	public static boolean setNullAirlineFlights(String airlineUsername) {
		Boolean result = true;
		List<Flight> planeList = new ArrayList<>();
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			session.beginTransaction();
			Query query = session.createQuery(LOAD_AIRLINE_FLIGHTS);
			query.setParameter(PARAMETER_AIRLINE_USERNAME, airlineUsername);
			planeList = query.getResultList();
			for (Flight plane : planeList) {
				if (plane.getPassengerList() != null) {

					plane.setPassengerList(new ArrayList<Passenger>());
					session.saveOrUpdate(plane);
				}

			}
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
