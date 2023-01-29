package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserDBService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {

    private final UserDBService userDBService;

    public UserController(UserDBService userDBService) {
        this.userDBService = userDBService;
    }

    @GetMapping("/formAddUser")
    public String formAddUser(Model model) {
        return "addTask";
    }

    @PostMapping("/createUser")
    public String createUser(@ModelAttribute User user) {
        userDBService.add(user);
        return "redirect:/tasks";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userDBService.add(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            model.addAttribute("fail", true);
            return "addUser";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userDBService.findByLogin(user.getLogin());
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}
