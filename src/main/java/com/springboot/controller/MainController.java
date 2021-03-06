package com.springboot.controller;

import com.springboot.entity.Message;
import com.springboot.entity.User;
import com.springboot.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class MainController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MessageRepository messageRepository;
    /**
     * С помощью этой аннотации мы запихиваем в поле значениe,полученное из prop
     */
    @Value("${upload.path}")
    private String uploadPath;
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
    @PostMapping("/main")/**AuthenticationPrincipal берет пользователя из сессии*/
    public String add(@AuthenticationPrincipal User user,
                      @Valid Message message,/**Тег,который запустит валидацию*/
                      BindingResult bindingResult,
                      Model model,/**BindingResult всега должен идти перед Model,т.к. если ту будет просто Map,то весь биндинг будет
                           просто сыпаться во View*/
                      @RequestParam("file") MultipartFile file){

        message.setAuthor(user);
        if(bindingResult.hasErrors()){
            Map<String, String> errors = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errors);
        }else{
            if(file!=null){
                File uploadDir = new File(uploadPath);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                System.out.println(uploadDir.getAbsolutePath());
                /**Обеспечиваем уникальный идентификатор*/
                String uuid = UUID.randomUUID().toString();
                String resultFileName = uuid + "." + file.getOriginalFilename();
                System.out.println("ResultFileName " + resultFileName);
                try {

//                File filet = new File(resultFileName);
//                System.out.println(filet.getAbsolutePath());
//                file.transferTo(new File(resultFileName));
                    file.transferTo(new File( "../../../../../../../../../../"+ uploadPath + "/" + resultFileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                message.setFilename(resultFileName);
            }
            model.addAttribute("message",message);
            messageRepository.save(message);
        }

        Iterable<Message> all = messageRepository.findAll();
        model.addAttribute("sometext","It is the main page");
        model.addAttribute("messages",all);
        return "main";
    }
    @GetMapping("/user-messages/{user}")/**AuthenticationPrincipal берет пользователя из сессии*/
    public String userMessages(@AuthenticationPrincipal User currentUser,
                                /**Чтобы из запроса сразу брал нужную переменную*/
                                @PathVariable User user,
                                Model model){
        Set<Message> messages = user.getMessages();
        model.addAttribute("messages",messages);
        model.addAttribute("isCurrentUser",currentUser.equals(user));
        return "userMessages";
    }


}
