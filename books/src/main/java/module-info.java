module project {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    opens project.books to javafx.fxml;
    exports project.books;
}