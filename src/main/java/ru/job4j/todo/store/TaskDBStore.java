package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class TaskDBStore implements Store {

    public static final Logger LOG = LoggerFactory.getLogger(TaskDBStore.class.getName());
    private static final String REPLACE_TASK =
            "UPDATE Task "
                    + "SET description = :fDescription, done = :fDone "
                    + "WHERE id = :fId";
    private static final String COMPLETE_TASK =
            "UPDATE Task "
                    + "SET done = true "
                    + "WHERE id = :fId";
    private static final String DELETE_TASK = "DELETE Task WHERE id = :fId";
    private static final String FIND_ALL_TASKS = "FROM Task";
    private static final String FIND_TASK_BY_ID = "FROM Task as t WHERE t.id = :fId";
    private static final String FIND_TASK_BY_DONE = "FROM Task as t WHERE t.done = :fDone";
    private final SessionFactory sf;

    @Override
    public Task add(Task task) {
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean rls = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(REPLACE_TASK)
                    .setParameter("fId", task.getId())
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fDone", task.isDone())
                    .executeUpdate();
            session.getTransaction().commit();
            rls = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }

        return rls;
    }

    @Override
    public boolean delete(int id) {
        boolean rls = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(DELETE_TASK)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            rls = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }

        return rls;
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        List<Task> rls = new ArrayList<>();
        try {

            Query<Task> query = session.createQuery(FIND_ALL_TASKS, Task.class);
            rls = query.list();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return rls;
    }

    @Override
    public Optional<Task> findById(int id) {
        Optional rls = Optional.empty();
        Session session = sf.openSession();
        try {
            Query<Task> query = session.createQuery(FIND_TASK_BY_ID, Task.class);
            query.setParameter("fId", id);
            rls = Optional.of(query.uniqueResult());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return rls;
    }

    @Override
    public List<Task> findByDone(boolean done) {
        Session session = sf.openSession();
        List<Task> rls = new ArrayList<>();
        try {
            Query<Task> query = session.createQuery(FIND_TASK_BY_DONE, Task.class);
            query.setParameter("fDone", done);
            rls = query.list();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return rls;
    }

    @Override
    public boolean complete(int id) {
        boolean rls = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(COMPLETE_TASK)
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            rls = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        } finally {
            session.close();
        }
        return rls;
    }

}
