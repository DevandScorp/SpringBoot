package com.springboot.controller;

import com.springboot.entity.User;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model){
        /**
         * Дважды  введенный пароль
         */
        if(user.getPassword()!=null && !user.getPassword().equals(user.getPassword2())){
            model.addAttribute("samePasswordError","Passwords are different");
        }
        if(bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        if(!userService.addUser(user)){
            model.addAttribute("message","User exists");
            return "registration";
        }
        return "redirect:/login";
    }
    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code){
        boolean isActivated = userService.activateUser(code);
        if(isActivated)model.addAttribute("message","User was successfully activated");
        else model.addAttribute("message","Activation code not found");
        return "login";
    }
}
