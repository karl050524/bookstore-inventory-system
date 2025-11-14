package classes;

public class Book {
    private final String title;
    private final String author;
    private final String isbn;
    private final float price;

    public Book(String title, String author, String isbn, float price){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", ISBN: " + isbn + ", Price: $" + price;
    }
}
