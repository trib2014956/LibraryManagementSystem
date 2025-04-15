import java.io.*;
import java.util.*;

public class LibrarySystem {
    private List<User> users = new ArrayList<>();
    private List<Book> books = new ArrayList<>();
    private List<String> transactions = new ArrayList<>();
    private User currentUser;

    public LibrarySystem() {
        loadUsers();
        loadBooks();
        loadTransactions();
    }

    // Add this getter method
    public User getCurrentUser() {
        return currentUser;
    }

    // Load data from files
    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[2].equals("Admin")) users.add(new Admin(parts[0], parts[1]));
                else if (parts[2].equals("Librarian")) users.add(new Librarian(parts[0], parts[1]));
                else if (parts[2].equals("Reader")) users.add(new Reader(parts[0], parts[1]));
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private void loadBooks() {
        try (BufferedReader br = new BufferedReader(new FileReader("books.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals("PrintedBook")) {
                    books.add(new PrintedBook(parts[1], parts[2], parts[3], parts[4], Boolean.parseBoolean(parts[5]), parts[6], Integer.parseInt(parts[7])));
                } else if (parts[0].equals("Ebook")) {
                    books.add(new Ebook(parts[1], parts[2], parts[3], parts[4], parts[7]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }

    private void loadTransactions() {
        try (BufferedReader br = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                transactions.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error loading transactions: " + e.getMessage());
        }
    }

    // Save data to files
    private void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User user : users) {
                bw.write(user.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private void saveBooks() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("books.txt"))) {
            for (Book book : books) {
                bw.write(book.toFileString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void saveTransactions() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("transactions.txt"))) {
            for (String transaction : transactions) {
                bw.write(transaction);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving transactions: " + e.getMessage());
        }
    }

    // Login functionality
    public boolean login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    // Admin functionalities
    public void addUser(String username, String password, String role) {
        if (currentUser.getRole().equals("Admin")) {
            if (role.equals("Admin")) users.add(new Admin(username, password));
            else if (role.equals("Librarian")) users.add(new Librarian(username, password));
            else if (role.equals("Reader")) users.add(new Reader(username, password));
            saveUsers();
            System.out.println("User added successfully.");
        } else {
            System.out.println("Permission denied.");
        }
    }

    public void removeUser(String username) {
        if (currentUser.getRole().equals("Admin")) {
            users.removeIf(user -> user.getUsername().equals(username));
            saveUsers();
            System.out.println("User removed successfully.");
        } else {
            System.out.println("Permission denied.");
        }
    }

    public void listUsers() {
        if (currentUser.getRole().equals("Admin")) {
            for (User user : users) {
                System.out.println(user.getUsername() + " (" + user.getRole() + ")");
            }
        } else {
            System.out.println("Permission denied.");
        }
    }

    // Librarian functionalities
    public void addBook(Book book) {
        if (currentUser.getRole().equals("Librarian")) {
            books.add(book);
            saveBooks();
            System.out.println("Book added successfully.");
        } else {
            System.out.println("Permission denied.");
        }
    }

    public void removeBook(String isbn) {
        if (currentUser.getRole().equals("Librarian")) {
            books.removeIf(book -> book.getIsbn().equals(isbn));
            saveBooks();
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("Permission denied.");
        }
    }

    public void listBooks() {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Reader functionalities
    public void borrowBook(String isbn, String dueDate) {
        if (currentUser.getRole().equals("Reader")) {
            for (Book book : books) {
                if (book.getIsbn().equals(isbn) && book.isAvailable() && !(book instanceof Ebook)) {
                    book.setAvailable(false);
                    book.setDueDate(dueDate);
                    transactions.add(currentUser.getUsername() + ",borrowed," + isbn + "," + dueDate);
                    saveBooks();
                    saveTransactions();
                    System.out.println("Book borrowed successfully.");
                    return;
                }
            }
            System.out.println("Book not available or is an Ebook.");
        } else {
            System.out.println("Permission denied.");
        }
    }

    public void returnBook(String isbn) {
        if (currentUser.getRole().equals("Reader")) {
            for (Book book : books) {
                if (book.getIsbn().equals(isbn) && !book.isAvailable()) {
                    book.setAvailable(true);
                    book.setDueDate(null);
                    transactions.add(currentUser.getUsername() + ",returned," + isbn);
                    saveBooks();
                    saveTransactions();
                    System.out.println("Book returned successfully.");
                    return;
                }
            }
            System.out.println("Book not borrowed.");
        } else {
            System.out.println("Permission denied.");
        }
    }
}