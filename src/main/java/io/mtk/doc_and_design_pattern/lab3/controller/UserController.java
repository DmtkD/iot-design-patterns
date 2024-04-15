package io.mtk.doc_and_design_pattern.lab3.controller;

import io.mtk.doc_and_design_pattern.lab3.model.User;
import io.mtk.doc_and_design_pattern.lab3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "")
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "index_user";
    }

    @GetMapping(value = "/showNewUserForm")
    public String showNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "new_user";
    }

    @PostMapping(value = "")
    public String addUser(@RequestBody @ModelAttribute("user") User user) {
        userService.create(user);
        return "redirect:/api/user";
    }

    @GetMapping(value = "/showFormForUpdate/{userId}")
    public String showFormForUpdate(Model model, @PathVariable("userId") Integer userId) {
        User user = userService.findById(userId);
        model.addAttribute("user", user);
        return "update_user";
    }

    @PutMapping(value = "")
    public String updateUser(@RequestBody @ModelAttribute("user") User user) {
        userService.update(user.getId(), user);
        return "redirect:/api/user";
    }

    @DeleteMapping(value = "/{userId}")
    public String deleteUser(@PathVariable("userId") Integer userId) {
        userService.delete(userId);
        return "redirect:/api/user";
    }
}