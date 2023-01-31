package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.util.CrudRepository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDBStore {

    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD =
            "FROM User as u WHERE u.login = :fLogin and u.password = :fPassword";

    private static final String ADD_USER =
            "INSERT INTO todo_user (name, login, password) "
                           + "values (:fName, :fLogin, :fPassword)";
    private final CrudRepository crudRepository;

    public Optional<User> add(User user) {
        crudRepository.run(session -> session.persist(user));
        return Optional.of(user);
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional(
                FIND_USER_BY_LOGIN_AND_PASSWORD, User.class,
                Map.of("fLogin", login,
                        "fPassword", password)
        );
    }
}
