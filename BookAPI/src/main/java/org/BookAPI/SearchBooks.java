package org.BookAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchBooks {
    private static HttpURLConnection connection;
    private JSONObject searchParameters = new JSONObject();
    private Books listOfBooks = new Books();
    
    private boolean answer;
    private int totalResults;
    
    private String message;

    public SearchBooks() {
    }

    public SearchBooks(JSONObject searchParameters) {
        this.searchParameters = searchParameters;
    }

    // function to search volumes
	public boolean searchVolumes(int page, int maxResults) {
		if (searchParameters == null || searchParameters.isEmpty()) {
			setMessage("You cannot search for books if you have not provided any search parameters");
			return false;
		}
		if (maxResults < 10 || maxResults > 40) {
			setMessage("Max results have to be beetween 10 and 40");
			return false;
		}
        try {
            StringBuilder responceContent = new StringBuilder();
            URL url = new URL(this.getURL(page, maxResults));
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            String line;
            BufferedReader reader;
            
            if (status != 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                while(true) {
                    if ((line = reader.readLine()) == null) {
                        reader.close();
                        break;
                    }

                    responceContent.append(line);
                    responceContent.append("\n");
                }
                this.setAnswer(false);
                setMessage("Something is wrong with your search. Please try again!");
                return getAnswer();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while((line = reader.readLine()) != null) {
                    responceContent.append(line);
                    responceContent.append("\n");
                }

                reader.close();
            }

            JSONArray books = this.parsetoJSON(responceContent.toString());
            if (books.isEmpty()) {
                this.setAnswer(false);
                setMessage("There were no results from your search. Please try a different one");
                return this.getAnswer();
            }

            this.extractInfoFromJSONVolume(books);
  
        } catch (MalformedURLException e) {
            this.setAnswer(false);
            setMessage("Please enter valid URL");
            return this.getAnswer();
        } catch (IOException e) {
            this.setAnswer(false);
            setMessage("Something happened, please try again later");
            return this.getAnswer();
        } finally {
            connection.disconnect();
        }

        this.setAnswer(true);
        setMessage("Books successfully retrieved!");
        return this.getAnswer();
    }

    // function to search volumes via Bookshelves
	public boolean searchVolumesViaBookshelf(String userId, String bookshelfId) {
    	try {
            StringBuilder responceContent = new StringBuilder();
            URL url = new URL(this.getURL(userId, bookshelfId));
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            String line;
            BufferedReader reader;
            
            if (status != 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

                while(true) {
                    if ((line = reader.readLine()) == null) {
                        reader.close();
                        break;
                    }

                    responceContent.append(line);
                    responceContent.append("\n");
                }
                this.setMessage("Something is wrong with your search. Please try again!");
                return false;
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while((line = reader.readLine()) != null) {
                    responceContent.append(line);
                    responceContent.append("\n");
                }

                reader.close();

                JSONArray books = this.parsetoJSON(responceContent.toString());
                if (books.isEmpty()) {
                    this.setAnswer(false);
                    setMessage("There were no results from your search. Please try a different one");
                    return this.getAnswer();
                }

                this.extractInfoFromJSONVolume(books);
            }
    	} catch (MalformedURLException e) {
            this.setAnswer(false);
            setMessage("Please enter valid URL");
            return this.getAnswer();
        } catch (IOException e) {
            this.setAnswer(false);
            setMessage("Something happened, please try again later");
            return this.getAnswer();
        } finally {
            connection.disconnect();
        }
    	
    	this.setAnswer(true);
    	setMessage("Books Successfully retrieved!");
    	return this.getAnswer();
    }
    
	// function to get URL for Bookshelf search
    private String getURL(String userId, String bookshelfId) {
    	String URL = "https://www.googleapis.com/books/v1/users/" + userId + "/bookshelves/" + bookshelfId + "/volumes";
		return URL;
	}
    
    // function to get URL for Books search
    private String getURL(int page, int maxResults) {
        Iterator<String> jsonKeys = this.searchParameters.keys();
        String URL = "https://www.googleapis.com/books/v1/volumes?q=";
        boolean first = true;

        while(jsonKeys.hasNext()) {
            String key = (String)jsonKeys.next();
            if (key != "filter" && key != "download" && key != "printType" && key != "projection" && key != "orderBy") {
                String searchParameter = (String)this.searchParameters.get(key);
                searchParameter = searchParameter.replaceAll(" ", "+");
                if (first) {
                    URL = URL + key + ":" + searchParameter;
                    first = false;
                } else {
                    URL = URL + "+" + key + ":" + searchParameter;
                }
            }
        }

        if (this.searchParameters.has("download")) URL = URL + "&download=" + this.searchParameters.getString("download");
        if (this.searchParameters.has("filter")) URL = URL + "&filter=" + this.searchParameters.getString("filter");
        if (this.searchParameters.has("printType")) URL = URL + "&printType=" + this.searchParameters.getString("printType");
        if (this.searchParameters.has("projection")) URL = URL + "&projection=" + this.searchParameters.getString("projection");
        if (this.searchParameters.has("orderBy")) URL = URL + "&orderBy=" + this.searchParameters.getString("orderBy");

        URL = URL + "&startIndex=" + page + "&maxResults=" + maxResults;
        return URL;
    }

    // save given information from JSONArray to a list of Books (which type is Books)
    private void extractInfoFromJSONVolume(JSONArray books) {
        for(int i = 0; i < books.length(); ++i) {
            JSONObject book = books.getJSONObject(i);
            
            String id = null;
            String selfLink = null;
            JSONObject volumeInfo = null;
            String title = null;
            JSONArray authors = null;
            String publisher = null;
            String publishedDate = null;
            String description = null;
            int pageCount = 0;
            String language = null;
            JSONArray industryIdentifiers = null;
            JSONArray categories = null;
            
            if (book.has("id")) id = book.getString("id");
            if (book.has("selfLink")) selfLink = book.getString("selfLink");
            if (book.has("volumeInfo")) volumeInfo = book.getJSONObject("volumeInfo");
            if (volumeInfo.has("title")) title = volumeInfo.getString("title");
            if (volumeInfo.has("authors")) authors = volumeInfo.getJSONArray("authors");
            if (volumeInfo.has("publisher")) publisher = volumeInfo.getString("publisher");
            if (volumeInfo.has("publishedDate")) publishedDate = volumeInfo.getString("publishedDate");
            if (volumeInfo.has("industryIdentifiers")) industryIdentifiers = volumeInfo.optJSONArray("industryIdentifiers");
            if (volumeInfo.has("categories")) categories = volumeInfo.optJSONArray("categories");
            if (volumeInfo.has("description")) description = volumeInfo.getString("description");
            if (volumeInfo.has("pageCount")) pageCount = volumeInfo.getInt("pageCount");
            if (volumeInfo.has("language")) language = volumeInfo.getString("language");

            this.listOfBooks.setNewBook(new Book(id, selfLink, title, authors, publisher, publishedDate, industryIdentifiers, description, pageCount, categories, language));
        }

    }

    // return the JSONArray of books that are returned to us from GoogleBooksAPI
    private JSONArray parsetoJSON(String responceBody) {
        JSONObject results = new JSONObject(responceBody);
        int totalItems = results.getInt("totalItems");
        JSONArray items;
        if (totalItems <= 0) {
            items = new JSONArray();
            return items;
        } else {
        	setTotalResults(totalItems);
            items = results.getJSONArray("items");
            return items;
        }
    }

    
    /*
     * 
     * SETTERS - GETTERS
     * 
     * */
	public boolean getAnswer() {
        return this.answer;
    }

    public void setAnswer(boolean done) {
        this.answer = done;
    }
    
    
    public ArrayList<Book> getListOfBooks(){
    	return this.listOfBooks.getBooks();
    }
    
    public int getTotalResults(){
    	return totalResults;
    }
    public void setTotalResults(int totalResults){
    	this.totalResults = totalResults;
    }

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	/*
     * 
     * END SETTERS - GETTERS
     * 
     * */
	
	public String getURLForTest(int page, int maxResults){
        String returnedString = getURL(page, maxResults);
        return returnedString;
    }
    public String getURLForTest(String userID, String bookshelfID){
        String returnedString = getURL(userID, bookshelfID);
        return returnedString;
    }
}
