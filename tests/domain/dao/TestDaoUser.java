package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Test;

import domain.model.User;

public class TestDaoUser {

	private static final String USERNAME = "joanes";
	private static final String PASSWORD = "1234";
	private static final String ERROR_REMOVING = "Error removing one user from database";
	private static final String ERROR_INSERT = "Error insert user into database";
	private static final String ERROR_LOAD = "Null when getting user by username";

	@Test
	public void testInsertUserWithoutBirthDateAndWithUsernameAndWithPasswordIntoDB() {
		User user = Initializer.initUser(USERNAME, PASSWORD);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertUserWithoutPasswordWithBirthDateAndAndWithUsernameIntoDB() {

		User user = Initializer.initUser(USERNAME, new Date());
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithoutUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		User user = Initializer.initUser(new Date(), PASSWORD);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		User user = Initializer.initUser(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, true, result);

	}
	
	@Test
	public void getUsernameWhereUsernameFromDB() {

		User user = Initializer.initUser(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);
		HibernateGeneric.insertObject(user);
		
		assertNotNull(ERROR_LOAD, DAOUser.getUser(USERNAME));
	}

	@Test
	public void testRemoveOneSpecificUser() {

		Initializer.deleteAllDelays();
		Initializer.deleteAllFlights();
		Initializer.deleteAllPlanes();
		User user = Initializer.initUser(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);

		HibernateGeneric.insertObject(user);

		boolean result = DAOUser.deleteUserWithUsername(user);
		assertEquals(ERROR_REMOVING, true, result);
	}




}
