package ru.oeru.springwebapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.oeru.springwebapp.config.utils.UserUtils;
import ru.oeru.springwebapp.model.User;
import ru.oeru.springwebapp.service.UserService;
import ru.oeru.springwebapp.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
	@Autowired
	UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/registration")
	public String registrationPage(Model model){
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration")
	public String registrationUser(@ModelAttribute("user") User user, Model model){
		UserUtils.formValidation(user, model);
    	if (model.containsAttribute("formError")){
			return "registration";
		} else if (userService.add(user)) {
			return "redirect:login";
		} else {
			model.addAttribute("userIsExist", "This user already exists");
			return "registration";
		}
	}

	@GetMapping("/userdetails")
	public String userdetailsPage(Model model){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		model.addAttribute(userService.findUserByUsername(username));
		return "userdetails";
	}


}