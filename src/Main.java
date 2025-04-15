import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LibrarySystem library = new LibrarySystem();
        Scanner scanner = new Scanner(System.in);

        // Login
        System.out.println("Welcome to the Library Management System");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (library.login(username, password)) {
            System.out.println("Login successful! Role: " + library.getCurrentUser().getRole());
            while (true) {
                System.out.println("\n1. List Books\n2. Admin: Add User\n3. Admin: Remove User\n4. Admin: List Users\n" +
                        "5. Librarian: Add Book\n6. Librarian: Remove Book\n7. Reader: Borrow Book\n8. Reader: Return Book\n9. Exit");
                System.out.print("Choose an option: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        library.listBooks();
                        break;
                    case 2:
                        System.out.print("New username: ");
                        String newUsername = scanner.nextLine();
                        System.out.print("New password: ");
                        String newPassword = scanner.nextLine();
                        System.out.print("Role (Admin/Librarian/Reader): ");
                        String role = scanner.nextLine();
                        library.addUser(newUsername, newPassword, role);
                        break;
                    case 3:
                        System.out.print("Username to remove: ");
                        String removeUsername = scanner.nextLine();
                        library.removeUser(removeUsername);
                        break;
                    case 4:
                        library.listUsers();
                        break;
                    case 5:
                        System.out.print("Book type (PrintedBook/Ebook): ");
                        String bookType = scanner.nextLine();
                        System.out.print("Title: ");
                        String title = scanner.nextLine();
                        System.out.print("Author: ");
                        String author = scanner.nextLine();
                        System.out.print("Genre: ");
                        String genre = scanner.nextLine();
                        System.out.print("ISBN: ");
                        String isbn = scanner.nextLine();
                        if (bookType.equals("PrintedBook")) {
                            System.out.print("Number of pages: ");
                            int pages = scanner.nextInt();
                            scanner.nextLine();
                            library.addBook(new PrintedBook(title, author, genre, isbn, true, null, pages));
                        } else if (bookType.equals("Ebook")) {
                            System.out.print("File format: ");
                            String format = scanner.nextLine();
                            library.addBook(new Ebook(title, author, genre, isbn, format));
                        }
                        break;
                    case 6:
                        System.out.print("ISBN to remove: ");
                        String removeIsbn = scanner.nextLine();
                        library.removeBook(removeIsbn);
                        break;
                    case 7:
                        System.out.print("ISBN to borrow: ");
                        String borrowIsbn = scanner.nextLine();
                        System.out.print("Due date (e.g., 2025-05-01): ");
                        String dueDate = scanner.nextLine();
                        library.borrowBook(borrowIsbn, dueDate);
                        break;
                    case 8:
                        System.out.print("ISBN to return: ");
                        String returnIsbn = scanner.nextLine();
                        library.returnBook(returnIsbn);
                        break;
                    case 9:
                        System.out.println("Goodbye!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid option.");
                }
            }
        } else {
            System.out.println("Login failed.");
        }
    }
}