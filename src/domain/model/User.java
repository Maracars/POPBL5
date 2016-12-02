package domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "AppUser")
public class User {

	public final static String PASSENGER = "passenger";

	@Id
	@GenericGenerator(name = "inc-gen", strategy = "increment")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "inc-gen")
	Integer id;

	@Column(nullable = false)
	String password;

	@Column(nullable = false, unique = true)
	String username;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	Date birthDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

}
