package com.isbnvalidator.ISBNValidator;

public class StockManager {

	private ExternalISBNDataService webService;
	private ExternalISBNDataService dBService;

	public ExternalISBNDataService getDBservice() {
		return dBService;
	}

	public void setDBservice(ExternalISBNDataService dBservice) {
		dBService = dBservice;
	}

	public ExternalISBNDataService getService() {
		return webService;
	}

	public void setWebService(ExternalISBNDataService webService) {
		this.webService = webService;
	}

	public String getLocatorCode(String isbn) {
		Book book = dBService.lookup(isbn);
		if (book == null)
			book = webService.lookup(isbn);
		StringBuilder locator = new StringBuilder();
		locator.append(isbn.substring(isbn.length() - 4));
		locator.append(book.getAuthor().substring(0, 1));
		locator.append(book.getTitle().split(" ").length);
		return locator.toString();
	}

}
