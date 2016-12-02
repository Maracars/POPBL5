package domain.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 * @author joanes
 *
 */
@Entity
public class Airline extends User {
	@Id
	@GeneratedValue
	Integer id;

	String name;

	@ManyToMany(cascade = CascadeType.ALL)
	Collection<Route> routesList = new ArrayList<>();

	public Collection<Route> getRoutesList() {
		return routesList;
	}

	public void setRoutesList(Collection<Route> routesList) {
		this.routesList = routesList;
	}

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

	// TODO hemen finkatzeko dare kanpuak

}
