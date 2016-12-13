package action.user;

import domain.dao.HibernateGeneric;
import domain.model.Address;
import domain.model.users.Admin;
import domain.model.users.Airline;

public class RegisterAirlineAction extends RegisterAction {

	private static final String NAME_FIELD = "name";
	private static final long serialVersionUID = 1L;
	private static final String BLANK = "user.blank";
	private static final String CITY = "address.city";
	private static final String COUNTRY = "address.country";
	private static final String REGION = "address.region";
	private static final String STREETANDNUMBER = "address.streetAndNumber";
	private static final String POSTCODE = "address.postCode";
	
	String name;
	Address address = new Address();
	
	@Override
	public void userSpecificValidate() {
		allowedUsers.add(Admin.class);
		
		user = new Airline(user);
		((Airline) user).setAddress(address);
		((Airline) user).setName(name);

		validateAddress();
		validateNameAndSecondName();

	}

	private void validateNameAndSecondName() {
		if (name == null || name.equals(""))
			addFieldError(NAME_FIELD, BLANK);
	}

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
	
	@Override
	public String userSpecificInsert() {
		String ret = SUCCESS;
		ret = HibernateGeneric.saveOrUpdateObject(((Airline)user).getAddress()) ? SUCCESS : ERROR;
		return ret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	
	
}
