package com.springboot.controller;


import com.springboot.entity.Role;
import com.springboot.entity.User;
import com.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
/**
 * Но этого недостаточно.Нужно еще поставить в WebSecurityConfig Необходимую аннотацию
 */
public class UserController {
    /**
     * Можно над классом,можно над методами.Если над классом,то все
     * методы этого класса получат дополнительный родительский адрес
     */
    @Autowired
    private UserRepository userRepository;
    @GetMapping
    public String userList(Model model){
        model.addAttribute("users",userRepository.findAll());
        return "userList";
    }

    /**
     * Получаем через идентификатор.Указав @PathVariable Log id можно получить id
     * Но Spring умнее,поэтому можно просто написать @PathVariable User user и оно сразу будет импортировать
     * из базы данных User
     * @PathVariable-позволяет получить параметры из строки запроса
     * @return
     */
    @GetMapping("{user}")
    public String editForm(@PathVariable User user,Model model){
        model.addAttribute("user",user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }
    @PostMapping
    public String userSave(
            @RequestParam String username,
            @RequestParam Map<String,String> form,
            @RequestParam("UserId") User user){
        user.setName(username);
        System.out.println("All ModelMap");
        for(Map.Entry<String,String> map: form.entrySet()){
            System.out.println(map.getKey() + " ---- " + map.getValue());
        }
        /**
         * Т.к. в мапе могут быть и другие значения,то нам нужно это отфильтровать
         */
        Set<String> roles = Arrays.stream(Role.values())
                                         .map(Role::name)
                                         .collect(Collectors.toSet());
        user.getRoles().clear();
        for(String key:form.keySet()){
           if(roles.contains(key)){
               user.getRoles().add(Role.valueOf(key));
           }
        }
        userRepository.save(user);
        return "redirect:/user";
    }
}
