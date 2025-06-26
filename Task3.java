import java.util.Scanner;
import java.util.*;
class Book {
    private String id;
    private String title;
    private String author;
    private boolean isIssued;

    public Book(String id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isIssued = false;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public boolean isIssued() { return isIssued; }

    public void issue() { isIssued = true; }
    public void returned() { isIssued = false; }

    public String toString() {
        return "[" + id + "] " + title + " by " + author + (isIssued ? " (Issued)" : " (Available)");
    }
}



 class User {
     private String userId;
     private String name;
     private List<Book> borrowedBooks = new ArrayList<>();

     public User(String userId, String name) {
         this.userId = userId;
         this.name = name;
     }

     public String getUserId() { return userId; }
     public String getName() { return name; }

     public void borrowBook(Book book) {
         borrowedBooks.add(book);
         book.issue();
     }

     public void returnBook(Book book) {
         borrowedBooks.remove(book);
         book.returned();
     }

     public List<Book> getBorrowedBooks() { return borrowedBooks; }

     public String toString() {
         return userId + " - " + name;
     }
 }


 class Library {
     private Map<String, Book> books = new HashMap<>();
     private Map<String, User> users = new HashMap<>();

     public void addBook(Book book) {
         books.put(book.getId(), book);
     }

     public void registerUser(User user) {
         users.put(user.getUserId(), user);
     }

     public void showAllBooks() {
         for (Book book : books.values()) {
             System.out.println(book);
         }
     }

     public void showUsers() {
         for (User user : users.values()) {
             System.out.println(user);
         }
     }

     public void issueBook(String bookId, String userId) {
         Book book = books.get(bookId);
         User user = users.get(userId);

         if (book == null) {
             System.out.println(" Book not found.");
         } else if (user == null) {
             System.out.println(" User not registered.");
         } else if (book.isIssued()) {
             System.out.println("⚠ Book already issued.");
         } else {
             user.borrowBook(book);
             System.out.println(" Book issued to " + user.getName());
         }
     }

     public void returnBook(String bookId, String userId) {
         Book book = books.get(bookId);
         User user = users.get(userId);

         if (book == null || user == null) {
             System.out.println(" Invalid user or book.");
         } else if (!user.getBorrowedBooks().contains(book)) {
             System.out.println("⚠ This book was not borrowed by the user.");
         } else {
             user.returnBook(book);
             System.out.println(" Book returned successfully.");
         }
     }

     public void showUserBorrowedBooks(String userId) {
         User user = users.get(userId);
         if (user == null) {
             System.out.println("❌ User not found.");
         } else {
             System.out.println(" Books borrowed by " + user.getName() + ":");
             for (Book b : user.getBorrowedBooks()) {
                 System.out.println("→ " + b.getTitle());
             }
         }
     }
 }



 public class Task3 {
     public static void main(String[] args) {
         Library library = new Library();
         Scanner sc = new Scanner(System.in);

         // Add demo data
         library.addBook(new Book("B101", "The Alchemist", "Paulo Coelho"));
         library.addBook(new Book("B102", "Clean Code", "Robert C. Martin"));
         library.addBook(new Book("B103", "1984", "George Orwell"));

         library.registerUser(new User("U001", "Alice"));
         library.registerUser(new User("U002", "Bob"));

         int choice;
         do {
             System.out.println("\n Welcome to BookVerse ");
             System.out.println("1. Show all books");
             System.out.println("2. Show users");
             System.out.println("3. Issue book");
             System.out.println("4. Return book");
             System.out.println("5. Show user's borrowed books");
             System.out.println("6. Exit");
             System.out.print(" Enter your choice: ");
             choice = sc.nextInt();
             sc.nextLine();  // consume newline

             switch (choice) {
                 case 1:
                     library.showAllBooks();
                     break;
                 case 2:
                     library.showUsers();
                     break;
                 case 3:
                     System.out.print("Enter Book ID: ");
                     String bookId = sc.nextLine();
                     System.out.print("Enter User ID: ");
                     String userId = sc.nextLine();
                     library.issueBook(bookId, userId);
                     break;
                 case 4:
                     System.out.print("Enter Book ID: ");
                     String returnBookId = sc.nextLine();
                     System.out.print("Enter User ID: ");
                     String returnUserId = sc.nextLine();
                     library.returnBook(returnBookId, returnUserId);
                     break;
                 case 5:
                     System.out.print("Enter User ID: ");
                     String viewUserId = sc.nextLine();
                     library.showUserBorrowedBooks(viewUserId);
                     break;
                 case 6:
                     System.out.println("Thank you for using BookVerse!");
                     break;
                 default:
                     System.out.println("❗ Invalid choice.");
             }

         } while (choice != 6);

         sc.close();
     }
 }
