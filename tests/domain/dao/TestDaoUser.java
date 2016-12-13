package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.users.User;


public class TestDaoUser {

	private static final String USERNAME = "joanes";
	private static final String PASSWORD = "1234";
	private static final String ERROR_REMOVING = "Error removing one user from database";
	private static final String ERROR_INSERT = "Error insert user into database";
	private static final String ERROR_LOAD = "Null when getting user by username";

	@Test
	public void testInsertUserWithUsernameAndWithPasswordIntoDB() {
		User user = Initializer.initUser(USERNAME, PASSWORD);
		DAOUser.deleteUserWithUsername(user);
		boolean result = HibernateGeneric.saveOrUpdateObject(user);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testInsertUserWithoutPasswordAndUsernameIntoDB() {

		User user = Initializer.initUser();
		boolean result = HibernateGeneric.saveOrUpdateObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}



	@Test
	public void getUsernameWhereUsernameFromDB() {

		User user = Initializer.initUser(USERNAME, PASSWORD);
		DAOUser.deleteUserWithUsername(user);
		HibernateGeneric.saveOrUpdateObject(user);

		assertNotNull(ERROR_LOAD, DAOUser.getUser(USERNAME));
	}

	@Test
	public void testRemoveOneSpecificUser() {

		User user = Initializer.initUser(USERNAME, PASSWORD);
		DAOUser.deleteUserWithUsername(user);

		HibernateGeneric.saveOrUpdateObject(user);

		boolean result = DAOUser.deleteUserWithUsername(user);
		assertEquals(ERROR_REMOVING, true, result);
	}

}
