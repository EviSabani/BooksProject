package project.API;

import org.json.JSONArray;
import org.json.JSONObject;

public class Book {
    private String id;
    private String selfLink;
    public String title;
    @SuppressWarnings("exports")
	public JSONArray authors;
    public String publisher;
    private String publishedDate;
    private JSONArray industryIdentifiers;
    private String description;
    private int pageCount;
    private JSONArray categories;
    private String language;

    public Book() { } // blank constructor

    // copy constructor
    public Book(Book book) {
        this.setId(book.id);
        this.setSelfLink(book.selfLink);
        this.setTitle(book.title);
        this.setAuthors(book.authors);
        this.setPublisher(book.publisher);
        this.setPublishedDate(book.publishedDate);
        this.setIndustryIdentifiers(book.industryIdentifiers);
        this.setDescription(book.description);
        this.setPageCount(book.pageCount);
        this.setCategories(book.categories);
        this.setLanguage(book.language);
    }

    Book(
    		String id, 
    		String selfLink, 
    		String title, 
    		JSONArray authors, 
    		String publisher, 
    		String publishedDate, 
    		JSONArray industryIdentifiers, 
    		String description, 
    		int pageCount, 
    		JSONArray categories, 
    		String language
    	) {
    	this.id = id;
        this.setId(id);
        this.setSelfLink(selfLink);
        this.setTitle(title);
        this.setAuthors(authors);
        this.setPublisher(publisher);
        this.setPublishedDate(publishedDate);
        this.setIndustryIdentifiers(industryIdentifiers);
        this.setDescription(description);
        this.setPageCount(pageCount);
        this.setCategories(categories);
        this.setLanguage(language);
    }

    /* 
     * 
     * SETTERS - GETTERS 
     * 
     * */
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelfLink() {
    	if(this.selfLink == null) return "not available";
        return this.selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public String getTitle() {
    	if(this.title == null) return "not available";
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // beautify authors JSONArray
    public String getAuthorsToString() {
    	String returnedString = "";
		if(!(this.authors == null)){
			for(int i=0; i<this.authors.length(); i++) {
				returnedString = returnedString + this.authors.getString(i) + "  ";
			}
		}else returnedString = "not available";
        return returnedString;
    }
    
	@SuppressWarnings("exports") // suppress the JSONArray warning
	public JSONArray getAuthors() {
		return authors;
    }

    public void setAuthors(@SuppressWarnings("exports") JSONArray authors) {
        this.authors = authors;
    }

    public String getPublisher() {
    	if(this.publisher == null) return "not available";
        return this.publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
    	if(this.publishedDate == null) return "not available";
        return this.publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    // beautify industry identifiers JSONArray
    public String getIndustryIdentifiersToString() {
		String returnedString = "";
		if(!(this.industryIdentifiers == null)) {
			for(int i=0; i<this.industryIdentifiers.length(); i++) {
				JSONObject temp = (JSONObject) this.industryIdentifiers.get(i);
				returnedString = returnedString + "\n" + temp.getString("type") + ": " + temp.getString("identifier") + "   ";
			}			
		} else returnedString = "\nIndustry Identifiers: not available";
		
        return returnedString;
    }
    
	@SuppressWarnings("exports")
	public JSONArray getIndustryIdentifiers() {
		return this.industryIdentifiers;
    }

    public void setIndustryIdentifiers(@SuppressWarnings("exports") JSONArray industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public String getDescription() {
    	if(this.description == null) return "not available";
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageCount() {
        return this.pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    // beautify categories JSONArray
    public String getCategoriesToString() {
    	String returnedString = "";
		if(!(this.categories == null)) {
			for(int i=0; i<this.categories.length(); i++) {
				String temp = this.categories.getString(i);
				returnedString = returnedString + temp + "   ";
			}			
		} else returnedString = "not available";
		
        return returnedString;
    }
	@SuppressWarnings("exports")
	public JSONArray getCategories() {
		return categories;
    }

    public void setCategories(@SuppressWarnings("exports") JSONArray categories) {
        this.categories = categories;
    }

    public String getLanguage() {
    	if(this.language == null) return "not available";
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
    
    /* 
     * 
     * END SETTERS - GETTERS 
     * 
     * */

    @Override
    public String toString() {
        return (
        		"Title: " + getTitle() + 
        		"\nAuthors: " + getAuthorsToString() + 
        		"\nPublisher: " + getPublisher() + 
        		"\nPublished Date: " + getPublishedDate() + 
        		getIndustryIdentifiersToString() + 
        		"\nDescription: " + getDescription() + 
        		"\nPage Count: " + getPageCount() + 
        		"\nCategories: " + getCategoriesToString() + 
        		"\nLanguage: " + getLanguage()
        		);
    }
}
