package com.springboot.service;


import com.springboot.entity.Role;
import com.springboot.entity.User;
import com.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByName(username);
    }
    /**Перенесли из RegistrationController,т.к. так будет правильнее*/
    public boolean addUser(User user){
            User userFromDb = userRepository.findByName(user.getName());
            if(userFromDb!=null){
                return false;
            }
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            user.setActivationCode(UUID.randomUUID().toString());
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            userRepository.save(user);
            if(!StringUtils.isEmpty(user.getEmail())){
                String message = String.format("Hello,%s!\n" +
                        "Welcome,Sweater. Please,visit next Link : http://localhost:8080/activate/%s",
                        user.getName(),user.getActivationCode());
                mailSender.send(user.getEmail(),"Activation code",message);
            }
            return true;
    }
    private void sendMessage(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "Welcome to Sweater. Please, visit next link: http://localhost:8080/activate/%s",
                    user.getUsername(),
                    user.getActivationCode()
            );

            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }
    public boolean activateUser(String code) {
       User user = userRepository.findByActivationCode(code);
       if(user==null){
           return false;
       }
       user.setActivationCode(null);
        /**
         * ТОлько после того,как была получена обратная связь от ссылки,мы сохраняем пользователя.
         */
        user.setPassword2("default");
       userRepository.save(user);
        return true;
    }

    public void saveUser(User user, String name, Map<String,String> form) {
        user.setName(name);
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
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void updateProfile(User user, String password, String email) {
        String user_email = user.getEmail();
        boolean isEmailActivated = (email != null && !email.equals(user_email)) || (user_email != null && !user_email.equals(email));
        if(isEmailActivated){
            user.setEmail(email);
            if(!StringUtils.isEmpty(email)){
                user.setActivationCode(UUID.randomUUID().toString());
            }
        }
        if(!StringUtils.isEmpty(password)){
            user.setPassword(password);
        }
        userRepository.save(user);
        if(isEmailActivated){
            sendMessage(user);
        }
    }
}
