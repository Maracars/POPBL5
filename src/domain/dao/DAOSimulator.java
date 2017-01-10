package domain.dao;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;

import hibernate.HibernateConnection;

// TODO: Auto-generated Javadoc
/**
 * The Class DAOSimulator.
 */
public class DAOSimulator {
	
	/** The Constant SECOND_TO_MIN. */
	private static final int SECOND_TO_MIN = 60;
	
	/** The Constant MILIS_TO_SECOND. */
	private static final int MILIS_TO_SECOND = 1000;
	
	/** The Constant MIN_TO_HOURS. */
	private static final int MIN_TO_HOURS = 60;
	
	/** The Constant HOURS_TO_DAY. */
	private static final int HOURS_TO_DAY = 24;
	
	/** The Constant WEEK_MARGIN. */
	private static final int WEEK_MARGIN = 7 + 1;
	
	/** The Constant MILIS_TO_DAYS. */
	private static final int MILIS_TO_DAYS = MILIS_TO_SECOND * SECOND_TO_MIN * MIN_TO_HOURS * HOURS_TO_DAY;
	
	/** The Constant PARAMETER_AIRPORT_ID. */
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	
	/** The Constant PARAMETER_MARGIN_WEEK. */
	private static final String PARAMETER_MARGIN_WEEK = "marginWeek";
	
	/** The Constant QUERY_COUNT_FLIGHTS_IN_WEEK. */
	private static final String QUERY_COUNT_FLIGHTS_IN_WEEK = "select COUNT(f) from Flight as f "
			+ "where (f.expectedDepartureDate BETWEEN current_date and :" + PARAMETER_MARGIN_WEEK
			+ " and f.route.departureTerminal.airport.id = :" + PARAMETER_AIRPORT_ID + " ) "
			+ "or (f.expectedArrivalDate BETWEEN current_date and :" + PARAMETER_MARGIN_WEEK
			+ " and f.route.arrivalTerminal.airport.id = :" + PARAMETER_AIRPORT_ID + " )";
	
	/** The session. */
	private static Session session;

	/**
	 * Gets the number of flights in A week from airport.
	 *
	 * @param airportId the airport id
	 * @return the number of flights in A week from airport
	 */
	public static Long getNumberOfFlightsInAWeekFromAirport(int airportId) {

		Long numFlights = null;
		Date soon = new Date();

		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_COUNT_FLIGHTS_IN_WEEK);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			query.setParameter(PARAMETER_MARGIN_WEEK, 
					new Date(soon.getTime() + (MILIS_TO_DAYS * WEEK_MARGIN)));
			numFlights = (Long) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return numFlights;
	}

	/**
	 * Gets the correct date from schedule.
	 *
	 * @param planeId the plane id
	 * @param airportId the airport id
	 * @return the correct date from schedule
	 */
	public static Date getCorrectDateFromSchedule(int planeId, int airportId) {

		Date date = null;

		try {

			session = HibernateConnection.getSessionFactory().openSession();

			StoredProcedureQuery query = session.createStoredProcedureQuery("selectDate")
					.registerStoredProcedureParameter("planeId", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("airportId", Integer.class, ParameterMode.IN)
					.registerStoredProcedureParameter("correctDate", Timestamp.class, ParameterMode.OUT)
					.setParameter("planeId", planeId).setParameter("airportId", airportId);

			query.execute();
			date = (Timestamp) query.getOutputParameterValue("correctDate");

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return date;
	}
}
