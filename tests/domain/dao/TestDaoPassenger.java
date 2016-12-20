package domain.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Test;

import domain.model.users.Passenger;

public class TestDaoPassenger {

	private static final String ERROR_INSERT = "Error insert plane maker into database";
	private static final String USERNAME = "joanes";
	private static final String PASSWORD = "1234";
	private static final String ERROR_REMOVING = "Error removing one user from database";

	@Test
	public void testInsertUserWithoutBirthDateAndWithUsernameAndWithPasswordIntoDB() {
		Passenger user = Initializer.initPassenger(USERNAME, PASSWORD);
		DAOUser.deleteUserWithUsername(user);
		boolean result = HibernateGeneric.saveOrUpdateObject(user);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertUserWithoutPasswordWithBirthDateAndAndWithUsernameIntoDB() {

		Passenger user = Initializer.initPassenger(USERNAME, new Date());
		boolean result = HibernateGeneric.saveOrUpdateObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithoutUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		Passenger user = Initializer.initPassenger(new Date(), PASSWORD);
		boolean result = HibernateGeneric.saveOrUpdateObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		Passenger user = Initializer.initPassenger(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);
		boolean result = HibernateGeneric.saveOrUpdateObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testRemoveOneSpecificUser() {

		Passenger passenger = Initializer.initCompletePassenger();
		DAOUser.deleteUserWithUsername(passenger);

		HibernateGeneric.saveOrUpdateObject(passenger);

		boolean result = DAOUser.deleteUserWithUsername(passenger);
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testCheckUserExistsThatExists() {

		Passenger passenger = Initializer.initCompletePassenger();
		DAOUser.deleteUserWithUsername(passenger);

		HibernateGeneric.saveOrUpdateObject(passenger);

		boolean result = DAOUser.checkUsernameExists(passenger.getUsername());
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testCheckUserExistsThatDoesNotExist() {

		Passenger passenger = Initializer.initCompletePassenger();
		String username = passenger.getUsername();
		DAOUser.deleteUserWithUsername(passenger);
		boolean result = DAOUser.checkUsernameExists(username);
		assertEquals(ERROR_REMOVING, false, result);
	}

}
