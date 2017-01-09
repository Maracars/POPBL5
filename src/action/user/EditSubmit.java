package action.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import domain.dao.DAOUser;
import domain.dao.HibernateGeneric;
import domain.model.users.Admin;
import domain.model.users.Airline;
import domain.model.users.Controller;
import domain.model.users.Mantainance;
import domain.model.users.Passenger;
import domain.model.users.User;

public class EditSubmit extends ActionSupport {
	private static final long serialVersionUID = 1L;
	private static final String NAME_FIELD = "user.name";
	private static final String SECONDNAME = "user.secondName";
	private static final String EMAIL_BLANK = "user.emailBlank";
	private static final String EMAIL = "user.email";
	private static final String USERNAME = "user.username";
	private static final String USERNAME_BLANK = "user.usernameBlank";
	private static final String CITY = "user.address.city";
	private static final String COUNTRY = "user.address.country";
	private static final String REGION = "user.address.region";
	private static final String STREETANDNUMBER = "user.address.streetAndNumber";
	private static final String POSTCODE = "user.address.postCode";
	private static final String BLANK = "user.blank";
	private static final String TOO_YOUNG = "user.underAge";
	private static final String BIRTH_DATE = "birthdate";
	private static final int MIN_YEARS = 18;
	private static final String INCORRECT_FORMAT = "user.incorrectDateFormat";

	String username;
	Passenger user = new Passenger();
	String type;
	String url;
	String birthdate;

	@Override
	public void validate() {
		if (user.getEmail() == null || user.getEmail().isEmpty())
			addFieldError(EMAIL, getText(EMAIL_BLANK));
		if (user.getUsername() == null || user.getUsername().isEmpty())
			addFieldError(USERNAME, getText(USERNAME_BLANK));
		if (((Passenger) user).getAddress().getCity() == null || ((Passenger) user).getAddress().getCity().equals(""))
			addFieldError(CITY, getText(BLANK));
		if (((Passenger) user).getAddress().getCountry() == null
				|| ((Passenger) user).getAddress().getCountry().equals(""))
			addFieldError(COUNTRY, getText(BLANK));
		if (((Passenger) user).getAddress().getPostCode() == null
				|| ((Passenger) user).getAddress().getPostCode().equals(""))
			addFieldError(POSTCODE, getText(BLANK));
		if (((Passenger) user).getAddress().getRegion() == null
				|| ((Passenger) user).getAddress().getRegion().equals(""))
			addFieldError(REGION, getText(BLANK));
		if (((Passenger) user).getAddress().getStreetAndNumber() == null
				|| ((Passenger) user).getAddress().getStreetAndNumber().equals(""))
			addFieldError(STREETANDNUMBER, getText(BLANK));
		if (((Passenger) user).getName() == null || ((Passenger) user).getName().equals(""))
			addFieldError(NAME_FIELD, getText(BLANK));
		if (!type.equals("airline")){
			if (((Passenger) user).getSecondName() == null || ((Passenger) user).getSecondName().equals(""))
				addFieldError(SECONDNAME, getText(BLANK));
			validateBirthdate();
		}
		

	}

	private void validateBirthdate() {
		if (birthdate == null || birthdate.isEmpty())
			addFieldError(BIRTH_DATE, getText(BLANK));
		else {
			SimpleDateFormat df = new SimpleDateFormat(getText("global.dateFormat"));
			try {
				((Passenger) user).setBirthDate(df.parse(birthdate));
				LocalDate birthdate = ((Passenger) user).getBirthDate().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();
				if (Period.between(birthdate, LocalDate.now()).getYears() < MIN_YEARS)
					addFieldError(BIRTH_DATE, getText(TOO_YOUNG) + " " + MIN_YEARS);
			} catch (ParseException e) {
				addFieldError(BIRTH_DATE, getText(INCORRECT_FORMAT));
			}
		}
	}

