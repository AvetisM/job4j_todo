package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
public class TaskController {

    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public String tasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks";
    }

    @GetMapping("/tasks/{done}")
    public String tasksDone(Model model, @PathVariable("done") boolean done) {
        model.addAttribute("tasks", taskService.findByDone(done));
        return "tasks";
    }

    @GetMapping("/formAddTask")
    public String formAddTask(Model model) {
        return "addTask";
    }

    @GetMapping("/formDetailTask/{taskId}")
    public String formDetailTask(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "detailTask";
    }

    @PostMapping("/createTask")
    public String createTask(@ModelAttribute Task task) {
        taskService.add(task);
        return "redirect:/tasks";
    }

    @GetMapping("/formUpdateTask/{taskId}")
    public String formUpdateTask(Model model, @PathVariable("taskId") int id) {
        model.addAttribute("task", taskService.findById(id));
        return "updateTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        taskService.update(task);
        return "redirect:/tasks";
    }

    @PostMapping("/executeTask")
    public String executeTask(@ModelAttribute Task task) {
        task.setDone(true);
        taskService.update(task);
        return "redirect:/tasks";
    }

    @PostMapping("/deleteTask")
    public String deleteTask(@ModelAttribute Task task) {
        taskService.delete(task);
        return "redirect:/tasks";
    }

}
