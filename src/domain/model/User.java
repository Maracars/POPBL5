package domain.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class User {

	@Id
	@GeneratedValue
	Integer id;

	String password;

	String username;

	// TODO hemen enkripzinua beiratu

	// TODO hemen erabiltzaile mota zela garatu pentsatu
	@Temporal(TemporalType.DATE)
	Date birthDate;

}
