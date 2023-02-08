package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.util.SessionService;
import ru.job4j.todo.service.TaskDBService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/tasks")
public class TaskController {

    private final TaskDBService taskDBService;
    private final PriorityService priorityService;
    private final CategoryService categoryService;

    @GetMapping
    public String tasks(Model model, HttpSession httpSession) {
        model.addAttribute("tasks", taskDBService.findAll());
        SessionService.modelAddUser(model, httpSession);
        return "task/tasks";
    }

    @GetMapping("/{done}")
    public String tasksDone(Model model, HttpSession httpSession,
                            @PathVariable("done") boolean done) {
        model.addAttribute("tasks", taskDBService.findByDone(done));
        SessionService.modelAddUser(model, httpSession);
        return "task/tasks";
    }

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        model.addAttribute("priorities", priorityService.getAllPriorities());
        model.addAttribute("categories", categoryService.findAll());
        return "task/add";
    }

    @GetMapping("/formDetail/{id}")
    public String formDetail(Model model, @PathVariable("id") int id) {
        Optional<Task> task = taskDBService.findById(id);
        if (task.isEmpty()) {
            model.addAttribute("message", "Failed to find a task.");
            return "error";
        }
        model.addAttribute("task", task.get());
        model.addAttribute("priorities", priorityService.getAllPriorities());
        model.addAttribute("categories", categoryService.findAll());
        return "task/detail";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @ModelAttribute Task task,
                         @RequestParam("priority.id") int priorityId,
                         @RequestParam("categoryList") String[] categoryIdArray,
                         HttpSession httpSession) {
        task.setUser((User) httpSession.getAttribute("user"));
        if (!taskDBService.add(task, priorityId, categoryIdArray)) {
            model.addAttribute("message", "Failed to create task.");
            return "general/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(Model model,
                         @ModelAttribute Task task,
                         @RequestParam("priority.id") int priorityId) {
        if (!taskDBService.update(task, priorityId)) {
            model.addAttribute("message", "Failed to update task.");
            return "general/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/complete")
    public String complete(Model model, @ModelAttribute Task task) {
        task.setDone(true);
        if (!taskDBService.complete(task.getId())) {
            model.addAttribute("message", "Failed to complete the task.");
            return "general/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String delete(Model model, @ModelAttribute Task task) {
        if (!taskDBService.delete(task)) {
            model.addAttribute("message", "Failed to delete task.");
            return "general/error";
        }
        return "redirect:/tasks";
    }

}
