package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.BookAPI.SearchBookshelves;
import org.junit.Test;

public class SearchBookshelvesTest {

	@Test
	public void searchBookshelvesTrueOutcome() {
        SearchBookshelves searchBookshelves = new SearchBookshelves("102701940585560677579");
        boolean answer = searchBookshelves.searchBookshelves();

        assertTrue(answer);
    }
	
	@Test
	public void searchBookshelvesFalseOutcome() {
        SearchBookshelves searchBookshelves = new SearchBookshelves("5");
        boolean answer = searchBookshelves.searchBookshelves();

        assertFalse(answer);
    }

    @Test
    public void getURLForTestTrueOutcome() {
        String userId = "102701940585560677579";
        SearchBookshelves searchBookshelves = new SearchBookshelves(userId);
        String returnedURL = searchBookshelves.getURLForTest();

        assertEquals("https://www.googleapis.com/books/v1/users/" + userId + "/bookshelves", returnedURL);
    }
}
