package com.isbnvalidator.ISBNValidator;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class StockManagementTest {

	ExternalISBNDataService testWebService;
	ExternalISBNDataService testDBService;
	StockManager stockManager;

	@Before
	public void setUp() {

		testWebService = mock(ExternalISBNDataService.class);
		testDBService = mock(ExternalISBNDataService.class);
		stockManager = new StockManager();
		stockManager.setWebService(testWebService);
		stockManager.setDBservice(testDBService);

	}

	@Test
	public void testCanGetACorrectLocatorCode() {
		String isbn = "0140177396";

		when(testWebService.lookup(Mockito.<String>any())).thenReturn(new Book(isbn, "Of Mice and Man", "J.K"));
		when(testDBService.lookup(Mockito.<String>any())).thenReturn(null);

		String locatorCode = stockManager.getLocatorCode(isbn);

		assertEquals("7396J4", locatorCode);

	}

	@Test
	public void databaseIsUsedIfDataIsPresent() {

		when(testDBService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);

		verify(testDBService, times(1)).lookup("0140177396");
		verify(testWebService, times(0)).lookup(Mockito.<String>any());
	}

	@Test
	public void webServiceIsUsedIfIsNotDataInDatabase() {

		when(testDBService.lookup("0140177396")).thenReturn(null);
		when(testWebService.lookup("0140177396")).thenReturn(new Book("0140177396", "abc", "abc"));

		String isbn = "0140177396";
		String locatorCode = stockManager.getLocatorCode(isbn);

		verify(testDBService, times(1)).lookup("0140177396");
		verify(testWebService, times(1)).lookup("0140177396");
	}

}
