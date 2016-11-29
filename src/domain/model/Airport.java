package domain.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Airport {

	@Id
	@GeneratedValue
	Integer id;

	String name;

	Integer maxFlights;

	// TODO tagak jarri
	City city;

	Collection<Terminal> TerminalList = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMaxFlights() {
		return maxFlights;
	}

	public Collection<Terminal> getTerminalList() {
		return TerminalList;
	}

	public void setTerminalList(Collection<Terminal> terminalList) {
		TerminalList = terminalList;
	}

	public void setMaxFlights(Integer maxFlights) {
		this.maxFlights = maxFlights;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
