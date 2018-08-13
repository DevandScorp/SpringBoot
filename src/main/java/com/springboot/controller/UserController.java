package com.springboot.controller;


import com.springboot.entity.Role;
import com.springboot.entity.User;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")

/**
 * Но этого недостаточно.Нужно еще поставить в WebSecurityConfig Необходимую аннотацию
 */
public class UserController {
    /**
     * Можно над классом,можно над методами.Если над классом,то все
     * методы этого класса получат дополнительный родительский адрес
     */
    @Autowired
    private UserService userService;

    /**
     * Если мы можем что-то отрефакторнить-рефакторим.Репозиторий можно спокойно заменить на сервис
     * Сохраняя время,которое бы ушло на дополнительную инициализацию
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userService.findAll());
        return "userList";
    }

    /**
     * Получаем через идентификатор.Указав @PathVariable Log id можно получить id
     * Но Spring умнее,поэтому можно просто написать @PathVariable User user и оно сразу будет импортировать
     * из базы данных User
     * @PathVariable-позволяет получить параметры из строки запроса
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String editForm(@PathVariable User user,Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String,String> form,
            @RequestParam("UserId") User user){

        userService.saveUser(user,username,form);
        return "redirect:/user";
    }
    @GetMapping("profile")
    public String getProfile(Model model, @AuthenticationPrincipal User user){
        model.addAttribute("name",user.getName());
        model.addAttribute("email",user.getEmail());
        return "profile";
    }
    @PostMapping("profile")
    public String updateProfile(@AuthenticationPrincipal User user,
                                @RequestParam String password,
                                @RequestParam String email){
        userService.updateProfile(user,password,email);
        return "redirect:/user/profile";
    }
}
