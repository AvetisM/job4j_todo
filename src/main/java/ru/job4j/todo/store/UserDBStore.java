package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserDBStore {

    public static final Logger LOG = LoggerFactory.getLogger(TaskDBStore.class.getName());
    private static final String FIND_USER_BY_LOGIN_AND_PASSWORD =
            "FROM User as u WHERE u.login = :fLogin and u.password = :fPassword";

    private final SessionFactory sf;

    public Optional<User> add(User user) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            return Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }

        return Optional.empty();
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        Session session = sf.openSession();
        try {
            Query<User> query = session.createQuery(FIND_USER_BY_LOGIN_AND_PASSWORD, User.class);
            query.setParameter("fLogin", login);
            query.setParameter("fPassword", password);
            User user = query.uniqueResult();
            return Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
            return Optional.empty();
        } finally {
            session.close();
        }
    }
}
