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
    private final TimeZoneService timeZoneService;

    public Optional<User> add(User user, String timeZoneId) {
        user.setUserZone(timeZoneService.getZoneIdOrDefault(timeZoneId));
        return userStore.add(user);
    }

    public boolean update(User user) {
        return userStore.update(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return userStore.findByLoginAndPassword(login, password);
    }
}
