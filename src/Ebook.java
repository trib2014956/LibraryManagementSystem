public class Ebook extends Book {
    private String fileFormat;

    public Ebook(String title, String author, String genre, String isbn, String fileFormat) {
        super(title, author, genre, isbn, true, null); // Ebooks cannot be borrowed
        this.fileFormat = fileFormat;
    }

    @Override
    public String toFileString() {
        return "Ebook," + title + "," + author + "," + genre + "," + isbn + "," + isAvailable + "," + dueDate + "," + fileFormat;
    }
}