	@Override
	public String execute() throws Exception {
		String ret = SUCCESS;
		User newUser = null;
		User oldUser = DAOUser.getUser(username);

		switch (type) {
		case "admin":
			newUser = new Admin();
			Admin aux = (Admin) newUser;
			aux.setPassword(oldUser.getPassword());
			aux.setUsername(user.getUsername());
			aux.setAddress(user.getAddress());
			aux.getAddress().setId(((Admin) oldUser).getId());
			aux.setName(user.getName());
			aux.setEmail(user.getEmail());
			aux.setSecondName(user.getSecondName());
			aux.setId(oldUser.getId());
			aux.setBirthDate(user.getBirthDate());
			newUser = aux;
			HibernateGeneric.saveOrUpdateObject(((Admin) newUser).getAddress());
			break;
		case "controller":
			newUser = new Controller();
			Controller aux1 = (Controller) newUser;
			aux1.setPassword(oldUser.getPassword());
			aux1.setUsername(user.getUsername());
			aux1.setAddress(user.getAddress());
			aux1.getAddress().setId(((Controller) oldUser).getId());
			aux1.setName(user.getName());
			aux1.setEmail(user.getEmail());
			aux1.setSecondName(user.getSecondName());
			aux1.setId(oldUser.getId());
			aux1.setBirthDate(user.getBirthDate());
			newUser = aux1;
			HibernateGeneric.saveOrUpdateObject(((Controller) newUser).getAddress());
			break;
		case "airline":
			newUser = new Airline();
			Airline aux2 = (Airline) newUser;
			aux2.setPassword(oldUser.getPassword());
			aux2.setUsername(user.getUsername());
			aux2.setAddress(user.getAddress());
			aux2.getAddress().setId(((Airline) oldUser).getId());
			aux2.setName(user.getName());
			aux2.setEmail(user.getEmail());
			aux2.setId(oldUser.getId());
			newUser = aux2;
			HibernateGeneric.saveOrUpdateObject(((Airline) newUser).getAddress());
			break;
		case "mantainance":
			newUser = new Mantainance();
			Controller aux3 = (Controller) newUser;
			aux3.setPassword(oldUser.getPassword());
			aux3.setUsername(user.getUsername());
			aux3.setAddress(user.getAddress());
			aux3.getAddress().setId(((Mantainance) oldUser).getId());
			aux3.setName(user.getName());
			aux3.setEmail(user.getEmail());
			aux3.setSecondName(user.getSecondName());
			aux3.setId(oldUser.getId());
			aux3.setBirthDate(user.getBirthDate());
			newUser = aux3;
			HibernateGeneric.saveOrUpdateObject(((Mantainance) newUser).getAddress());
			break;
		case "passenger":
			newUser = new Passenger();
			Passenger aux4 = (Passenger) newUser;
			aux4.setPassword(oldUser.getPassword());
			aux4.setUsername(user.getUsername());
			aux4.setAddress(user.getAddress());
			aux4.getAddress().setId(((Passenger) oldUser).getId());
			aux4.setName(user.getName());
			aux4.setEmail(user.getEmail());
			aux4.setSecondName(user.getSecondName());
			aux4.setId(oldUser.getId());
			aux4.setBirthDate(user.getBirthDate());
			newUser = aux4;
			HibernateGeneric.saveOrUpdateObject(((Passenger) newUser).getAddress());
			break;
		default:
			break;
		}

		HibernateGeneric.saveOrUpdateObject(newUser);

		if (((User) ActionContext.getContext().getSession().get("user")).getId().equals(oldUser.getId())) {
			ActionContext.getContext().getSession().remove("user");
			ActionContext.getContext().getSession().put("user", newUser);
		}

		if (getFieldErrors().isEmpty()) {
			url = (String) ActionContext.getContext().getSession().get("lastPage");
			ActionContext.getContext().getSession().remove("lastPage");
		}

		return ret;
	}

	public Passenger getUser() {
		return user;
	}

	public void setUser(Passenger user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

}
