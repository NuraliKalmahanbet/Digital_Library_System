import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryBookRepository implements BookRepository {
    private final Map<Integer, Book> data = new HashMap<>();
    private final AtomicInteger seq = new AtomicInteger(1);

    @Override
    public Book save(Book entity) {
        int id = seq.getAndIncrement();
        data.put(id, entity);
        return entity;
    }

    @Override
    public Book update(Book entity) {
        return entity;
    }

    @Override
    public void deleteById(Integer id) {
        data.remove(id);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(data.values());
    }
}