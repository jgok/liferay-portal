/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.poshi.runner.pql;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * @author Michael Hashimoto
 */
public class PQLValueTest extends TestCase {

	@Test
	public void testGetValue() throws Exception {
		_validateValueResult("false", Boolean.valueOf(false));
		_validateValueResult("'false'", Boolean.valueOf(false));
		_validateValueResult("\"false\"", Boolean.valueOf(false));
		_validateValueResult("true", Boolean.valueOf(true));
		_validateValueResult("'true'", Boolean.valueOf(true));
		_validateValueResult("\"true\"", Boolean.valueOf(true));

		_validateValueResult("3.2", Double.valueOf(3.2));
		_validateValueResult("'3.2'", Double.valueOf(3.2));
		_validateValueResult("\"3.2\"", Double.valueOf(3.2));
		_validateValueResult("2016.0", Double.valueOf(2016));
		_validateValueResult("'2016.0'", Double.valueOf(2016));
		_validateValueResult("\"2016.0\"", Double.valueOf(2016));

		_validateValueResult("2016", Integer.valueOf(2016));
		_validateValueResult("'2016'", Integer.valueOf(2016));
		_validateValueResult("\"2016\"", Integer.valueOf(2016));

		_validateValueResult("test", "test");
		_validateValueResult("'test'", "test");
		_validateValueResult("\"test\"", "test");

		_validateValueResult("'test test'", "test test");
		_validateValueResult("\"test test\"", "test test");
	}

	@Test
	public void testGetValueNull() throws Exception {
		_validateValueResultNull(null);
		_validateValueResultNull("'null'");
		_validateValueResultNull("\"null\"");
	}

	@Test
	public void testValueError() throws Exception {
		Set<String> pqls = new HashSet<>();

		pqls.add("test test");
		pqls.add("true AND true");
		pqls.add("test == test");

		for (String pql : pqls) {
			_validateValueError(pql, "Invalid value: " + pql);
		}
	}

	private void _validateValueError(String pql, String expected)
		throws Exception {

		String actual = null;

		try {
			PQLValue pqlValue = new PQLValue(pql);

			Object valueObject = pqlValue.getValue(new Properties());
		}
		catch (Exception e) {
			actual = e.getMessage();

			if (!actual.equals(expected)) {
				StringBuilder sb = new StringBuilder();

				sb.append("Mismatched error within the following PQL:\n");
				sb.append(pql);
				sb.append("\n\n* Actual:   \"");
				sb.append(actual);
				sb.append("\"\n* Expected: \"");
				sb.append(expected);
				sb.append("\"");

				throw new Exception(sb.toString(), e);
			}
		}
		finally {
			if (actual == null) {
				throw new Exception(
					"No error thrown for the following PQL:\n" + pql);
			}
		}
	}

	private void _validateValueResult(String pql, Object expected)
		throws Exception {

		Properties properties = new Properties();

		Class clazz = expected.getClass();

		PQLValue pqlValue = new PQLValue(pql);

		Object actual = pqlValue.getValue(properties);

		if (!clazz.isInstance(actual)) {
			throw new Exception(
				pql + " should be of type '" + clazz.getName() + "'");
		}

		if (!actual.equals(expected)) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched value within the following PQL:\n");
			sb.append(pql);
			sb.append("\n\n* Actual:   \"");
			sb.append(actual);
			sb.append("\"\n* Expected: \"");
			sb.append(expected);
			sb.append("\"");

			throw new Exception(sb.toString());
		}
	}

	private void _validateValueResultNull(String pql) throws Exception {
		Properties properties = new Properties();

		PQLValue pqlValue = new PQLValue(pql);

		Object actual = pqlValue.getValue(properties);
		Object expected = null;

		if (actual != null) {
			StringBuilder sb = new StringBuilder();

			sb.append("Mismatched value within the following PQL:\n");
			sb.append(pql);
			sb.append("\n\n* Actual:   \"");
			sb.append(actual);
			sb.append("\"\n* Expected: \"");
			sb.append(expected);
			sb.append("\"");

			throw new Exception(sb.toString());
		}
	}

}