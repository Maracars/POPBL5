package domain.dao;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import org.junit.Test;

import domain.model.Passenger;

public class TestDaoPassenger {

	private static final String ERROR_INSERT = "Error insert plane maker into database";
	private static final String USERNAME = "joanes";
	private static final String PASSWORD = "1234";
	private static final String ERROR_REMOVING = "Error removing one user from database";

	@Test
	public void testInsertUserWithoutBirthDateAndWithUsernameAndWithPasswordIntoDB() {
		Passenger user = initPassenger(USERNAME, PASSWORD);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertUserWithoutPasswordWithBirthDateAndAndWithUsernameIntoDB() {

		Passenger user = initPassenger(USERNAME, new Date());
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithoutUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		Passenger user = initPassenger(new Date(), PASSWORD);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		Passenger user = initPassenger(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, true, result);

	}

	@Test
	public void testRemoveOneSpecificUser() {

		Passenger user = initPassenger(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);

		HibernateGeneric.insertObject(user);

		boolean result = DAOUser.deleteUserWithUsername(user);
		assertEquals(ERROR_REMOVING, true, result);
	}

	public static Passenger initPassenger(String username, String password, Date date) {
		Passenger passenger = new Passenger();
		passenger.setUsername(username);
		passenger.setPassword(password);
		passenger.setBirthDate(date);
		return passenger;
	}

	public static Passenger initPassenger(Date date, String password) {
		Passenger passenger = new Passenger();
		passenger.setPassword(password);
		passenger.setBirthDate(date);
		return passenger;
	}

	public static Passenger initPassenger(String username, Date date) {
		Passenger passenger = new Passenger();
		passenger.setUsername(username);
		passenger.setBirthDate(date);
		return passenger;
	}

	public static Passenger initPassenger(String username, String password) {
		Passenger user = new Passenger();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}

}
