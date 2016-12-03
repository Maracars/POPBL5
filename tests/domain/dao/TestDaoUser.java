package domain.dao;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import domain.model.User;
import helpers.MD5;

public class TestDaoUser {

	private static final String USERNAME = "joanes";
	private static final String PASSWORD = "1234";
	private static final String ERROR_REMOVING = "Error removing one user from database";
	private static final String ERROR_INSERT = "Error insert user into database";

	@Test
	public void testInsertUserWithoutBirthDateAndWithUsernameAndWithPasswordIntoDB() {
		User user = new User();
		user.setPassword(MD5.encrypt(PASSWORD));
		user.setUsername(USERNAME);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertUserWithoutPasswordWithBirthDateAndAndWithUsernameIntoDB() {

		User user = new User();
		user.setUsername(USERNAME);
		user.setBirthDate(new Date());
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithoutUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		User user = new User();
		user.setPassword(MD5.encrypt(PASSWORD));
		user.setBirthDate(new Date());
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		User user = new User();
		user.setUsername(USERNAME);
		user.setBirthDate(new Date());
		user.setPassword(MD5.encrypt(PASSWORD));
		deleteAllUsers();
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, true, result);

	}

	@Test
	public void testRemoveOneSpecificUser() {
		User user = new User();
		user.setUsername(USERNAME);
		user.setPassword(PASSWORD);
		user.setBirthDate(new Date());
		deleteAllUsers();
		HibernateGeneric.insertObject(user);
		boolean result = HibernateGeneric.deleteObject(
				(User) HibernateGeneric.loadAllObjects(
						new User()).get(0)
				);
		assertEquals(ERROR_REMOVING, true, result);
	}

	

	/* For testing, delete all users */
	private void deleteAllUsers() {
		List<Object> listUsers = HibernateGeneric.loadAllObjects(new User());
		for (Object user : listUsers) {
			HibernateGeneric.deleteObject((User)user);
		}
	}
}
