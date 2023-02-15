package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;
import ru.job4j.todo.util.CrudRepository;

import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;

@Repository
@AllArgsConstructor
public class UserDBStore {

    public static final Logger LOG = LoggerFactory.getLogger(UserDBStore.class.getName());
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD =
            "FROM User as u WHERE u.login = :fLogin and u.password = :fPassword";
    private final CrudRepository crudRepository;

    public Optional<User> add(User user) {
        try {
            crudRepository.run(session -> session.persist(user));
            return Optional.of(user);
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public boolean update(User user) {
        try {
            crudRepository.run(session -> session.merge(user));
            return true;
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return false;
        }
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        try {
            return crudRepository.optional(
                    FIND_USER_BY_LOGIN_AND_PASSWORD, User.class,
                    Map.of("fLogin", login,
                            "fPassword", password)
            );
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            return Optional.empty();
        }
    }
}
