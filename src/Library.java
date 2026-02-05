import java.util.ArrayList;
import java.util.Comparator;

public class Library {

    private ArrayList<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
    }

    public void showAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }

        books.stream()
                .sorted(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER))
                .forEach(System.out::println);
    }

    public void searchBookByTitle(String title) {
        String q = (title == null) ? "" : title.trim().toLowerCase();

        boolean found = books.stream()
                .anyMatch(b -> b.getTitle().toLowerCase().contains(q));

        if (!found) {
            System.out.println("Book not found.");
            return;
        }

        books.stream()
                .filter(b -> b.getTitle().toLowerCase().contains(q))
                .forEach(System.out::println);
    }

    public void showAvailableBooks() {
        boolean any = books.stream().anyMatch(Book::isAvailable);

        if (!any) {
            System.out.println("No available books.");
            return;
        }

        books.stream()
                .filter(Book::isAvailable)
                .forEach(System.out::println);
    }

    public void searchByTitle(String title) {
        searchBookByTitle(title);
    }

    public void sortByYear() {
        if (books.isEmpty()) {
            System.out.println("No books in library.");
            return;
        }

        books.stream()
                .sorted(Comparator.comparingInt(Book::getYear))
                .forEach(System.out::println);
    }
}