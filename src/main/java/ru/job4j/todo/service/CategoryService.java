package ru.job4j.todo.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryDBStore;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryDBStore categoryDBStore;

    public List<Category> findAll() {
        return categoryDBStore.findAll();
    }

    public Optional<Category> findById(int id) {
        return categoryDBStore.findById(id);
    }

    public List<Category> getCategoryListByIdArray(Integer[] idArray) {
        return categoryDBStore.findByIds(idArray);
    }
}
