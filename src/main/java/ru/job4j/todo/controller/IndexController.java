package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.todo.service.SessionService;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {
    @GetMapping("/index")
    public String index(Model model, HttpSession session) {
        SessionService.modelAddUser(model, session);
        return "user/login";
    }
}