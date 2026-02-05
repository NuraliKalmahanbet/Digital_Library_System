import java.util.List;

public class BookService {
    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public Book create(Book book) {
        if (book == null) throw new InvalidInputException("Book is null");
        return repo.save(book);
    }

    public List<Book> getAll() {
        return repo.findAll();
    }

    public Book getById(int id) {
        return repo.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Book not found: id=" + id));
    }
}