package domain.dao;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import domain.model.Route;
import hibernate.HibernateConnection;

public class DAORoute {
	
	private static final String PARAMETER_AIRPORT_ID = "airportId";
	private static final String QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID = "from Route as r "
			+ "where r.arrivalGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID;
	private static final String QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID = "from Route as r "
			+ "where r.departureGate.terminal.airport.id = :" + PARAMETER_AIRPORT_ID;

	private static Session session;

	@SuppressWarnings("unchecked")
	public static List<Route> getRandomArrivalRouteFromAirport(int airportId) {
		// TODO hau ondo dabil, kontua da oindio randomena daola gehitzeko, eta
		// ez dau lista bat bueltauko
		List<Route> routeList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_ARRIVAL_ROUTES_FROM_AIRPORTID);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return routeList;
	}

	@SuppressWarnings("unchecked")
	public static Route selectDepartureRouteFromAirport(int airportId) {

		List<Route> routeList = null;
		try {
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(QUERY_DEPARTURE_ROUTES_FROM_AIRPORTID);
			query.setParameter(PARAMETER_AIRPORT_ID, airportId);
			routeList = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return routeList.get(0);
	}

}
