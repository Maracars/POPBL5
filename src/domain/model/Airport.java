package domain.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Airport{

	@Id
	@GeneratedValue
	Integer id;

	String name;

	Integer maxFlights;

	
	@ManyToOne(optional = false)
	Address address;

	@OneToMany//(cascade = CascadeType.PERSIST)
	@NotNull
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	

}
