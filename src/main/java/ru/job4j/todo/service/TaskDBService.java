package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.Store;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskDBService implements TaskService {

    private final Store taskStore;

    public void add(Task task) {
        taskStore.add(task);
    }

    public boolean update(Task task) {
        return taskStore.update(task);
    }

    public boolean delete(Task task) {
        return taskStore.delete(task.getId());
    }

    public Collection<Task> findAll() {
        return taskStore.findAll();
    }

    public Optional<Task> findById(int id) {
        return taskStore.findById(id);
    }

    public Collection<Task> findByDone(boolean done) {
        return taskStore.findByDone(done);
    }

    public boolean complete(int id) {
        return taskStore.complete(id);
    }
}
