import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T entity);
    T update(T entity);
    void deleteById(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();

    default boolean existsById(ID id) {
        return findById(id).isPresent();
    }
}