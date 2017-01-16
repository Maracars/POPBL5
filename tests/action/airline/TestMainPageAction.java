package action.airline;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Test;

import action.airline.MainPageAction.FlightView;
import domain.dao.HibernateGeneric;
import domain.dao.Initializer;
import domain.model.Flight;

public class TestMainPageAction {

	private static final String JSON_DATA_EMPTY_ERROR = "Error, JSON data is not empty";
	private static final String JSON_DATA_NOT_EMPTY_ERROR = "Error, JSON data is empty";
	private static final String JSON_DATA_NOT_OK_ERROR = "JSON data was not parsed OK";
	
	
	@Test
	public void testJSONDataIsEmpty(){
		HibernateGeneric.deleteAllObjects(new Flight());

		MainPageAction mpAc = new MainPageAction();
		try {
			mpAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNull(JSON_DATA_EMPTY_ERROR, mpAc.getData());
	}


	@Test
	public void testJSONDataIsNotEmpty(){
		HibernateGeneric.deleteAllObjects(new Flight());

		Flight flight = Initializer.initCompleteFlight();
		HibernateGeneric.saveObject(flight);
		
		MainPageAction mpAc = new MainPageAction();

		try {
			mpAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertNotNull(JSON_DATA_NOT_EMPTY_ERROR, mpAc.getData());
	}

	@Test
	public void testJSONDataIsOK(){
		HibernateGeneric.deleteAllObjects(new Flight());
		
		Flight flight = Initializer.initCompleteFlight();
		
		HibernateGeneric.saveObject(flight);
		
		MainPageAction mpAc = new MainPageAction();
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		List<FlightView> lFv = new ArrayList<FlightView>();
		
		String routeName = flight.getRoute().getDepartureTerminal().getName() + "-" + flight.getRoute().getArrivalTerminal().getName();
		
		FlightView fv = mpAc.newFlightView(flight.getPlane().getSerial(), String.valueOf(flight.getId()), routeName, 
				sdf.format(flight.getExpectedDepartureDate()), sdf.format(flight.getExpectedArrivalDate()));
		
		lFv.add(fv);
		
		try {
			mpAc.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(int i = 0; i < mpAc.getData().size(); i++){
			assertThat(JSON_DATA_NOT_OK_ERROR, lFv.get(i), SamePropertyValuesAs.samePropertyValuesAs(mpAc.getData().get(i)));
		}

	}

}
