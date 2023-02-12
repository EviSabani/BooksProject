package project.books;

public class SearchesHistory {
	// values for Book history
	private String title, category, writer, publisher, isbn, lccn, oclc, viewability, printType, projection, sorting;
	private boolean hasDownloadableFile;
	
	// values for Bookshelf history
	private String userID;
	
	public SearchesHistory() { } // blank constructor
	
	// bookshelf searches constructor
	public SearchesHistory(String userId) {
		setUserID(userId);
	}
	
	// books searches constructor
	public SearchesHistory(
			String title, 
			String category, 
			String writer, 
			String publisher, 
			String isbn, 
			String lccn, 
			String oclc, 
			String viewability, 
			String printType, 
			String projection,
			String sorting,
			boolean hasDownloadableFile
			) {
		setTitle(title);
		setCategory(category);
		setWriter(writer);
		setPublisher(publisher);
		setIsbn(isbn);
		setLccn(lccn);
		setOclc(oclc);
		setViewability(viewability);
		setPrintType(printType);
		setProjection(projection);
		setSorting(sorting);
		setHasDownloadableFile(hasDownloadableFile);
	}
	
	/*
	 * SETTERS - GETTERS
	 * 
	 * */
	
	// For Book Constructor
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getLccn() {
		return lccn;
	}
	public void setLccn(String lccn) {
		this.lccn = lccn;
	}

	public String getOclc() {
		return oclc;
	}
	public void setOclc(String oclc) {
		this.oclc = oclc;
	}

	public String getViewability() {
		return viewability;
	}
	public void setViewability(String viewability) {
		this.viewability = viewability;
	}

	public String getPrintType() {
		return printType;
	}
	public void setPrintType(String printType) {
		this.printType = printType;
	}

	public String getProjection() {
		return projection;
	}
	public void setProjection(String projection) {
		this.projection = projection;
	}

	public String getSorting() {
		return sorting;
	}
	public void setSorting(String sorting) {
		this.sorting = sorting;
	}
	
	public boolean getHasDownloadableFile() {
		return hasDownloadableFile;
	}
	public void setHasDownloadableFile(boolean hasDownloadableFile) {
		this.hasDownloadableFile = hasDownloadableFile;
	}
	
	// For Bookshelf constructor
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
}
