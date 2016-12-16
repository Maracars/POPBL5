package domain.dao;

import static org.junit.Assert.assertEquals;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

import helpers.MD5;

public class TestMD5 {

	private static final String NOT_EXISTING_ALGORYTHM = "MDMA";
	private static final String RESULT_JOANES = "29035a75131c99ec0e55977625c2f544";
	private static final String USERNAME = "admin";
	private static final String ENCRYPT_USERNAME_TEST = "The MD5 helper did not encrypt well";

	@Test
	public void testMD5NullAndMD5TypeIntoDB() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		assertEquals(ENCRYPT_USERNAME_TEST, null, MD5.encrypt(null));
	}

	@Test
	public void testMD5WithInputAndMD5TypeIntoDB() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		assertEquals(ENCRYPT_USERNAME_TEST, RESULT_JOANES, MD5.encrypt(USERNAME));
	}

	@Test(expected = NoSuchAlgorithmException.class)
	public void testMD5WithInputAndNotExistingAlgorythmIntoDB() throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MD5.encrypt(USERNAME, NOT_EXISTING_ALGORYTHM);
	}

}
