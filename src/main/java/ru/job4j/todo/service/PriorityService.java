package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.PriorityDBStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PriorityService {

    private PriorityDBStore priorityDBStore;

    public List<Priority> getAllPriorities() {
        return priorityDBStore.findAll();
    }

    public Optional<Priority> findById(int id) {
        return priorityDBStore.findByID(id);
    }

    public boolean checkPriority(Optional<Priority> priorityOptional) {
        return priorityOptional.isPresent();
    }
}
