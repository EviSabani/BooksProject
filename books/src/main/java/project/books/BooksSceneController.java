package project.books;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import project.API.Book;
import project.API.SearchBooks;

public class BooksSceneController extends MainController{
	// setup Books History Searches
	private ArrayList<SearchesHistory> bookSearchesHistory = new ArrayList<SearchesHistory>();
	
	// setup main controller
	@SuppressWarnings("unused")
	private MainController mainController;
	
	private ObservableList<Book> observableBooksList;
	private ObservableList<SearchesHistory> observableBooksHistoryList;
	 	
	private int page = 0;
	private int maxResults = 10;
	private int totalResults;
	
	@SuppressWarnings("exports")
	public JSONObject searchParameters = new JSONObject();
	
    // import choice boxes and set up their choices
	@FXML private ChoiceBox<Integer> maxResultsChoiceBox;
	private Integer[] maxResultsChoices = {10, 15, 20, 25, 30, 35, 40};
	
	@FXML private ChoiceBox<String> viewabilityChoiceBox;
	private String[] previewAvailabilityChoices = {"", "partial", "full", "free-ebooks", "paid-ebooks", "ebooks"};

	@FXML private ChoiceBox<String> printTypeChoiceBox;
	private String[] printTypeChoices = { "", "all", "books", "magazines"};
	
	@FXML private ChoiceBox<String> projectionChoiceBox;
	private String[] projectionChoices = {"", "full", "lite"};
	
	@FXML private ChoiceBox<String> sortingChoiceBox;
	private String[] sortingChoices = {"", "relevance", "newest"};
	
	
    // import books view table view
	@FXML private TableView<Book> booksTableView;
    @FXML private TableColumn<Book, JSONArray> colAuthors;
    @FXML private TableColumn<Book, String> colPublisher;
    @FXML private TableColumn<Book, String> colTitle;
    
    // import history table view
    @FXML private TableView<SearchesHistory> booksHistoryTableView;
    @FXML private TableColumn<SearchesHistory, String> titleHistoryCol;
    @FXML private TableColumn<SearchesHistory, String> categoryHistoryCol;
    @FXML private TableColumn<SearchesHistory, String> writerHistoryCol;
    @FXML private TableColumn<SearchesHistory, String> publisherHistoryCol;
    @FXML private TableColumn<SearchesHistory, String> isbnHistoryCol;
    @FXML private TableColumn<SearchesHistory, String> lccnHistoryCol;
    @FXML private TableColumn<SearchesHistory, String> oclcHistoryCol;
    
    @FXML private Button transferSearchedItemButton;
    
    // import text fields
    @FXML private TextField titleLabel;		// -------------------------
    @FXML private TextField categoryLabel;	//
    @FXML private TextField writerLabel;	//	ONE OF THOSE FIELDS IS REQUIRED
    @FXML private TextField publisherLabel;	//
    @FXML private TextField isbnLabel;		// -------------------------
    @FXML private TextField subjectLabel;
    @FXML private TextField lccnLabel;
    @FXML private TextField oclcLabel;
    
    // import userId and BookshelfId for searching volumes via public Bookshelves
    @FXML private TextField bookshelfIdLabel;
    @FXML private TextField userIdLabel;
    
    // import checkBox and moreInfoBox and required variables
    @FXML private CheckBox epubCheckBox;
    @FXML private TextArea moreInfoBox;
    
    // import pagination buttons
    @FXML private Button rightPageButton;
    @FXML private Button leftPageButton;


    // import error label & status label
    @FXML private Label errorLabel;
    @FXML private Label statusLabel;
    
    public BooksSceneController() { }
    
