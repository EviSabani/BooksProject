package project.API;

import java.util.ArrayList;

public class Bookshelves extends Bookshelf {
    private ArrayList<Bookshelf> bookshelves = new ArrayList<Bookshelf>();

    public Bookshelves() {
    }

    public int length() {
        return this.getBookshelves().size();
    }

    public Bookshelf getChild(int child) {
        return (Bookshelf)this.bookshelves.get(child);
    }

    public void setNewBookshelf(Bookshelf bookshelf) {
        this.bookshelves.add(bookshelf);
    }

    public void printBookshelves() {
        for(int i = 0; i < this.bookshelves.size(); ++i) {
            System.out.println(this.bookshelves.get(i) + "");
        }

    }

    public ArrayList<Bookshelf> getBookshelves() {
        return this.bookshelves;
    }
    public void setBookshelves(ArrayList<Bookshelf> bookshelves) {
        this.bookshelves = bookshelves;
    }
}
