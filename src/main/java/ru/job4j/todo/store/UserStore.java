package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class UserStore {

    public static final Logger LOG = LoggerFactory.getLogger(TaskDBStore.class.getName());
    private static final String REPLACE_USER =
            "UPDATE User "
                    + "SET name = :fName, password = :fPassword "
                    + "WHERE id = :fId";
    private static final String DELETE_USER = "DELETE User WHERE id = :fId";
    private static final String FIND_ALL_USERS = "FROM User";
    private static final String FIND_USER_BY_ID = "FROM User as u WHERE u.id = :fId";
    private static final String FIND_USER_BY_LOGIN = "FROM User as u WHERE u.login = :fLogin";

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
        }
        session.close();
        return Optional.empty();
    }

    public boolean update(User user) {
        boolean result = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(REPLACE_USER)
                    .setParameter("fId", user.getId())
                    .setParameter("fName", user.getName())
                    .setParameter("fPassword", user.getPassword())
                    .executeUpdate();
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        session.close();
        return result;
    }

    public boolean delete(int id) {
        boolean result = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(DELETE_USER)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            result = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        session.close();
        return result;
    }

    public List<User> findAll() {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_ALL_USERS, User.class);
        List<User> result = query.list();
        session.close();
        return result;
    }

    public Optional<User> findById(int id) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_USER_BY_ID, User.class);
        query.setParameter("fId", id);
        User user = query.uniqueResult();
        session.close();
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    public Optional<User> findByLogin(String login) {
        Session session = sf.openSession();
        Query<User> query = session.createQuery(FIND_USER_BY_LOGIN, User.class);
        query.setParameter("fLogin", login);
        User user = query.uniqueResult();
        session.close();
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

}
