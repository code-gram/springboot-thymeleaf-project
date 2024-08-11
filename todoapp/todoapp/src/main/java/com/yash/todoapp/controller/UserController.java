package com.yash.todoapp.controller;

import com.yash.todoapp.dto.UserDto;
import com.yash.todoapp.entity.User;
import com.yash.todoapp.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class UserController {


    private UserService userService;
    //handler method to handle showRegistration form request

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user =  new UserDto();
        model.addAttribute("user", user);
        return "user/register";
    }

    //handler method to save user
    @PostMapping("/register/save")
    public String saveUser(@Valid @ModelAttribute("user") UserDto userDto, BindingResult result, Model model){
       User existingUser = userService.findUserByEmail(userDto.getEmail());
       if(existingUser!=null && existingUser.getEmail()!=null && !existingUser.getEmail().isEmpty()){
           result.rejectValue("email",null,"There is an account registered with same email address.");
       }
       if(result.hasErrors()){
           model.addAttribute("user",userDto);
           return "user/register";
       }
        userService.saveUser(userDto);
        return "redirect:/login?success";
    }

    //handler method to handle login request
    @GetMapping("/login")
    public String showLoginForm(){
        return "user/login";
    }


    @PostMapping("/authenticate")
    public String authenticateUser(){
        //
        return "";
    }

    //handler method to handle list of users
    @GetMapping("/users")
    public String showUserList(Model model){
        List<UserDto> users =  userService.findAllUsers();
        model.addAttribute("users",users);
        return "/user/users";

    }
    @GetMapping("/logout")
    public String logout(){
        return "redirect:/login?logout";
    }
}
