package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Priority;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.PriorityService;
import ru.job4j.todo.util.Response;
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
        model.addAttribute("priorities", priorityService.getAllPriorities());
        return "task/detail";
    }

    @PostMapping("/create")
    public String create(Model model,
                         @ModelAttribute Task task,
                         @RequestParam("priority.id") int priorityId,
                         HttpSession httpSession) {

        Optional<Priority> priorityOptional = priorityService.findById(priorityId);
        Response response = priorityService.checkPriority(priorityOptional);
        if (!response.isResult()) {
            model.addAttribute("message", response.getMessage());
            return "general/error";
        }
        task.setUser((User) httpSession.getAttribute("user"));
        task.setPriority(priorityOptional.get());
        taskDBService.add(task);
        return "redirect:/tasks";
    }

    @PostMapping("/update")
    public String update(Model model,
                         @ModelAttribute Task task,
                         @RequestParam("priority.id") int priorityId) {

        Optional<Priority> priorityOptional = priorityService.findById(priorityId);
        Response response = priorityService.checkPriority(priorityOptional);
        if (!response.isResult()) {
            model.addAttribute("message", response.getMessage());
            return "general/error";
        }
        task.setPriority(priorityOptional.get());
        boolean result = taskDBService.update(task);
        if (!result) {
            model.addAttribute("message", "Failed to update task.");
            return "general/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/complete")
    public String complete(Model model, @ModelAttribute Task task) {
        task.setDone(true);
        boolean result = taskDBService.complete(task.getId());
        if (!result) {
            model.addAttribute("message", "Failed to complete the task.");
            return "general/error";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/delete")
    public String delete(Model model, @ModelAttribute Task task) {
        boolean result = taskDBService.delete(task);
        if (!result) {
            model.addAttribute("message", "Failed to delete task.");
            return "general/error";
        }
        return "redirect:/tasks";
    }

}
