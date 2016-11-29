package action.user;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import domain.dao.DAOUser;
import domain.model.Passenger;
import domain.model.User;

public class RegisterAction extends ActionSupport implements ModelDriven<User>{

	private static final String TOO_YOUNG = "You are too young m8!";
	private static final String BIRTH_DATE = "birthDate";
	private static final String PASSWORD_BLANK = "Password cannot be blank";
	private static final String PASSWORD = "password";
	private static final String USERNAME_BLANK = "Username cannot be blank";
	private static final String USERNAME = "username";
	private static final long serialVersionUID = 1L;

	User user = new User();
	String type;
	
	@Override
	public void validate() {
		if(user.getPassword().isEmpty())
			addFieldError(PASSWORD, PASSWORD_BLANK);
		if(user.getUsername().isEmpty())
			addFieldError(USERNAME, USERNAME_BLANK);
		if(user.getUsername().isEmpty())
			addFieldError(USERNAME, USERNAME_BLANK);
		LocalDate birthdate = user.getBirthDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		if(Period.between(birthdate , LocalDate.now()).getYears() < 18)
			addFieldError(BIRTH_DATE, TOO_YOUNG);
	}
	
	@Override
	public String execute() throws Exception {
		type = User.PASSENGER;
		switch(type){
		case "passenger":
			user = (Passenger) user;
			break;
		//TODO mas tipos
		}
		
		return DAOUser.insertUser(user) ? SUCCESS : ERROR;
	}
	
	@Override
	public User getModel() {
		return user;
	}
	
	

}
