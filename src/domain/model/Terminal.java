package domain.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import domain.model.Gate;
@Entity
public class Terminal {
	@Id@GeneratedValue
	Integer id;
	String name;
	@ElementCollection
	@JoinTable(name="TerminalGates",
 				joinColumns= @JoinColumn(name="terminalID"))
 	@GenericGenerator(name="sequence", strategy="sequence")
 
 	@CollectionId(columns = { @Column(name="gateID") }, generator = "sequence", type = @Type(type="long"))
	Collection<Gate> gatesList = new ArrayList<>();
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
	public Collection<Gate> getGatesList() {
		return gatesList;
	}
	public void setGatesList(Collection<Gate> gatesList) {
		this.gatesList = gatesList;
	}
	

}
