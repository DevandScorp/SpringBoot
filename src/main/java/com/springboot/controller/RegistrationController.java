package com.springboot.controller;

import com.springboot.entity.CaptchaResponseDto;
import com.springboot.entity.User;
import com.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

import static com.springboot.config.MvcConfig.CAPTCHA_URL;

@Controller
public class RegistrationController {
    @Autowired
    private UserService userService;
    @Value("${recaptcha.secret}")
    private String secret;
    @Autowired
    private RestTemplate restTemplate;
    @GetMapping("/registration")
    public String registration(){
        return "registration";
    }
    @PostMapping("/registration")
    public String addUser(@RequestParam("g-recaptcha-response") String recaptchaResponse,
                          @Valid User user,
                          BindingResult bindingResult, Model model){
        /**
         * Дважды  введенный пароль
         */
        String format = String.format(CAPTCHA_URL, secret, recaptchaResponse);
        CaptchaResponseDto captchaResponseDto = restTemplate.postForObject(format,
                Collections.emptyList()/**Это то,что мы отдаем нашим запросом.Т.к. тут нам ничего не надо,то просто
                 оставляем пустой лист*/,
                CaptchaResponseDto.class

        );
        if(!captchaResponseDto.isSuccess()){
            model.addAttribute("captchaError","fill Captcha");
        }
        if(user.getPassword()!=null && !user.getPassword().equals(user.getPassword2())){
            model.addAttribute("samePasswordError","Passwords are different");
        }
        if(bindingResult.hasErrors() || !captchaResponseDto.isSuccess()){
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
