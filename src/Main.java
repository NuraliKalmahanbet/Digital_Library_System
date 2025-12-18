public class Main {
    public static void main(String[] args) {
        Book book1 = new Book("1984", "George Orwell", "12347", true);
        Book book2 = new Book("Brave New World", "Aldous Huxley", "54321", true);
        Book book3 = new Book("1984", "George Orwell", "12345", true);

        System.out.println(book1.equals(book3));
        System.out.println(book1.equals(book2));

        Library library = new Library("City Library");
        library.addBook(book1);
        library.addBook(book2);

        library.displayBooks();

        LibraryUser user1 = new LibraryUser("Alice", 1);
        LibraryUser user2 = new LibraryUser("Bob", 2);

        user1.displayInfo();
        user2.displayInfo();
    }
}
