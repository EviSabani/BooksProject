package project.books;

import javafx.fxml.FXML;

public class MainController {
	
	private static String userTransferID;
	private static String bookshelfID;
	
	@FXML public BooksSceneController booksSceneController;
	@FXML public BookshelfSceneController bookshelfSceneController;

	public MainController() { } // blank constructor
	
	/*
	 * 
	 *  Setter and Getters
	 * 
	 * */
	public String getTransferUserID() {
		return MainController.userTransferID;
	}
	public void setTransferUserID(String userTransferID) {
		MainController.userTransferID = userTransferID;
	}

	public String getBookshelfID() {
		return MainController.bookshelfID;
	}
	public void setBookshelfID(String bookshelfID) {
		MainController.bookshelfID = bookshelfID;
	}
	
	/*
	 * 
	 *  END Setter and Getters
	 * 
	 * */
}
