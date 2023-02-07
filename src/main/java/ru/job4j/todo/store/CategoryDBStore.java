package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.util.CrudRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CategoryDBStore {

    public static final Logger LOG = LoggerFactory.getLogger(CategoryDBStore.class.getName());

    private static final String FIND_ALL_CATEGORIES = "FROM Category";
    private static final String FIND_CATEGORY_BY_ID = "FROM Category WHERE id = :fId";
    private final CrudRepository crudRepository;

    public List<Category> findAll() {
        return crudRepository.query(FIND_ALL_CATEGORIES, Category.class);
    }

    public Optional<Category> findById(int id) {
        return crudRepository.optional(
                FIND_CATEGORY_BY_ID, Category.class,
                Map.of("fId", id)
        );
    }
}
