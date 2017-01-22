package helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Class MD5.
 * Provides MD5 encryption with custom salting
 */
public class MD5 {

	/** The Constant DEFAULT_ALGORITHM. */
	private static final String DEFAULT_ALGORITHM = "MD5";
	
	/** The Constant SALT. */
	private final static String SALT = "Kappa123&/()==?%#@|º¡¿@$@4&#%^$*";
	
	/** The Constant BASE. */
	private final static Integer BASE = 16;
	
	/** The Constant SIGNAL. */
	private final static Integer SIGNAL = 1;

	/**
	 * Encrypt.
	 *
	 * @param input the input
	 * @param type the type
	 * @return the string
	 */
	public static String encrypt(String input, String type) {

		String md5 = null;
		String saltedInput = null;

		if (null == input)
			return null;

		saltedInput = input + SALT;
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance(type);
			digest.update(saltedInput.getBytes(), 0, saltedInput.length());
			md5 = new BigInteger(SIGNAL, digest.digest()).toString(BASE);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return md5;
	}

	/**
	 * Encrypt.
	 *
	 * @param input the input
	 * @return the string
	 */
	public static String encrypt(String input) {
		return encrypt(input, DEFAULT_ALGORITHM);
	}

}
