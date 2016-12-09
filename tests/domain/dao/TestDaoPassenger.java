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
		Passenger user = Initializer.initPassenger(USERNAME, PASSWORD);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);
	}

	@Test
	public void testInsertUserWithoutPasswordWithBirthDateAndAndWithUsernameIntoDB() {

		Passenger user = Initializer.initPassenger(USERNAME, new Date());
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithoutUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		Passenger user = Initializer.initPassenger(new Date(), PASSWORD);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, false, result);

	}

	@Test
	public void testInsertUserWithUsernameWithBirthDateAndAndWithPasswordIntoDB() {

		Passenger user = Initializer.initPassenger(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);
		boolean result = HibernateGeneric.insertObject(user);
		assertEquals(ERROR_INSERT, true, result);

	}

	@Test
	public void testRemoveOneSpecificUser() {

		Passenger user = Initializer.initPassenger(USERNAME, PASSWORD, new Date());
		DAOUser.deleteUserWithUsername(user);

		HibernateGeneric.insertObject(user);

		boolean result = DAOUser.deleteUserWithUsername(user);
		assertEquals(ERROR_REMOVING, true, result);
	}

}
