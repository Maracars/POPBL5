package domain.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;

import domain.model.Flight;
import domain.model.Gate;
import domain.model.users.Passenger;
import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOFlight.
 */
public class DAOFlight {
	
	/** The Constant PARAMETER_AIRLINE_USERNAME. */
	private static final String PARAMETER_AIRLINE_USERNAME = "airlineUsername";
	
	/** The Constant LOAD_AIRLINE_FLIGHTS. */
	private static final String LOAD_AIRLINE_FLIGHTS = "from Flight as f where f.plane.airline.username = :"
			+ PARAMETER_AIRLINE_USERNAME;

	private static final String LOAD_TABLE_FLIGHTS = "from Flight as f order by f.";
	
	private static final String LOAD_WEEK_FLIGHTS = "from Flight as f where DATE(f.expectedArrivalDate) = "
			+ "current_date or DATE(f.expectedDepartureDate) = current_date" ;
	
	
	/** The session. */
	private static Session session;

	/**
	 * Sets the null airline flights.
	 *
	 * @param airlineUsername the airline username
	 * @return true, if successful
	 */
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
	
	
	@SuppressWarnings("unchecked")
	public static List<Flight> loadFlightsForTable(String orderCol, String orderDir, int start, int length){
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(LOAD_TABLE_FLIGHTS + orderCol + " " + orderDir);
			if (query.getResultList().size() > 0) {
				query.setFirstResult(start);
				query.setMaxResults(length);
				flightList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return flightList;
	}

	/**
	 * Sets the null passenger flights.
	 *
	 * @param passengerUsername the passenger username
	 * @return true, if successful
	 */
	@SuppressWarnings("unchecked")
	public static boolean setNullPassengerFlights(String passengerUsername) {
		Boolean result = true;
		List<Flight> flightList = new ArrayList<>();
		try {

			
			session = HibernateConnection.getSessionFactory().openSession();
			session.beginTransaction();
			TypedQuery<Flight> query = session.createQuery("from Flight");
			flightList = query.getResultList();
			for (Flight flight : flightList) {

				if (flight.getPassengerList() != null) {
					for (Passenger passenger : flight.getPassengerList()) {
						if (passenger.getUsername().equals(passengerUsername)) {
							flight.getPassengerList().remove(passenger);
						}
						session.saveOrUpdate(flight);
						break;
					}

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
	
	@SuppressWarnings("unchecked")
	public static List<Flight> loadOneWeekFlights(){
		List<Flight> flightList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(LOAD_WEEK_FLIGHTS);
			if (query.getResultList().size() > 0) {
				flightList = query.getResultList();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return flightList;
	}

}
