package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.util.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskDBStore implements Store {

    public static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());

    private static final String REPLACE_TASK =
            "UPDATE Task "
                    + "SET description = :fDescription, done = :fDone, priority = :fPriority "
                    + "WHERE id = :fId";
    private static final String COMPLETE_TASK =
            "UPDATE Task "
                    + "SET done = true "
                    + "WHERE id = :fId";
    private static final String DELETE_TASK = "DELETE Task WHERE id = :fId";
    private static final String FIND_ALL_TASKS = "FROM Task as t JOIN FETCH t.priority";
    private static final String FIND_TASK_BY_ID =
            "FROM Task as t JOIN FETCH t.priority WHERE t.id = :fId";
    private static final String FIND_TASK_BY_DONE =
            "FROM Task as t JOIN FETCH t.priority  WHERE t.done = :fDone";
    private final CrudRepository crudRepository;

    @Override
    public Task add(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean update(Task task) {
        try {
            crudRepository.run(
                    REPLACE_TASK,
                    Map.of("fId", task.getId(),
                            "fDescription", task.getDescription(),
                            "fDone", task.isDone(),
                            "fPriority", task.getPriority())
            );
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            crudRepository.run(
                    DELETE_TASK,
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<Task> findAll() {
        return crudRepository.query(FIND_ALL_TASKS, Task.class);
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional(
                FIND_TASK_BY_ID, Task.class,
                Map.of("fId", id)
        );
    }

    @Override
    public List<Task> findByDone(boolean done) {
        return crudRepository.query(FIND_TASK_BY_DONE,
                Task.class,
                Map.of("fDone", done)
        );
    }

    @Override
    public boolean complete(int id) {
        try {
            crudRepository.run(
                    COMPLETE_TASK,
                    Map.of("fId", id)
            );
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }
}
