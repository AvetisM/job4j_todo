package ru.job4j.todo.util;

import org.springframework.ui.Model;
import ru.job4j.todo.model.User;

import javax.servlet.http.HttpSession;

public final class SessionService {
    private SessionService() {
        throw new UnsupportedOperationException(
                "Utility class and cannot be instantiated");
    }

    public static void modelAddUser(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        model.addAttribute("user", user);
    }
}
