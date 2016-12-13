package domain.dao;

import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import helpers.MD5;

public class TestMD5 {

	private static final String NOT_EXISTING_ALGORYTHM = "MDMA";
	private static final String RESULT_JOANES = "190e705eb4164f500ed435cb69689cb6";
	private static final String USERNAME = "Joanes";
	private static final String ENCRYPT_USERNAME_TEST = "The MD5 helper did not encrypt well";

	@Test
	public void testMD5NullAndMD5TypeIntoDB() throws NoSuchAlgorithmException {
		assertEquals(ENCRYPT_USERNAME_TEST, null, MD5.encrypt(null));
	}

	@Test
	public void testMD5WithInputAndMD5TypeIntoDB() throws NoSuchAlgorithmException {
		assertEquals(ENCRYPT_USERNAME_TEST, RESULT_JOANES, MD5.encrypt(USERNAME));
	}

	@Test(expected = NoSuchAlgorithmException.class)
	public void testMD5WithInputAndNotExistingAlgorythmIntoDB() throws NoSuchAlgorithmException {

		MD5.encrypt(USERNAME, NOT_EXISTING_ALGORYTHM);
	}

}
