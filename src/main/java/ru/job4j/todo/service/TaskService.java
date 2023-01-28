package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {

    void add(Task task);

    boolean update(Task task);

    boolean delete(Task task);

    Collection<Task> findAll();

    Optional<Task> findById(int id);

    Collection<Task> findByDone(boolean done);

    boolean complete(int id);
}
