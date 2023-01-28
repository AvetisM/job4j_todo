package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskDBService;

import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskDBService taskDBService;

    @GetMapping("")
    public String tasks(Model model) {
        model.addAttribute("tasks", taskDBService.findAll());
        return "Task/tasks";
    }

    @GetMapping("/{done}")
    public String tasksDone(Model model, @PathVariable("done") boolean done) {
        model.addAttribute("tasks", taskDBService.findByDone(done));
        return "Task/tasks";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        return "Task/add";
    }

    @GetMapping("/formDetail/{id}")
    public String formDetail(Model model, @PathVariable("id") int id) {
        Optional<Task> task = taskDBService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задачу.");
            return "errorPage";
        }
        model.addAttribute("task", task.get());
        return "Task/detail";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task) {
        taskDBService.add(task);
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(Model model, @ModelAttribute Task task) {
        boolean rls = taskDBService.update(task);
        if (!rls) {
            model.addAttribute("message", "Не удалось обновить задачу.");
            return "errorPage";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/complete")
    public String complete(Model model, @ModelAttribute Task task) {
        task.setDone(true);
        boolean rls = taskDBService.complete(task.getId());
        if (!rls) {
            model.addAttribute("message", "Не удалось сделать задачу выполненной.");
            return "errorPage";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String delete(Model model, @ModelAttribute Task task) {
        boolean rls = taskDBService.delete(task);
        if (!rls) {
            model.addAttribute("message", "Не удалось удалить задачу.");
            return "errorPage";
        }
        return "redirect:/tasks";
    }

}
