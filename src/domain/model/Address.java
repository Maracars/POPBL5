package domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// TODO: Auto-generated Javadoc
/**
 * The Class Address.
 */
@Entity
public class Address {
	
	/** The id. */
	@Id
	@GeneratedValue
	int id;
	
	/** The country. */
	@Column(nullable = false)
	String country;
	
	/** The region. */
	@Column(nullable = false)
	String region;
	
	/** The city. */
	@Column(nullable = false)
	String city;
	
	/** The street and number. */
	@Column(nullable = false)
	String streetAndNumber;	
	
	/** The post code. */
	@Column(nullable = false)
	String postCode;
	
	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * Gets the country.
	 *
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	
	/**
	 * Sets the country.
	 *
	 * @param country the new country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	
	/**
	 * Gets the region.
	 *
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}
	
	/**
	 * Sets the region.
	 *
	 * @param region the new region
	 */
	public void setRegion(String region) {
		this.region = region;
	}
	
	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}
	
	/**
	 * Sets the city.
	 *
	 * @param city the new city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	
	/**
	 * Gets the street and number.
	 *
	 * @return the street and number
	 */
	public String getStreetAndNumber() {
		return streetAndNumber;
	}
	
	/**
	 * Sets the street and number.
	 *
	 * @param streetAndNumber the new street and number
	 */
	public void setStreetAndNumber(String streetAndNumber) {
		this.streetAndNumber = streetAndNumber;
	}
	
	/**
	 * Gets the post code.
	 *
	 * @return the post code
	 */
	public String getPostCode() {
		return postCode;
	}
	
	/**
	 * Sets the post code.
	 *
	 * @param postCode the new post code
	 */
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	
	
	
	
}
