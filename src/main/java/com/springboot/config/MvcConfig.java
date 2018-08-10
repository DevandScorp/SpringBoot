package com.springboot.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        /**
         * По сути это-маппинг твоих контроллеров.Тут для /login в соответствие ставится шаблон login
         * Т.е. по сути как return ViewName
         */
        registry.addViewController("/login").setViewName("login");
    }

}
