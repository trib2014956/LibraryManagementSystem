public class PrintedBook extends Book {
    private int numPages;

    public PrintedBook(String title, String author, String genre, String isbn, boolean isAvailable, String dueDate, int numPages) {
        super(title, author, genre, isbn, isAvailable, dueDate);
        this.numPages = numPages;
    }

    @Override
    public String toFileString() {
        return "PrintedBook," + title + "," + author + "," + genre + "," + isbn + "," + isAvailable + "," + dueDate + "," + numPages;
    }
}