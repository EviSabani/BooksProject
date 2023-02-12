package org.BookAPI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchBookshelves {
    private static HttpURLConnection connection;
    private String userID;
    private Bookshelves listOfBookshelves = new Bookshelves();
    
    private boolean answer;
    private String message;

    public SearchBookshelves() {
    }

    public SearchBookshelves(String userID) {
        this.setUserID(userID);
    }

	public boolean searchBookshelves() {
		if(userID == null) {
			setMessage("You cannot search for public bookshelves if you don't provide a user ID.");
			return false;
		}

		try {
            StringBuilder responceContent = new StringBuilder();
            URL url = new URL(this.getURL());
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            int status = connection.getResponseCode();
            BufferedReader reader;
            String line;
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

            JSONArray bookshelves = this.parsetoJSON(responceContent.toString());
            if (bookshelves.isEmpty()) {
            	this.setAnswer(false);
            	setMessage("There were no public Bookshelves with this user ID");
            	return this.getAnswer();
            }

            this.extractInfoFromJSONBookshelf(bookshelves);
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
        setMessage("Bookshelves successfully retrieved!");
        return this.getAnswer();
    }


    private JSONArray parsetoJSON(String responceBody) {
        JSONObject results = new JSONObject(responceBody);
        JSONArray items = new JSONArray();
        if(!results.has("items")) {
        	return items;
        }
        items = results.getJSONArray("items");
        return items;
    }

    private void extractInfoFromJSONBookshelf(JSONArray bookshelves) {
        for(int i = 0; i < bookshelves.length(); ++i) {
            JSONObject bookshelf = bookshelves.getJSONObject(i);
            int id = 0;
            String title = null;
            String description = null;
            String updated = null;
            String created = null;
            int volumeCount = 0;
            String volumesLastUpdated = null;
            
            if (bookshelf.has("id")) id = bookshelf.getInt("id");
            if (bookshelf.has("title")) title = bookshelf.getString("title");
            if (bookshelf.has("description")) description = bookshelf.getString("description");
            if (bookshelf.has("updated")) description = bookshelf.getString("updated");
            if (bookshelf.has("created")) description = bookshelf.getString("created");
            if (bookshelf.has("volumeCount")) volumeCount = bookshelf.getInt("volumeCount");
            if (bookshelf.has("volumesLastUpdated")) description = bookshelf.getString("volumesLastUpdated");

            this.listOfBookshelves.setNewBookshelf(new Bookshelf(id, title, description, updated, created, volumeCount, volumesLastUpdated));
        }

    }

    private String getURL() {
        String URL = "https://www.googleapis.com/books/v1/users/" + this.getUserID() + "/bookshelves";
        return URL;
    }

    /*
     * 
     * SETTERS - GETTERS
     * 
     * */
    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

	public boolean getAnswer() {
        return this.answer;
    }

    public void setAnswer(boolean done) {
    	this.answer = done;
    }
    
    public ArrayList<Bookshelf> getListOfBookshelves(){
    	return this.listOfBookshelves.getBookshelves();
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

	public String getURLForTest() {
        return getURL();
    }
}
