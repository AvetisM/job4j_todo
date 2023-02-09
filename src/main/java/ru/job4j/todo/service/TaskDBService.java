package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.Store;

import javax.transaction.Transactional;
import java.util.*;

@Service
@AllArgsConstructor
public class TaskDBService implements TaskService {

    private final Store taskStore;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @Transactional
    public boolean add(Task task, int priorityId, String[] categoryIdArray) {
        Optional<Priority> priorityOptional = priorityService.findById(priorityId);
        if (priorityOptional.isEmpty()) {
            return false;
        }
        Integer[] categoryIdIntArray = Arrays.stream(categoryIdArray)
                .mapToInt(Integer ::parseInt)
                .boxed()
                .toArray(Integer[]::new);
        List<Category> categories = categoryService.getCategoryListByIdArray(categoryIdIntArray);
        task.setCategories(categories);
        task.setPriority(priorityOptional.get());
        return taskStore.add(task);
    }

    public boolean update(Task task, int priorityId, String[] categoryIdArray) {
        Optional<Priority> priorityOptional = priorityService.findById(priorityId);
        if (priorityOptional.isEmpty()) {
            return false;
        }
        Integer[] categoryIdIntArray = Arrays.stream(categoryIdArray)
                .mapToInt(Integer ::parseInt)
                .boxed()
                .toArray(Integer[]::new);
        List<Category> categories = categoryService.getCategoryListByIdArray(categoryIdIntArray);
        task.setCategories(categories);
        task.setPriority(priorityOptional.get());
        return taskStore.update(task);
    }

    public boolean delete(Task task) {
        return taskStore.delete(task.getId());
    }

    public List<Task> findAll() {
        return taskStore.findAll();
    }

    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    public List<Task> findByDone(boolean done) {
        return taskStore.findByDone(done);
    }

    public boolean complete(int id) {
        return taskStore.complete(id);
    }
}
