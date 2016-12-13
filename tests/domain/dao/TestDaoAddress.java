package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Address;

public class TestDaoAddress {

	private static final String ERROR_REMOVING = "Error removing one address from database";
	private static final String ERROR_LOAD = "Error load all addresses from database";
	private static final String ERROR_INSERT = "Error insert address into database";

	@Test
	public void testInsertAddressIntoDB() {
		Address address = Initializer.initAddress();
		boolean result = HibernateGeneric.saveOrUpdateObject(address);
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllStates() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Address()));

	}

	@Test
	public void testRemoveOneSpecificState() {
		Address address = Initializer.initAddress();
		HibernateGeneric.saveOrUpdateObject(address);
		boolean result = HibernateGeneric.deleteObject(address);
		assertEquals(ERROR_REMOVING, true, result);
	}



}
