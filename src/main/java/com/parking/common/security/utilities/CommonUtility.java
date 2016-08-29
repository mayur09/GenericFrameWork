/*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
package com.parking.common.security.utilities;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.parking.common.security.DigestEncoderTypes;
import com.parking.common.security.StatusType;

public class CommonUtility {
	private static final String NULL_STRING = "null";
	private static final String NONE_STRING = "none";
	private static final String ALHA_NUMERIC_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static int alhaNumericCharLength = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
			.length();
	private static final int PWD_LENGTH = 10;
	private static MessageDigestPasswordEncoder md5PasswordEncoder;
	private static MessageDigestPasswordEncoder shaPasswordEncoder;

	public static String encodeMD5(String encodeString) {
		return md5PasswordEncoder.encodePassword(encodeString, "");
	}

	public static String encodeSHA256(String encodeString) {
		return shaPasswordEncoder.encodePassword(encodeString, "");
	}

	public boolean isValidMD5(String encodedString, String rawString) {
		return md5PasswordEncoder.isPasswordValid(encodedString, encodedString, "");
	}

	public boolean isValidSHA256(String encodedString, String rawString) {
		return shaPasswordEncoder.isPasswordValid(encodedString, encodedString, "");
	}

	@Deprecated
	public static String convertStringIntoMD5(String plainString) throws IllegalArgumentException {
		return encodeMD5(plainString);
	}

	@Deprecated
	public static String convertStringIntoSHA256(String plainString) throws IllegalArgumentException {
		return encodeSHA256(plainString);
	}

	public static String encodeString(DigestEncoderTypes encodeType, String plainString) {
		String encodePassword;
		switch (encodeType.ordinal()) {
		case 1:
			encodePassword = encodeMD5(plainString);
			break;
		case 2:
			encodePassword = encodeSHA256(plainString);
			break;
		default:
			encodePassword = plainString;
		}

		return encodePassword;
	}

	public static String generateRandomAlphaNumericString() {
		return generateRandomAlphaNumericString(10);
	}

	public static String generateRandomAlphaNumericString(int pwdLength) {
		Random rnd = new SecureRandom();
		StringBuilder randomPassword = new StringBuilder();

		for (int i = 0; i < pwdLength; ++i) {
			randomPassword.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
					.charAt(rnd.nextInt(alhaNumericCharLength)));
		}

		return randomPassword.toString();
	}

	public static String getLoggedUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
	}

	public static StatusType getStatusTypeByValue(Integer value) {
		for (StatusType s : StatusType.values()) {
			if (s.getValue() == value.intValue()) {
				return s;
			}
		}
		return null;
	}

	public static HttpHeaders getHttpHeader() {
		return new HttpHeaders() {
			public MultivaluedMap<String, String> getRequestHeaders() {
				return null;
			}

			public List<String> getRequestHeader(String name) {
				return Collections.emptyList();
			}

			public MediaType getMediaType() {
				return null;
			}

			public Locale getLanguage() {
				return null;
			}

			public Map<String, Cookie> getCookies() {
				return null;
			}

			public List<MediaType> getAcceptableMediaTypes() {
				return Collections.emptyList();
			}

			public List<Locale> getAcceptableLanguages() {
				return Collections.emptyList();
			}
		};
	}

	public static boolean isValidString(String str) {
		return isValidString(str, false, false);
	}

	public static boolean isValidString(String str, boolean isNoneValue, boolean isNullValue) {
		boolean result = false;
		if (StringUtils.isEmpty(str))
			result = true;
		else if (str.trim().length() == 0)
			result = true;
		else if ((isNullValue) && ("null".equalsIgnoreCase(str)))
			result = true;
		else if ((isNoneValue) && ("none".equalsIgnoreCase(str))) {
			result = true;
		}
		return result;
	}

	static {
		ApplicationContext context = new ClassPathXmlApplicationContext("framework-beans.xml");
		md5PasswordEncoder = (MessageDigestPasswordEncoder) context.getBean("md5PasswordEncoder");
		shaPasswordEncoder = (MessageDigestPasswordEncoder) context.getBean("shaPasswordEncoder");
		((ConfigurableApplicationContext) context).close();
	}
}