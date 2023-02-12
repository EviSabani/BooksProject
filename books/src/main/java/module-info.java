module project {
	requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
	requires org.json;
	
    opens project.books to javafx.fxml;
    opens project.API to javafx.fxml;
    
    exports project.books;
    exports project.API;
}