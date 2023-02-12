package project.books;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import project.API.Bookshelf;
import project.API.SearchBookshelves;

public class BookshelfSceneController extends MainController{
	
	// setup Books History Searches
	private ArrayList<SearchesHistory> bookshelfSearchesHistory = new ArrayList<SearchesHistory>();
	private ObservableList<SearchesHistory> observableBookshelfHistoryList;
		
	
	private String userID;
	
	@FXML private ResourceBundle resources;
    @FXML private URL location;
    
    // import table view stuff
    @FXML private TableView<Bookshelf> bookshelvesTableView;
    @FXML private TableColumn<Bookshelf, String> colTitle;
    @FXML private TableColumn<Bookshelf, Integer> colVolumeCount;
    
    // import history table view
    @FXML private TableView<SearchesHistory> bookshelfHistoryTableView;
    @FXML private TableColumn<SearchesHistory, String> usrIdHistoryCol;
    
    
    @FXML private Button transferSearchedItemButton;
    @FXML private Button findBooksFromBookshelfButton;
    
    @FXML private TextArea moreInfoTextArea;
    @FXML private TextField userIdTextField;

    @FXML private Label statusBookshelfLabel;
    @FXML private Label errorBookshelfLabel;

	private ObservableList<Bookshelf> observableBookshelfList;
	
	// scene initialization
	@FXML public void initialize() {
    	findBooksFromBookshelfButton.setDisable(true);
    	colTitle.setCellValueFactory(new PropertyValueFactory<Bookshelf, String>("title"));
    	colVolumeCount.setCellValueFactory(new PropertyValueFactory<Bookshelf, Integer>("volumeCount"));
    	
    	usrIdHistoryCol.setCellValueFactory(new PropertyValueFactory<SearchesHistory, String>("userID"));

    }
	
	// send user and bookshelf ids to Main Controller
    @FXML public void transferValuesToBooksScene(@SuppressWarnings("exports") ActionEvent event) {
		setBookshelfID(String.valueOf(bookshelvesTableView.getSelectionModel().getSelectedItem().getId()));
		setTransferUserID(getUserID());
		
		statusBookshelfLabel.setText("Values have been transfered. Please go to books tab");
    }

    @FXML public void searchBookshelves(@SuppressWarnings("exports") ActionEvent event) {    	
    	errorBookshelfLabel.setText("");
    	statusBookshelfLabel.setText("");
    	moreInfoTextArea.setText("");
    	
    	if(userIdTextField.getText().isBlank()) {
    		errorBookshelfLabel.setText("You have to provide a user ID");
    		return;
    	}
    	
    	findBooksFromBookshelfButton.setDisable(false);
    	
    	SearchBookshelves bookshelves = new SearchBookshelves(userIdTextField.getText());
    	boolean bookshelvesResult = bookshelves.searchBookshelves();
    	
    	if(!bookshelvesResult) errorBookshelfLabel.setText(bookshelves.getMessage());
    	else {
    		setUserID(userIdTextField.getText());
    		bookshelvesListToObservableList(bookshelves.getListOfBookshelves());

    		statusBookshelfLabel.setText(bookshelves.getMessage());
    		
    		setFoundBookshelvesOnTableView();
    		addSearch();
			transferSearchedItemButton.setDisable(false);
    	}
    	
    }
    
    public void addSearch() {
    	bookshelfSearchesHistory.add(new SearchesHistory(userIdTextField.getText()));
    	
    	if(!(bookshelfSearchesHistory.size() <= 5)) {
			Collections.rotate(bookshelfSearchesHistory, -1);
			bookshelfSearchesHistory.remove(5);
    	}
    	
    	transferSearchedItemButton.setDisable(false);
    	bookshelfSearchesToObservableList(bookshelfSearchesHistory);
    	setSearchesOnTableView();
    }
    
    // set the existing searched items on table view, from our Observable List
    public void setSearchesOnTableView() {
    	bookshelfHistoryTableView.setItems(observableBookshelfHistoryList);
    }
    
    // set previous searched userId on userId Label in order to search for it again
    public void setSearchedItem() {
    	if(bookshelfHistoryTableView.getSelectionModel().getSelectedItem() != null) {
    		userIdTextField.setText(bookshelfHistoryTableView.getSelectionModel().getSelectedItem().getUserID());
    		return;
    	}
    	errorBookshelfLabel.setText("Please select a previous search you have done!");
    }
    
    // shows more data from the chosen bookshelf in the above field
    @FXML
    void findMoreBookshelfInfo() {
    	if(bookshelvesTableView.getSelectionModel().getSelectedItem() != null) {
    		moreInfoTextArea.setText(bookshelvesTableView.getSelectionModel().getSelectedItem().toString());
    	}
    }
    
    /*
     * 
     * GETTERS - SETTERS
     * 
     * */
    
    public void bookshelfSearchesToObservableList(ArrayList<SearchesHistory> bookshelfHistoryItems) {
    	observableBookshelfHistoryList = FXCollections.observableArrayList(bookshelfHistoryItems);
	}
    
    public void setFoundBookshelvesOnTableView() {
    	bookshelvesTableView.setItems(observableBookshelfList);
    }
    
    public void bookshelvesListToObservableList(ArrayList<Bookshelf> BookshelfItems) {
		observableBookshelfList = FXCollections.observableArrayList(BookshelfItems);
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public ArrayList<SearchesHistory> getBookshelfSearchesHistory() {
		return bookshelfSearchesHistory;
	}
	public void setBookshelfSearchesHistory(ArrayList<SearchesHistory> bookshelfSearchesHistory) {
		this.bookshelfSearchesHistory = bookshelfSearchesHistory;
	}

	public ObservableList<SearchesHistory> getObservableBookshelfHistoryList() {
		return observableBookshelfHistoryList;
	}

	public void setObservableBookshelfHistoryList(ObservableList<SearchesHistory> observableBookshelfHistoryList) {
		this.observableBookshelfHistoryList = observableBookshelfHistoryList;
	}
}
