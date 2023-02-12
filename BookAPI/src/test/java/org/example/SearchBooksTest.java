package org.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.BookAPI.SearchBooks;
import org.json.JSONObject;
import org.junit.Test;

public class SearchBooksTest {

    @Test
    public void searchVolumesTestTrueOutcome() {
        JSONObject searchParameters = new JSONObject();
        searchParameters.put("intitle", "flower of truth");
        searchParameters.put("subject", "fiction");

        SearchBooks searchBooks = new SearchBooks(searchParameters);
        boolean answer = searchBooks.searchVolumes(0, 20);

        assertTrue(answer);
    }

    @Test
    public void searchVolumesTestFalseOutcome() {
        JSONObject searchParameters = new JSONObject();

        SearchBooks searchBooks = new SearchBooks(searchParameters);
        boolean answer = searchBooks.searchVolumes(0, 20);

        assertFalse(answer);
    }

    @Test
    public void searchVolumesViaBookshelfTestTrueOutcome() {
        SearchBooks searchBooks = new SearchBooks();
        boolean answer = searchBooks.searchVolumesViaBookshelf("102701940585560677579", "1001");

        assertTrue(answer);
        assertEquals("Books Successfully retrieved!", searchBooks.getMessage());
    }

    @Test
    public void searchVolumesViaBookshelfTestFalseOutcome() {
        SearchBooks searchBooks = new SearchBooks();
        boolean answer = searchBooks.searchVolumesViaBookshelf("5", "5");

        assertFalse(answer);

    }

    @Test
    public void getURLTestWithTwoIntegers() {
        JSONObject searchParameters = new JSONObject();
        searchParameters.put("intitle", "flower of truth");
        searchParameters.put("subject", "fiction");

        SearchBooks searchBooks = new SearchBooks(searchParameters);

        String returnedURL = searchBooks.getURLForTest(0, 10);

        String correctURL = "https://www.googleapis.com/books/v1/volumes?q=intitle:flower+of+truth+subject:fiction&startIndex=0&maxResults=10";
        assertEquals(correctURL, returnedURL);

    }

    @Test
    public void getURLTestWithTwoStrings() {
        SearchBooks searchBooks = new SearchBooks();
        String returnedURL = searchBooks.getURLForTest("102701940585560677579", "1001");

        String correctURL = "https://www.googleapis.com/books/v1/users/102701940585560677579/bookshelves/1001/volumes";
        assertEquals(correctURL, returnedURL);

    }
}