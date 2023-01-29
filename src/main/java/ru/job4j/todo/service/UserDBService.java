package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.UserDBStore;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserDBService {

    private final UserDBStore userStore;

    public Optional<User> add(User user) {
       return userStore.add(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }

}
