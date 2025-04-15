public abstract class Book {
    protected String title;
    protected String author;
    protected String genre;
    protected String isbn;
    protected boolean isAvailable;
    protected String dueDate;

    public Book(String title, String author, String genre, String isbn, boolean isAvailable, String dueDate) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isbn = isbn;
        this.isAvailable = isAvailable;
        this.dueDate = dueDate;
    }

    public String getIsbn() {
        return isbn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return "Title: " + title + ", Author: " + author + ", Genre: " + genre + ", ISBN: " + isbn +
                ", Available: " + isAvailable + ", Due Date: " + (dueDate == null ? "N/A" : dueDate);
    }
}