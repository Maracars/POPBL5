package domain.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.Delay;
import domain.model.Flight;
import domain.model.Plane;
import domain.model.User;

public class TestDaoUser {

	private static final String USERNAME = "joanes";
	private static final String PASSWORD = "1234";
	private static final String ERROR_REMOVING = "Error removing one user from database";
	private static final String ERROR_INSERT = "Error insert user into database";

	@Test
	public void testInsertUserWithoutBirthDateAndWithUsernameAndWithPasswordIntoDB() {
		User user = initUser(USERNAME, PASSWORD);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertUserWithoutPasswordWithBirthDateAndAndWithUsernameIntoDB() {

		User user = initUser(USERNAME, new Date());
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithoutUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		User user = initUser(new Date(), PASSWORD);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		User user = initUser(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, true, result);

	}

	@Test
	public void testRemoveOneSpecificUser() {

		deleteAllDelays();
		deleteAllFlights();
		deleteAllPlanes();
		User user = initUser(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);

		HibernateGeneric.insertObject(user);

		boolean result = DAOUser.deleteUserWithUsername(user);
		assertEquals(ERROR_REMOVING, true, result);
	}

	public static User initUser(String username, String password, Date date) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setBirthDate(date);
		return user;
	}

	public static User initUser(Date date, String password) {
		User user = new User();
		user.setPassword(password);
		user.setBirthDate(date);
		return user;
	}

	public static User initUser(String username, Date date) {
		User user = new User();
		user.setUsername(username);
		user.setBirthDate(date);
		return user;
	}

	public static User initUser(String username, String password) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}

	private void deleteAllFlights() {
		List<Object> listFlight = HibernateGeneric.loadAllObjects(new Flight());
		for (Object flight : listFlight) {
			HibernateGeneric.deleteObject((Flight) flight);
		}
	}

	private void deleteAllPlanes() {
		List<Object> listPlanes = HibernateGeneric.loadAllObjects(new Plane());
		for (Object plane : listPlanes) {
			HibernateGeneric.deleteObject((Plane) plane);
		}
	}

	private void deleteAllDelays() {
		List<Object> listDelays = HibernateGeneric.loadAllObjects(new Delay());
		for (Object delay : listDelays) {
			HibernateGeneric.deleteObject((Delay) delay);
		}
	}

}
