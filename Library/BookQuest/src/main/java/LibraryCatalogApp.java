import java.util.List;
import java.util.Scanner;

public class LibraryCatalogApp {
    private LibraryCatalog catalog;
    private Scanner scanner;
    
    public LibraryCatalogApp() {
        this.catalog = new LibraryCatalog();
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("Welcome to the Library Catalog System!");
        System.out.println("=====================================");
        
        while (true) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    addNewBook();
                    break;
                case 2:
                    searchBooks();
                    break;
                case 3:
                    listAllBooks();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    System.out.println("Thank you for using the Library Catalog System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void displayMenu() {
        System.out.println("\n--- Library Catalog Menu ---");
        System.out.println("1. Add a new book");
        System.out.println("2. Search books");
        System.out.println("3. List all books");
        System.out.println("4. Delete a book");
        System.out.println("5. Exit");
        System.out.print("Enter your choice (1-5): ");
    }
    
    private int getChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            return choice;
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1;
        }
    }
    
    private void addNewBook() {
        System.out.println("\n--- Add New Book ---");
        
        System.out.print("Enter book title: ");
        String title = scanner.nextLine().trim();
        if (title.isEmpty()) {
            System.out.println("Error: Title cannot be empty.");
            return;
        }
        
        System.out.print("Enter author name: ");
        String author = scanner.nextLine().trim();
        if (author.isEmpty()) {
            System.out.println("Error: Author name cannot be empty.");
            return;
        }
        
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine().trim();
        if (isbn.isEmpty()) {
            System.out.println("Error: ISBN cannot be empty.");
            return;
        }
        
        System.out.print("Enter publication year: ");
        int year;
        try {
            year = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            java.time.Year currentYear = java.time.Year.now();
            if (year < 1000 || year > currentYear.getValue()) {
                System.out.println("Error: Please enter a valid publication year (1000-" + currentYear.getValue() + ").");
                return;
            }
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            System.out.println("Error: Please enter a valid year.");
            return;
        }
        
        Book newBook = new Book(title, author, isbn, year);
        
        if (catalog.addBook(newBook)) {
            System.out.println("Book added successfully!");
        } else {
            System.out.println("Error: A book with this ISBN already exists in the catalog.");
        }
    }
    
    private void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.println("1. Search by title");
        System.out.println("2. Search by author");
        System.out.print("Enter your choice (1-2): ");
        
        int searchChoice = getChoice();
        List<Book> results;
        
        switch (searchChoice) {
            case 1:
                System.out.print("Enter title to search: ");
                String title = scanner.nextLine().trim();
                results = catalog.searchByTitle(title);
                System.out.println("\nSearch results for title containing '" + title + "':");
                break;
            case 2:
                System.out.print("Enter author to search: ");
                String author = scanner.nextLine().trim();
                results = catalog.searchByAuthor(author);
                System.out.println("\nSearch results for author containing '" + author + "':");
                break;
            default:
                System.out.println("Invalid search option.");
                return;
        }
        
        catalog.displayBooks(results);
    }
    
    private void listAllBooks() {
        System.out.println("\n--- All Books ---");
        List<Book> allBooks = catalog.getAllBooks();
        catalog.displayBooks(allBooks);
    }
    
    private void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        
        if (catalog.isEmpty()) {
            System.out.println("No books available to delete.");
            return;
        }
        
        System.out.println("1. Delete by ISBN");
        System.out.println("2. Search and delete");
        System.out.print("Enter your choice (1-2): ");
        
        int deleteChoice = getChoice();
        
        switch (deleteChoice) {
            case 1:
                deleteByIsbn();
                break;
            case 2:
                searchAndDelete();
                break;
            default:
                System.out.println("Invalid delete option.");
        }
    }
    
    private void deleteByIsbn() {
        System.out.print("Enter ISBN of the book to delete: ");
        String isbn = scanner.nextLine().trim();
        
        if (isbn.isEmpty()) {
            System.out.println("Error: ISBN cannot be empty.");
            return;
        }
        
        if (catalog.deleteBookByIsbn(isbn)) {
            System.out.println("Book deleted successfully!");
        } else {
            System.out.println("Error: No book found with ISBN '" + isbn + "'.");
        }
    }
    
    private void searchAndDelete() {
        System.out.println("Search for the book to delete:");
        System.out.println("1. Search by title");
        System.out.println("2. Search by author");
        System.out.print("Enter your choice (1-2): ");
        
        int searchChoice = getChoice();
        List<Book> results;
        String searchType;
        
        switch (searchChoice) {
            case 1:
                System.out.print("Enter title to search: ");
                String title = scanner.nextLine().trim();
                results = catalog.searchByTitle(title);
                searchType = "title containing '" + title + "'";
                break;
            case 2:
                System.out.print("Enter author to search: ");
                String author = scanner.nextLine().trim();
                results = catalog.searchByAuthor(author);
                searchType = "author containing '" + author + "'";
                break;
            default:
                System.out.println("Invalid search option.");
                return;
        }
        
        if (results.isEmpty()) {
            System.out.println("No books found for " + searchType + ".");
            return;
        }
        
        System.out.println("\nSearch results for " + searchType + ":");
        catalog.displayBooks(results);
        
        System.out.print("\nEnter the number of the book to delete (1-" + results.size() + "): ");
        int bookChoice = getChoice();
        
        if (bookChoice < 1 || bookChoice > results.size()) {
            System.out.println("Invalid book number.");
            return;
        }
        
        Book bookToDelete = catalog.getBookByIndex(results, bookChoice - 1);
        if (bookToDelete != null) {
            System.out.println("\nYou are about to delete:");
            System.out.println(bookToDelete);
            System.out.print("Are you sure? (y/N): ");
            
            String confirmation = scanner.nextLine().trim().toLowerCase();
            if (confirmation.equals("y") || confirmation.equals("yes")) {
                if (catalog.deleteBookByIsbn(bookToDelete.getIsbn())) {
                    System.out.println("Book deleted successfully!");
                } else {
                    System.out.println("Error: Failed to delete the book.");
                }
            } else {
                System.out.println("Delete operation cancelled.");
            }
        } else {
            System.out.println("Error: Could not retrieve the selected book.");
        }
    }
    
    public static void main(String[] args) {
        LibraryCatalogApp app = new LibraryCatalogApp();
        app.run();
    }
}