	@FXML
	public void initialize() {
		leftPageButton.setDisable(true);
		rightPageButton.setDisable(true);
		
		// setup table view
		colTitle.setCellValueFactory(new PropertyValueFactory<Book, String>("title"));
		colAuthors.setCellValueFactory(new PropertyValueFactory<Book, JSONArray>("authors"));
		colPublisher.setCellValueFactory(new PropertyValueFactory<Book, String>("publisher"));
		
		// setup history table view
		titleHistoryCol.setCellValueFactory(new PropertyValueFactory<SearchesHistory, String>("title"));
		categoryHistoryCol.setCellValueFactory(new PropertyValueFactory<SearchesHistory, String>("category"));
		writerHistoryCol.setCellValueFactory(new PropertyValueFactory<SearchesHistory, String>("writer"));
		publisherHistoryCol.setCellValueFactory(new PropertyValueFactory<SearchesHistory, String>("publisher"));
		isbnHistoryCol.setCellValueFactory(new PropertyValueFactory<SearchesHistory, String>("isbn"));
		lccnHistoryCol.setCellValueFactory(new PropertyValueFactory<SearchesHistory, String>("lccn"));
		oclcHistoryCol.setCellValueFactory(new PropertyValueFactory<SearchesHistory, String>("oclc"));		
		
		// setup choices for choice boxes
		viewabilityChoiceBox.getItems().addAll(previewAvailabilityChoices);
		maxResultsChoiceBox.getItems().addAll(maxResultsChoices);
		printTypeChoiceBox.getItems().addAll(printTypeChoices);
		projectionChoiceBox.getItems().addAll(projectionChoices);
		sortingChoiceBox.getItems().addAll(sortingChoices);
	}
	
	// search button controller function
	@FXML
    private boolean getData() throws MalformedURLException, IOException{
		// set error label and and more info box to blank
		errorLabel.setText("");
		statusLabel.setText("");
		
		moreInfoBox.setText("");
		booksTableView.getSelectionModel().clearSelection();
		
		// check if they gave at least one value on the categories title, category, writer, publisher, ISBN, LCCN, OCLC
		if(		titleLabel.getText().isBlank() && 
				categoryLabel.getText().isBlank() && 
				writerLabel.getText().isBlank() && 
				publisherLabel.getText().isBlank() && 
				isbnLabel.getText().isBlank() &&
				lccnLabel.getText().isBlank() &&
				oclcLabel.getText().isBlank()
		) {
			errorLabel.setText("You have to fill at least one of the fields: 'title, category, writer, publisher, ISBN, LCCN, OCLC'");
			return false;
		}
		
		// change max Results
		if (maxResultsChoiceBox.getValue() != null) this.maxResults = maxResultsChoiceBox.getValue();
		
		// make a json object that will contain our criteria
		JSONObject searchParameters = setSearchObject();
		
		SearchBooks books = new SearchBooks(searchParameters);
		boolean searchResult = books.searchVolumes(this.page, this.maxResults);
		
		// checking if searchResult is false
		if(!searchResult) {
			errorLabel.setText(books.getMessage());
			return false;
		}
		else {
			// set total book results to a variable for future reference, so that we cannot add more pages forever
			this.totalResults = books.getTotalResults();
			statusLabel.setText(books.getMessage());
			
			// set books to GUI
			booksToObservableList(books.getListOfBooks());
			setFoundBooksOnTableView();
			return true;
		}
    }
	
	@SuppressWarnings("exports")
	public JSONObject setSearchObject() {
		// make searchParameters blank for the next search
		searchParameters.clear();
		
		if(!titleLabel.getText().isBlank()) searchParameters.put("intitle", titleLabel.getText());
		if(!categoryLabel.getText().isBlank()) searchParameters.put("subject", categoryLabel.getText());
		if(!writerLabel.getText().isBlank()) searchParameters.put("inauthor", writerLabel.getText());
		if(!publisherLabel.getText().isBlank()) searchParameters.put("inpublisher", publisherLabel.getText());
		if(!isbnLabel.getText().isBlank()) searchParameters.put("isbn", isbnLabel.getText());
		
		if(!lccnLabel.getText().isBlank()) searchParameters.put("lccn", lccnLabel.getText());
		if(!oclcLabel.getText().isBlank()) searchParameters.put("oclc", oclcLabel.getText());
		
		if(viewabilityChoiceBox.getValue() != "") searchParameters.put("filter", viewabilityChoiceBox.getValue());
		if(printTypeChoiceBox.getValue() != "") searchParameters.put("printType", printTypeChoiceBox.getValue());
		if(projectionChoiceBox.getValue() != "") searchParameters.put("projection", projectionChoiceBox.getValue());
		if(sortingChoiceBox.getValue() != "") searchParameters.put("orderBy", sortingChoiceBox.getValue());
		if(epubCheckBox.isSelected()) searchParameters.put("download", "epub");
		
		return searchParameters;
	}
	
