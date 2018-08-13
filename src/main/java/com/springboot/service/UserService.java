package com.springboot.service;


import com.springboot.entity.Role;
import com.springboot.entity.User;
import com.springboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MailSender mailSender;
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
            userRepository.save(user);
            if(!StringUtils.isEmpty(user.getEmail())){
                String message = String.format("Hello,%s!\n" +
                        "Welcome,Sweater. Please,visit next Link : http://localhost:8080/activate/%s",
                        user.getName(),user.getActivationCode());
                mailSender.send(user.getEmail(),"Activation code",message);
            }
            return true;
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
       userRepository.save(user);
        return true;
    }
}
