package domain.dao;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.hibernate.Session;

import hibernate.HibernateConnection;

public class DAOSimulator {
	private static final int SECOND_TO_MIN = 60;
	private static final int MILIS_TO_SECOND = 1000;
	private static final int MIN_TO_HOURS = 60;
	private static final int HOURS_TO_DAY = 24;
	private static final int WEEK_MARGIN = 7+1;
	private static final int MILIS_TO_DAYS = MILIS_TO_SECOND * SECOND_TO_MIN * MIN_TO_HOURS * HOURS_TO_DAY;
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	private static final String PARAMETER_MARGIN_WEEK = "marginWeek";
	private static final String QUERY_COUNT_FLIGHTS_IN_WEEK = "select COUNT(f) from Flight as f "
			+ "where (f.expectedDepartureDate BETWEEN current_date and :" + PARAMETER_MARGIN_WEEK
			+ " and f.route.departureGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID + " ) "
			+ "or (f.expectedArrivalDate BETWEEN current_date and :" + PARAMETER_MARGIN_WEEK
			+ " and f.route.arrivalGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID + " )";
	private static Session session;

	public static Long getNumberOfFlightsInAWeekFromAirport(int airportId) {

		Long numFlights = null;
		Date soon = new Date();

		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_COUNT_FLIGHTS_IN_WEEK);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			query.setParameter(PARAMETER_MARGIN_WEEK, new Date(soon.getTime() + (MILIS_TO_DAYS * WEEK_MARGIN)));
			numFlights = (Long) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return numFlights;
	}

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
		date =  (Timestamp) query.getOutputParameterValue("correctDate");
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		

		return date;
	}
}
