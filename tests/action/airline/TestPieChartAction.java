package action.airline;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Test;

import action.airline.PieChartAction.FlightView;
import domain.dao.HibernateGeneric;
import domain.model.Flight;

public class TestPieChartAction {

	private static final String _0 = "0";
	private static final String JSON_DATA_EMPTY_ERROR = "Error, JSON data is not empty";
	private static final String JSON_DATA_NOT_OK_ERROR = "JSON data was not parsed OK";
	private static final int JSON_DATA_EMPTY_LENGTH = 4;

	@Test
	public void testJSONDataEmptyLengthIsOk() {
		HibernateGeneric.deleteAllObjects(new Flight());
		PieChartAction pcAc = new PieChartAction();

		try {
			pcAc.execute();
		} catch (Exception e) {

			e.printStackTrace();
		}

		assertEquals(JSON_DATA_EMPTY_ERROR, JSON_DATA_EMPTY_LENGTH, pcAc.getData().size());
	}

	@Test
	public void testJSONDataEmptyisOk() {
		HibernateGeneric.deleteAllObjects(new Flight());
		List<FlightView> lFv = new ArrayList<FlightView>();

		PieChartAction pcAc = new PieChartAction();

		FlightView fv = pcAc.newFlightView("Arrival On Time", _0);
		lFv.add(fv);
		fv = pcAc.newFlightView("Departure On Time", _0);
		lFv.add(fv);
		fv = pcAc.newFlightView("Arrival Delay", _0);
		lFv.add(fv);
		fv = pcAc.newFlightView("Departure Delay", _0);
		lFv.add(fv);

		try {
			pcAc.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 0; i < pcAc.getData().size(); i++) {
			assertThat(JSON_DATA_NOT_OK_ERROR, lFv.get(i),
					SamePropertyValuesAs.samePropertyValuesAs(pcAc.getData().get(i)));
		}
	}
}
