package helpers;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

	private static final String DEFAULT_ALGORITHM = "MD5";
	private final static String SALT = "Kappa123&/()==?%#@|º¡¿@$@4&#%^$*";
	private final static Integer BASE = 16;
	private final static Integer SIGNAL = 1;

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return md5;
	}

	public static String encrypt(String input) {
		return encrypt(input, DEFAULT_ALGORITHM);
	}

}
