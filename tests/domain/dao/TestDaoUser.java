package domain.dao;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.User;

public class TestDaoUser {

	private static final String USERNAME = "joanes";
	private static final String PASSWORD = "1234";
	private static final String ERROR_REMOVING = "Error removing one user from database";
	private static final String ERROR_INSERT = "Error insert user into database";

	@Test
	public void testInsertUserWithoutBirthDateAndWithUsernameAndWithPasswordIntoDB() {
		User user = new User();
		user.setPassword(PASSWORD);
		user.setUsername(USERNAME);
		boolean result = DAOUser.insertUser(user);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertUserWithoutPasswordWithBirthDateAndAndWithUsernameIntoDB() {

		User user = new User();
		user.setUsername(USERNAME);
		user.setBirthDate(new Date());
		boolean result = DAOUser.insertUser(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithoutUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		User user = new User();
		user.setPassword(PASSWORD);
		user.setBirthDate(new Date());
		boolean result = DAOUser.insertUser(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		User user = new User();
		user.setUsername(USERNAME);
		user.setBirthDate(new Date());
		user.setPassword(PASSWORD);
		deleteAllUsers();
		boolean result = DAOUser.insertUser(user);
		assertEquals(ERROR_INSERT, true, result);

	}

	@Test
	public void testRemoveOneSpecificUser() {
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		user.setBirthDate(new Date());
		deleteAllUsers();
		DAOUser.insertUser(user);
		boolean result = DAOUser.deleteUser((User) HibernateGeneric.loadAllObjects(new User()).get(0));
		assertEquals(ERROR_REMOVING, true, result);
	}

	@Test
	public void testRemoveOneUserByUsername() {
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		user.setBirthDate(new Date());
		deleteAllUsers();
		DAOUser.insertUser(user);

	}

	/* For testing, delete all users */
	private void deleteAllUsers() {
		List<User> listUsers = DAOUser.loadAllUsers();
		for (User user : listUsers) {
			DAOUser.deleteUser(user);
		}
	}
}
