package domain.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

import domain.model.Path;

public class TestDaoPath {

	private static final String FREE_LOAD_ERROR = "Could not load free paths";
	private static final String DELETE_ERROR = "ERROR: could not delete path from DB";
	private static final String ERROR_LOAD = "Error load all paths from database";
	private static final String ERROR_INSERT = "Error insert path into database";

	@Test
	public void testInsertPathWithEverythingIntoDB() {

		boolean result = HibernateGeneric.saveOrUpdateObject(Initializer.initPathWithFreeLanes());
		assertEquals(ERROR_INSERT, true, result);
	}

	@Test
	public void testLoadAllPlanes() {
		assertNotNull(ERROR_LOAD, HibernateGeneric.loadAllObjects(new Path()));

	}

	@Test
	public void testGetFreePlaneWithoutFlight() {
		Path path = Initializer.initPathWithFreeLanes();
		HibernateGeneric.saveOrUpdateObject(path);

		boolean result = HibernateGeneric.deleteObject(path);
		assertEquals(DELETE_ERROR, true, result);
	}

	@Test
	public void testGetFreePaths() {
		Path path = Initializer.initPathWithFreeLanes();
		HibernateGeneric.saveOrUpdateObject(path);
		List<Path> paths = DAOPath.loadAllFreePaths();
		assertNotNull(FREE_LOAD_ERROR, paths);
	}

}
