import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();

        Person user = new LibraryUser("Nurali", 1);
        System.out.println("Welcome, " + user.getName() + " (" + user.getRole() + ")");

        while (true) {
            System.out.println("\n--- Digital Library Menu ---");
            System.out.println("1. Add book");
            System.out.println("2. Show all books");
            System.out.println("3. Search book by title");
            System.out.println("4. Show available books");
            System.out.println("5. Sort books by year");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 0) {
                System.out.println("Goodbye!");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();

                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();

                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();

                    library.addBook(new Book(title, author, year));
                    System.out.println("Book added.");
                    break;

                case 2:
                    library.showAllBooks();
                    break;

                case 3:
                    System.out.print("Enter title to search: ");
                    String searchTitle = scanner.nextLine();
                    library.searchByTitle(searchTitle);
                    break;

                case 4:
                    library.showAvailableBooks();
                    break;

                case 5:
                    library.sortByYear();
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}