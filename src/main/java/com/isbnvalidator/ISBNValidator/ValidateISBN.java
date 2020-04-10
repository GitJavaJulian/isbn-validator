package com.isbnvalidator.ISBNValidator;

public class ValidateISBN {

	private static final int LONG_ISBN_MULTIPLIER = 10;
	private static final int SHORT_ISBN_MULTIPLIER = 11;
	private static final int SHORT_ISBN_LENGTH = 10;
	private static final int LONG_ISBN_LENGTH = 13;

	public boolean checkISBN(String isbn) {

		if (isbn.length() == LONG_ISBN_LENGTH) {
			return isThisAValidLongISBN(isbn);

		} else if (isbn.length() == SHORT_ISBN_LENGTH) {

			return isThisAValidShortISBN(isbn);
		}
		throw new NumberFormatException("El ISBN debe tener un total de 10 o 13 numeros!");
	}

	private boolean isThisAValidShortISBN(String isbn) {

		int total10Digits = 0;

		for (int i = 0; i < SHORT_ISBN_LENGTH; i++) {

			if (!Character.isDigit(isbn.charAt(i))) {

				if (i == 9 && isbn.charAt(i) == 'X') {
					total10Digits += 10;
				} else {
					throw new NumberFormatException("El ISBN debe estar compuesto de numeros!");
				}

			} else {
				total10Digits += Character.getNumericValue(isbn.charAt(i)) * (SHORT_ISBN_LENGTH - i);
			}

		}

		return ((total10Digits % SHORT_ISBN_MULTIPLIER) == 0);
	}

	private boolean isThisAValidLongISBN(String isbn) {

		int total13Digits = 0;

		for (int i = 0; i < LONG_ISBN_LENGTH; i++) {
			if ((i % 2) == 0) {
				total13Digits += Character.getNumericValue(isbn.charAt(i));
			} else {
				total13Digits += Character.getNumericValue(isbn.charAt(i)) * 3;
			}
		}

		return ((total13Digits % LONG_ISBN_MULTIPLIER) == 0);
	}
}
