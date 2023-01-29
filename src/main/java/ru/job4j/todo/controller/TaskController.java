package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.SessionService;
import ru.job4j.todo.service.TaskDBService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskDBService taskDBService;

    @GetMapping
    public String tasks(Model model, HttpSession session) {
        model.addAttribute("tasks", taskDBService.findAll());
        SessionService.modelAddUser(model, session);
        return "task/tasks";
    }

    @GetMapping("/{done}")
    public String tasksDone(Model model, HttpSession session, @PathVariable("done") boolean done) {
        model.addAttribute("tasks", taskDBService.findByDone(done));
        SessionService.modelAddUser(model, session);
        return "task/tasks";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        return "task/add";
    }

    @GetMapping("/formDetail/{id}")
    public String formDetail(Model model, @PathVariable("id") int id) {
        Optional<Task> task = taskDBService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задачу.");
            return "error";
        }
        model.addAttribute("task", task.get());
        return "task/detail";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task) {
        taskDBService.add(task);
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task) {
        boolean result = taskDBService.update(task);
        if (!result) {
            model.addAttribute("message", "Не удалось обновить задачу.");
            return "error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/complete")
    public String complete(Model model, @ModelAttribute Task task) {
        task.setDone(true);
        boolean result = taskDBService.complete(task.getId());
        if (!result) {
            model.addAttribute("message", "Не удалось сделать задачу выполненной.");
            return "error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String delete(Model model, @ModelAttribute Task task) {
        boolean result = taskDBService.delete(task);
        if (!result) {
            model.addAttribute("message", "Не удалось удалить задачу.");
            return "error";
        }
        return "redirect:/tasks";
    }

}
