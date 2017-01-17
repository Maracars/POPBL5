package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import domain.model.Path;
import initialization.GatesInitialization;

public class TestDaoPath {

	private static final String DELETE_ERROR = "ERROR: could not delete path from DB";
	private static final String ERROR_LOAD = "Error load all paths from database";
	private static final String ERROR_INSERT = "Error insert path into database";

	@Test
	public void testInsertPathWithEverythingIntoDB() {

		boolean result = HibernateGeneric.saveObject(Initializer.initPathWithFreeLanes());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPaths() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Path()));

	}

	@Test
	public void testDeletePathWithFreeLanes() {
		Path path = Initializer.initPathWithFreeLanes();
		HibernateGeneric.saveObject(path);

		boolean result = HibernateGeneric.deleteObject(path);
		assertEquals(DELETE_ERROR, true, result);
	}

	@Test
	public void testGetPathsFromDatabase() {
		assertNotNull("COULD NOT GET THE PATHS", GatesInitialization.getPathsFromDatabase());
	}

}
