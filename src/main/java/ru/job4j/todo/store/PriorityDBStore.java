package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.util.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class PriorityDBStore {

    public static final Logger LOG = LoggerFactory.getLogger(PriorityDBStore.class.getName());

    private static final String FIND_ALL_PRIORITIES = "FROM Priority";
    private static final String FIND_PRIORITY_BY_ID = "FROM Priority as p WHERE p.id = :fId";
    private final CrudRepository crudRepository;

    public List<Priority> findAll() {
        return crudRepository.query(FIND_ALL_PRIORITIES, Priority.class);
    }

    public Optional<Priority> findByID(int id) {
        try {
            return crudRepository.optional(
                    FIND_PRIORITY_BY_ID, Priority.class,
                    Map.of("fId", id)
            );
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