	// Button Controller Functions
	public void searchButtonController() {
		page = 0;
		rightPageButton.setDisable(false);
		try {
			boolean result = getData();
			
			if (!result) return;
			// add a new search history object to SearchesHistory ArrayList
			addSearch();
			// add the searched item to History matrix
			transferSearchedItemButton.setDisable(false);
		} catch (IOException e) {
			errorLabel.setText("Something went wrong. Please try again later!");
		}
	}
	
	public void leftPageButtonController(){
		// handle the possibility of a left button when it should not get pressed
		if (page <= 0) {
			errorLabel.setText("There are no pages before the first page");
			leftPageButton.setDisable(true);
			return;
		}
		rightPageButton.setDisable(false);
		page --;
		try {
			getData();
		} catch (Exception e) {
			errorLabel.setText("Something went wrong. Please try again later!");
		}
		
		if(page <= 0) leftPageButton.setDisable(true);
		
	}
	
	public void rightPageButtonController() {
		// handle the possibility of a right button when it should not get pressed
		if(page >= (this.totalResults/this.maxResults)) {
			errorLabel.setText("There are no more pages to see");
			rightPageButton.setDisable(true);
			return;
		}
		leftPageButton.setDisable(false);
		page ++;
		try {
			getData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if(page >= (this.totalResults/this.maxResults)) rightPageButton.setDisable(true);
	}
	
	public void setFoundBooksOnTableView() {
    	booksTableView.setItems(observableBooksList);
    }
    
	// function to get more info from books
    public void findMoreInfo() {
    	if(booksTableView.getSelectionModel().getSelectedItem() == null) {
    		return;
    	}
    	
    	moreInfoBox.setText(booksTableView.getSelectionModel().getSelectedItem().toString());
    }
    
    @FXML public void searchBooksViaBookshelf() {
    	errorLabel.setText("");
    	statusLabel.setText("");
    	
    	rightPageButton.setDisable(true);
    	leftPageButton.setDisable(true);
    	
    	if(userIdLabel.getText().isBlank() || bookshelfIdLabel.getText().isBlank()) {
    		errorLabel.setText("Please go to the Bookshelf tab to search via public Bookselves");
    		return;
    	}
    	
    	SearchBooks bookshelfBooks = new SearchBooks();
		boolean bookshelfSearchResult = bookshelfBooks.searchVolumesViaBookshelf(getTransferUserID(), getBookshelfID());
		
		if(!bookshelfSearchResult) errorLabel.setText(bookshelfBooks.getMessage());
		else {
			this.totalResults = bookshelfBooks.getTotalResults();
			statusLabel.setText(bookshelfBooks.getMessage());
			
			// set books to GUI
			booksToObservableList(bookshelfBooks.getListOfBooks());
			setFoundBooksOnTableView();
		}
    	
    }
    
    // get user and bookshelf IDs from MainController
    public void getUserAndBookshelfIDs() {
    	userIdLabel.setText(getTransferUserID());
    	bookshelfIdLabel.setText(getBookshelfID());
    }
	
    // add searched item to search history
    public void addSearch(){
    	bookSearchesHistory.add(new SearchesHistory(
    			titleLabel.getText(), 
    			categoryLabel.getText(), 
    			writerLabel.getText(),
    			publisherLabel.getText(),
    			isbnLabel.getText(),
    			lccnLabel.getText(),
    			oclcLabel.getText(),
    			viewabilityChoiceBox.getValue(),
    			printTypeChoiceBox.getValue(),
    			projectionChoiceBox.getValue(),
    			sortingChoiceBox.getValue(),
    			epubCheckBox.isSelected()
    			));
    	if(!(bookSearchesHistory.size() <= 5)) {
    		// rotate Array List so that the fifth element is the old zero
			Collections.rotate(bookSearchesHistory, -1);
			// remove the old zero element, that we don't need
			bookSearchesHistory.remove(5);
    	}
    	booksSearchesToObservableList(bookSearchesHistory);
    	setSearchesOnTableView();
    }
    
    // setting observable list object on GUI table view for the history table
    public void setSearchesOnTableView() {
    	booksHistoryTableView.setItems(observableBooksHistoryList);
    }
    
    // function to set the previously searched item on fields
    public void setSearchedItem() {
    	if(booksHistoryTableView.getSelectionModel().getSelectedItem() == null) {
    		errorLabel.setText("Please select a previous search you have done!");
    		return;
    	}
    	
    	titleLabel.setText(booksHistoryTableView.getSelectionModel().getSelectedItem().getTitle());
    	categoryLabel.setText(booksHistoryTableView.getSelectionModel().getSelectedItem().getCategory());
    	writerLabel.setText(booksHistoryTableView.getSelectionModel().getSelectedItem().getWriter());
    	publisherLabel.setText(booksHistoryTableView.getSelectionModel().getSelectedItem().getPublisher());
    	isbnLabel.setText(booksHistoryTableView.getSelectionModel().getSelectedItem().getIsbn());
    	lccnLabel.setText(booksHistoryTableView.getSelectionModel().getSelectedItem().getLccn());
    	oclcLabel.setText(booksHistoryTableView.getSelectionModel().getSelectedItem().getOclc());
    	
    	viewabilityChoiceBox.setValue(booksHistoryTableView.getSelectionModel().getSelectedItem().getViewability());
    	printTypeChoiceBox.setValue(booksHistoryTableView.getSelectionModel().getSelectedItem().getPrintType());
    	projectionChoiceBox.setValue(booksHistoryTableView.getSelectionModel().getSelectedItem().getProjection());
    	sortingChoiceBox.setValue(booksHistoryTableView.getSelectionModel().getSelectedItem().getSorting());
    	
    	epubCheckBox.setSelected(booksHistoryTableView.getSelectionModel().getSelectedItem().getHasDownloadableFile());
    }
    
    /*
     * 
     * GETTERS - SETTERS
     * 
     * */
    
	// Converting ArrayLists to Observable Lists for GUI
	public void booksToObservableList(ArrayList<Book> bookItems) {
		observableBooksList = FXCollections.observableArrayList(bookItems);
	}
	public void booksSearchesToObservableList(ArrayList<SearchesHistory> bookHistoryItems) {
		observableBooksHistoryList = FXCollections.observableArrayList(bookHistoryItems);
	}
	
	@SuppressWarnings("exports")
	public TextField getBookshelfIdLabel() {
		return this.bookshelfIdLabel;
	}
	public void setBookshelfIdLabel(String value) {
		this.bookshelfIdLabel.setText(value);
	}
	
	@SuppressWarnings("exports")
	public TextField getUserIdLabel() {
		return this.userIdLabel;
	}
	public void setUserIdLabel(String value) {
		this.userIdLabel.setText(value);
	}

	public ArrayList<SearchesHistory> getBookSearchesHistory() {
		return bookSearchesHistory;
	}
	public void setBookSearchesHistory(ArrayList<SearchesHistory> bookSearchesHistory) {
		this.bookSearchesHistory = bookSearchesHistory;
	}

	public ObservableList<SearchesHistory> getObservableBooksHistoryList() {
		return observableBooksHistoryList;
	}
	public void setObservableBooksHistoryList(ObservableList<SearchesHistory> observableBooksHistoryList) {
		this.observableBooksHistoryList = observableBooksHistoryList;
	}
}
