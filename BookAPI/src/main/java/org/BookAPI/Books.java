package org.BookAPI;
import java.util.ArrayList;

public class Books extends Book {
    private ArrayList<Book> books = new ArrayList<Book>();

    public Books() { } // blank constructor

    public Books(ArrayList<Book> books) {
        this.setBooks(books);
    }

    public ArrayList<Book> getBooks() {
        return this.books;
    }
    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public int length() {
        return this.books.size();
    }

    // to get an element from the ArrayList books
    public Book getChild(int child) {
        return (Book)this.books.get(child);
    }

    public void setNewBook(Book book) {
        this.books.add(book);
    }

    // testing function
    public void printBooks() {
        for(int i = 0; i < this.books.size(); ++i) {
            System.out.println(this.books.get(i) + "");
        }

    }
}
