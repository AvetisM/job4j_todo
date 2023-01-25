package ru.job4j.todo.store;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.sql.Timestamp;
import java.util.List;

@Repository
@AllArgsConstructor
public class TaskRepository implements Store {

    public static final Logger LOG = LoggerFactory.getLogger(TaskRepository.class.getName());
    private static final String REPLACE_TASK =
            "UPDATE tasks "
                    + "SET description = :fDescription, created = :fCreated, done = :fDone "
                    + "WHERE id = :fId";
    private static final String DELETE_TASK = "DELETE Item WHERE id = :fId";
    private static final String FIND_ALL_TASKS = "FROM Task";
    private static final String FIND_TASK_BY_ID = "FROM Task as t WHERE t.id = :fId";

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
        }
        session.close();
        return task;
    }

    @Override
    public boolean replace(int id, Task task) {
        boolean rls = false;
        Session session = sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(REPLACE_TASK)
                    .setParameter("fId", id)
                    .setParameter("fDescription", task.getDescription())
                    .setParameter("fCreated", Timestamp.valueOf(task.getCreated()))
                    .setParameter("fDone", task.isDone())
                    .executeUpdate();
            session.getTransaction().commit();
            rls = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOG.error(e.getMessage(), e);
        }
        session.close();
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
        }
        session.close();
        return rls;
    }

    @Override
    public List<Task> findAll() {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(FIND_ALL_TASKS, Task.class);
        List<Task> rls = query.list();
        session.close();
        return rls;
    }

    @Override
    public Task findById(int id) {
        Session session = sf.openSession();
        Query<Task> query = session.createQuery(FIND_TASK_BY_ID, Task.class);
        query.setParameter("fId", id);
        Task rls = query.uniqueResult();
        session.close();
        return rls;
    }
}
