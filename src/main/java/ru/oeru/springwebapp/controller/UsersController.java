package ru.oeru.springwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.oeru.springwebapp.dao.UserDao;
import ru.oeru.springwebapp.model.User;
import ru.oeru.springwebapp.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UsersController {
    private UserService userService;
    private List<User> users;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public String usersPage(Model model){
        users = userService.listUsers();
        model.addAttribute("users", users);
        return "users";
    }
    @PostMapping
    public String createUser(@ModelAttribute("user") User user, Model model){
        userService.add(user);
        System.out.println(user.getFirstName());
        System.out.println(user.getLastName());
        return "redirect:/users";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
        return "new";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.find(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") Long id) {
        userService.update(user);
        return "redirect:/users";
    }
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable("id") Long id){
        userService.removeById(id);
        return "redirect:/users";
    }
}
