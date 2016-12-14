package action.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import domain.dao.HibernateGeneric;
import domain.model.Address;
import domain.model.users.Admin;
import domain.model.users.Mantainance;

public class RegisterMantainanceAction extends RegisterAction{

	private static final long serialVersionUID = 1L;
	private static final String NAME_FIELD = "name";
	private static final String SECONDNAME = "secondName";
	private static final String INCORRECT_FORMAT = "user.incorrectDateFormat";
	private static final String TOO_YOUNG = "user.underAge";
	private static final String BIRTH_DATE = "birthdate";
	private static final String BLANK = "action.user.blank";
	private static final int MIN_YEARS = 18;
	private static final String CITY = "address.city";
	private static final String COUNTRY = "address.country";
	private static final String REGION = "address.region";
	private static final String STREETANDNUMBER = "address.streetAndNumber";
	private static final String POSTCODE = "address.postCode";
	
	String birthdate;
	String name;
	String secondName;
	Address address = new Address();
	
	
	
	@Override
	void userSpecificValidate() {
		allowedUsers.add(Admin.class);
		
		user = new Mantainance(user);
		((Mantainance) user).setAddress(address);
		((Mantainance) user).setName(name);
		((Mantainance) user).setSecondName(secondName);		

		validateBirthdate();
		validateAddress();
		validateNameAndSecondName();
	}

	private void validateNameAndSecondName() {
		if (name == null || name.equals(""))
			addFieldError(NAME_FIELD, getText(BLANK));
		if (secondName == null || secondName.equals(""))
			addFieldError(SECONDNAME, getText(BLANK));
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

	private void validateBirthdate() {
		if (birthdate == null || birthdate.isEmpty())
			addFieldError(BIRTH_DATE, getText(BLANK));
		else {
			SimpleDateFormat df = new SimpleDateFormat(getText("global.dateFormat"));
			try {
				((Mantainance) user).setBirthDate(df.parse(birthdate));
				LocalDate birthdate = ((Mantainance) user).getBirthDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				if (Period.between(birthdate, LocalDate.now()).getYears() < MIN_YEARS)
					addFieldError(BIRTH_DATE, getText(TOO_YOUNG) + " " + MIN_YEARS);
			} catch (ParseException e) {
				addFieldError(BIRTH_DATE, getText(INCORRECT_FORMAT));
			}
		}
	}
	
	@Override
	public String userSpecificInsert() {
		String ret = SUCCESS;
		ret = HibernateGeneric.saveOrUpdateObject(((Mantainance)user).getAddress()) ? SUCCESS : ERROR;
		return ret;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
}
