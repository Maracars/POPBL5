package action.user;

import domain.dao.HibernateGeneric;
import domain.model.Address;
import domain.model.users.Admin;
import domain.model.users.Airline;

// TODO: Auto-generated Javadoc
/**
 * The Class RegisterAirlineAction.
 */
public class RegisterAirlineAction extends RegisterAction {

	/** The Constant NAME_FIELD. */
	private static final String NAME_FIELD = "name";
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The Constant BLANK. */
	private static final String BLANK = "user.blank";
	
	/** The Constant CITY. */
	private static final String CITY = "address.city";
	
	/** The Constant COUNTRY. */
	private static final String COUNTRY = "address.country";
	
	/** The Constant REGION. */
	private static final String REGION = "address.region";
	
	/** The Constant STREETANDNUMBER. */
	private static final String STREETANDNUMBER = "address.streetAndNumber";
	
	/** The Constant POSTCODE. */
	private static final String POSTCODE = "address.postCode";
	
	/** The name. */
	String name;
	
	/** The address. */
	Address address = new Address();
	
	/* (non-Javadoc)
	 * @see action.user.RegisterAction#userSpecificValidate()
	 */
	@Override
	public void userSpecificValidate() {
		allowedUsers.add(Admin.class);
		
		user = new Airline(user);
		((Airline) user).setAddress(address);
		((Airline) user).setName(name);

		validateAddress();
		validateNameAndSecondName();

	}

	/**
	 * Validate name and second name.
	 */
	private void validateNameAndSecondName() {
		if (name == null || name.equals(""))
			addFieldError(NAME_FIELD, BLANK);
	}

	/**
	 * Validate address.
	 */
	private void validateAddress() {
		if (address.getCity() == null || address.getCity().equals(""))
			addFieldError(CITY, getText(BLANK));
		if (address.getCountry() == null || address.getCountry().equals(""))
			addFieldError(COUNTRY, getText(BLANK));
		if (address.getPostCode() == null || address.getPostCode().equals(""))
			addFieldError(POSTCODE, getText(BLANK));
		if (address.getRegion() == null || address.getRegion().equals(""))
			addFieldError(REGION, getText(BLANK));
		if (address.getStreetAndNumber() == null || address.getStreetAndNumber().equals(""))
			addFieldError(STREETANDNUMBER, getText(BLANK));
	}
	
	/* (non-Javadoc)
	 * @see action.user.RegisterAction#userSpecificInsert()
	 */
	@Override
	public String userSpecificInsert() {
		String ret = SUCCESS;
		ret = HibernateGeneric.saveObject(((Airline)user).getAddress()) ? SUCCESS : ERROR;
		return ret;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the address.
	 *
	 * @return the address
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * Sets the address.
	 *
	 * @param address the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
}
