package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserStore;

import java.util.Collection;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDBService {

    private final UserStore userStore;

    public Optional<User> add(User user) {
       return userStore.add(user);
    }

    public boolean update(User user) {
        return userStore.update(user);
    }

    public boolean delete(User user) {
        return userStore.delete(user.getId());
    }

    public Collection<User> findAll() {
        return userStore.findAll();
    }

    public Optional<User> findById(int id) {
        return userStore.findById(id);
    }

    public Optional<User> findByLogin(String login) {
        return userStore.findByLogin(login);
    }

}
