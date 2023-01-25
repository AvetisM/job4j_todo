package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.store.Store;

import java.util.Collection;
import java.util.List;

@Service
public class TaskService {

    private final Store taskStore;

    private TaskService(Store taskStore) {
        this.taskStore = taskStore;
    }

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
        List<Task> posts = taskStore.findAll();
        return posts;
    }

    public Task findById(int id) {
        return taskStore.findById(id);
    }
}
