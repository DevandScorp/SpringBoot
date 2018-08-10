package com.springboot.controller;

import com.springboot.entity.Message;
import com.springboot.entity.User;
import com.springboot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MessageRepository messageRepository;

    @GetMapping("/")
    public String greeting(@RequestParam(name="name",
                            required=false,
                            defaultValue="World") String name,
                            Map<String,Object> model) {
        model.put("name", name);
        return "greeting";
    }
    @GetMapping("/main")
    public String main(@RequestParam(required = false) String filter, Model model){
        Iterable<Message> all = messageRepository.findAll();
        if (filter != null && !filter.isEmpty()) {
            all = messageRepository.findByTag(filter);
        } else {
            all = messageRepository.findAll();
        }
        model.addAttribute("sometext","It is the main page");
        model.addAttribute("messages",all);
        return "main";
    }
    @PostMapping("/main")
    public String add(@AuthenticationPrincipal User user,
                      @RequestParam String text,
                      @RequestParam String tag,
                      Map<String,Object> model){
        Message message = new Message();
        message.setText(text);
        message.setTag(tag);
        message.setAuthor(user);
        messageRepository.save(message);
        Iterable<Message> all = messageRepository.findAll();
        model.put("sometext","It is the main page");
        model.put("messages",all);
        return "main";
    }

}
