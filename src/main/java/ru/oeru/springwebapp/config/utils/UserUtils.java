package ru.oeru.springwebapp.config.utils;

import org.springframework.ui.Model;
import ru.oeru.springwebapp.model.User;

public class UserUtils {
        public static void formValidation(User user, Model model){
            if (user.getUsername().equals("")
                    || user.getPassword().equals("")
                    || user.getPasswordConfirm().equals("")
                    || !user.getPassword().equals(user.getPasswordConfirm())) {
                model.addAttribute("formError", "Form filling error");
            }
        }
}
