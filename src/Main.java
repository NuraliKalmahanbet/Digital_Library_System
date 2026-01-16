import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.InputMismatchException;
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
            System.out.println("6. Database");
            System.out.println("0. Exit");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }

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
                    int year;
                    try {
                        year = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid year. Please enter a number.");
                        scanner.nextLine();
                        break;
                    }

                    library.addBook(new Book(title, author, year));
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

                case 6:
                    dbMenu(scanner);
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    static void dbMenu(Scanner scanner) {
        while (true) {
            System.out.println("\nDatabase Menu");
            System.out.println("1. Insert book");
            System.out.println("2. Select books");
            System.out.println("3. Update availability");
            System.out.println("4. Delete book");
            System.out.println("5. Insert user");
            System.out.println("6. Select users");
            System.out.println("7. Update user");
            System.out.println("8. Delete user");
            System.out.println("0. Back");
            System.out.print("Choose option: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a number.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1 -> dbInsertBook(scanner);
                case 2 -> dbSelectBooks();
                case 3 -> dbUpdateAvailability(scanner);
                case 4 -> dbDeleteBook(scanner);
                case 5 -> dbInsertUser(scanner);
                case 6 -> dbSelectUsers();
                case 7 -> dbUpdateUser(scanner);
                case 8 -> dbDeleteUser(scanner);
                case 0 -> { return; }
                default -> System.out.println("Invalid option.");
            }
        }
    }

    static void dbInsertBook(Scanner scanner) {
        try (Connection conn = DB.connect()) {
            System.out.print("Title: ");
            String title = scanner.nextLine();

            System.out.print("Author: ");
            String author = scanner.nextLine();

            System.out.print("Year: ");
            int year;
            try {
                year = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid year. Please enter a number.");
                scanner.nextLine();
                return;
            }

            System.out.print("Available (true/false): ");
            boolean available;
            String availableInput = scanner.nextLine().trim().toLowerCase();
            if (availableInput.equals("true")) {
                available = true;
            } else if (availableInput.equals("false")) {
                available = false;
            } else {
                System.out.println("Invalid input. Enter true or false.");
                return;
            }

            String sql = "INSERT INTO books (title, author, year, available) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setString(2, author);
                ps.setInt(3, year);
                ps.setBoolean(4, available);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void dbSelectBooks() {
        try (Connection conn = DB.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM books ORDER BY id")) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("title") + " | " +
                                rs.getString("author") + " | " +
                                rs.getInt("year") + " | " +
                                rs.getBoolean("available")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void dbUpdateAvailability(Scanner scanner) {
        try (Connection conn = DB.connect()) {
            System.out.print("Book id: ");
            int id;
            try {
                id = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid id. Please enter a number.");
                scanner.nextLine();
                return;
            }

            System.out.print("Available (true/false): ");
            boolean available;
            String availableInput = scanner.nextLine().trim().toLowerCase();
            if (availableInput.equals("true")) {
                available = true;
            } else if (availableInput.equals("false")) {
                available = false;
            } else {
                System.out.println("Invalid input. Enter true or false.");
                return;
            }

            String sql = "UPDATE books SET available = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setBoolean(1, available);
                ps.setInt(2, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void dbDeleteBook(Scanner scanner) {
        try (Connection conn = DB.connect()) {
            System.out.print("Book id: ");
            int id;
            try {
                id = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid id. Please enter a number.");
                scanner.nextLine();
                return;
            }

            String sql = "DELETE FROM books WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void dbInsertUser(Scanner scanner) {
        try (Connection conn = DB.connect()) {
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            String sql = "INSERT INTO library_users (name, email) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void dbSelectUsers() {
        try (Connection conn = DB.connect();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM library_users ORDER BY id")) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void dbUpdateUser(Scanner scanner) {
        try (Connection conn = DB.connect()) {
            System.out.print("User id: ");
            int id;
            try {
                id = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid id. Please enter a number.");
                scanner.nextLine();
                return;
            }

            System.out.print("New name: ");
            String name = scanner.nextLine();

            System.out.print("New email: ");
            String email = scanner.nextLine();

            String sql = "UPDATE library_users SET name = ?, email = ? WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setInt(3, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void dbDeleteUser(Scanner scanner) {
        try (Connection conn = DB.connect()) {
            System.out.print("User id: ");
            int id;
            try {
                id = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid id. Please enter a number.");
                scanner.nextLine();
                return;
            }

            String sql = "DELETE FROM library_users WHERE id = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}