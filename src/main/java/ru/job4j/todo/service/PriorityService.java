package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.store.PriorityDBStore;
import ru.job4j.todo.util.Response;

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

    public Response checkPriority(Optional<Priority> priorityOptional) {
        Response response = new Response(true, "success");
        if (!priorityOptional.isPresent() || priorityOptional.isEmpty()) {
            response.setMessage("Failed to find priority.");
            response.setResult(false);
       }
        return response;
    }
}
