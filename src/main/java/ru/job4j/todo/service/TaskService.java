package ru.job4j.todo.service;

import ru.job4j.todo.model.Task;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TaskService {

    boolean add(Task task, int priorityId, String[] categoryIdArray);

    boolean update(Task task, int priorityId);

    boolean delete(Task task);

    List<Task> findAll();

    Optional<Task> findById(int id);

    Collection<Task> findByDone(boolean done);

    boolean complete(int id);
}
