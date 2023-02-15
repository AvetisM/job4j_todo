package ru.job4j.todo.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TimeZoneService;
import ru.job4j.todo.service.UserDBService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserDBService userDBService;
    private final TimeZoneService timeZoneService;

    @GetMapping("/formAdd")
    public String formAdd(Model model) {
        model.addAttribute("availableZones", timeZoneService.getAvailableZones());
        return "user/add";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user,
                               @RequestParam("timeZoneId") String timeZoneId) {
        Optional<User> regUser = userDBService.add(user, timeZoneId);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Login is already taken");
            model.addAttribute("fail", true);
            return "user/add";
        }
        return "user/login";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb =
                userDBService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (userDb.isEmpty()) {
            return "redirect:/user/loginPage?fail=true";
        }
        HttpSession httpSession = req.getSession();
        httpSession.setAttribute("user", userDb.get());
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/user/loginPage";
    }

    @GetMapping("/formDetail/{login}/{password}")
    public String formDetail(Model model,
                             @PathVariable("login") String login,
                             @PathVariable("password") String password) {
        Optional<User> userDb =
                userDBService.findByLoginAndPassword(login, password);
        if (userDb.isEmpty()) {
            model.addAttribute("message", "Failed to find a user.");
            return "error";
        }
        User user = userDb.get();
        model.addAttribute("user", user);
        model.addAttribute("availableZones", timeZoneService.getAvailableZones());
        return "user/detail";
    }

    @PostMapping("/update")
    public String update(Model model,
                         @ModelAttribute User user) {
        if (!userDBService.update(user)) {
            model.addAttribute("message", "Failed to update user.");
            return "general/error";
        }
        return "redirect:/tasks";
    }
}
