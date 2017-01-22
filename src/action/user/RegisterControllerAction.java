package action.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import domain.dao.HibernateGeneric;
import domain.model.Address;
import domain.model.users.Admin;
import domain.model.users.Controller;

/**
 * The Class RegisterControllerAction.
 */
public class RegisterControllerAction extends RegisterAction {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant NAME_FIELD. */
	private static final String NAME_FIELD = "name";

	/** The Constant SECONDNAME. */
	private static final String SECONDNAME = "secondName";

	/** The Constant INCORRECT_FORMAT. */
	private static final String INCORRECT_FORMAT = "user.incorrectDateFormat";

	/** The Constant TOO_YOUNG. */
	private static final String TOO_YOUNG = "user.underAge";

	/** The Constant BIRTH_DATE. */
	private static final String BIRTH_DATE = "birthdate";

	/** The Constant BLANK. */
	private static final String BLANK = "user.blank";

	/** The Constant MIN_YEARS. */
	private static final int MIN_YEARS = 18;

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

	/** The birthdate. */
	String birthdate;

	/** The name. */
	String name;

	/** The second name. */
	String secondName;

	/** The address. */
	Address address = new Address();

	/*
	 * (non-Javadoc)
	 * 
	 * @see action.user.RegisterAction#userSpecificValidate()
	 */
	@Override
	void userSpecificValidate() {
		allowedUsers.add(Admin.class);

		user = new Controller(user);
		((Controller) user).setAddress(address);
		((Controller) user).setName(name);
		((Controller) user).setSecondName(secondName);

		validateBirthdate();
		validateAddress();
		validateNameAndSecondName();
	}

	/**
	 * Validate name and second name.
	 */
	private void validateNameAndSecondName() {
		if (name == null || name.equals(""))
			addFieldError(NAME_FIELD, getText(BLANK));
		if (secondName == null || secondName.equals(""))
			addFieldError(SECONDNAME, getText(BLANK));
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

	/**
	 * Validate birthdate.
	 */
	private void validateBirthdate() {
		if (birthdate == null || birthdate.isEmpty())
			addFieldError(BIRTH_DATE, getText(BLANK));
		else {
			SimpleDateFormat df = new SimpleDateFormat(getText("global.dateFormat"));
			try {
				((Controller) user).setBirthDate(df.parse(birthdate));
				LocalDate birthdate = ((Controller) user).getBirthDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				if (Period.between(birthdate, LocalDate.now()).getYears() < MIN_YEARS)
					addFieldError(BIRTH_DATE, getText(TOO_YOUNG) + " " + MIN_YEARS);
			} catch (ParseException e) {
				addFieldError(BIRTH_DATE, getText(INCORRECT_FORMAT));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see action.user.RegisterAction#userSpecificInsert()
	 */
	@Override
	public String userSpecificInsert() {
		String ret = SUCCESS;
		ret = HibernateGeneric.saveObject(((Controller) user).getAddress()) ? SUCCESS : ERROR;
		return ret;
	}

	/**
	 * Gets the birthdate.
	 *
	 * @return the birthdate
	 */
	public String getBirthdate() {
		return birthdate;
	}

	/**
	 * Sets the birthdate.
	 *
	 * @param birthdate
	 *            the new birthdate
	 */
	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
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
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the second name.
	 *
	 * @return the second name
	 */
	public String getSecondName() {
		return secondName;
	}

	/**
	 * Sets the second name.
	 *
	 * @param secondName
	 *            the new second name
	 */
	public void setSecondName(String secondName) {
		this.secondName = secondName;
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
	 * @param address
	 *            the new address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

}
