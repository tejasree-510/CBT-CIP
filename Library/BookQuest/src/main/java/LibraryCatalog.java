import java.util.ArrayList;
import java.util.List;

public class LibraryCatalog {
    private List<Book> books;
    
    public LibraryCatalog() {
        this.books = new ArrayList<>();
    }
    
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        
        // Check if book with same ISBN already exists
        for (Book existingBook : books) {
            if (existingBook.getIsbn().equals(book.getIsbn())) {
                return false; // Book already exists
            }
        }
        
        books.add(book);
        return true;
    }
    
    public List<Book> searchByTitle(String title) {
        List<Book> results = new ArrayList<>();
        if (title == null || title.trim().isEmpty()) {
            return results;
        }
        
        String searchTitle = title.toLowerCase().trim();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(searchTitle)) {
                results.add(book);
            }
        }
        
        return results;
    }
    
    public List<Book> searchByAuthor(String author) {
        List<Book> results = new ArrayList<>();
        if (author == null || author.trim().isEmpty()) {
            return results;
        }
        
        String searchAuthor = author.toLowerCase().trim();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(searchAuthor)) {
                results.add(book);
            }
        }
        
        return results;
    }
    
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }
    
    public int getTotalBooks() {
        return books.size();
    }
    
    public boolean isEmpty() {
        return books.isEmpty();
    }
    
    public boolean deleteBookByIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getIsbn().equals(isbn.trim())) {
                books.remove(i);
                return true;
            }
        }
        
        return false; // Book not found
    }
    
    public Book getBookByIndex(List<Book> bookList, int index) {
        if (index < 0 || index >= bookList.size()) {
            return null;
        }
        return bookList.get(index);
    }
    
    public void displayBooks(List<Book> booksToDisplay) {
        if (booksToDisplay.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        
        System.out.println("\n" + "=".repeat(80));
        System.out.println("LIBRARY CATALOG");
        System.out.println("=".repeat(80));
        
        for (int i = 0; i < booksToDisplay.size(); i++) {
            System.out.printf("%d. %s%n", (i + 1), booksToDisplay.get(i));
        }
        
        System.out.println("=".repeat(80));
        System.out.printf("Total books displayed: %d%n", booksToDisplay.size());
    }
}