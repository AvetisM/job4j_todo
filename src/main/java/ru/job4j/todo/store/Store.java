package ru.job4j.todo.store;

import ru.job4j.todo.model.Task;

import java.util.List;

public interface Store {

    Task add(Task item);

    boolean update(Task item);

    boolean delete(int id);

    List<Task> findAll();

    Task findById(int id);

    List<Task> findByDone(boolean done);
}
