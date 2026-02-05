public class ServiceFactory {

    private ServiceFactory() {}

    public static BookService createBookService() {
        return new BookService(new InMemoryBookRepository());
    }
}