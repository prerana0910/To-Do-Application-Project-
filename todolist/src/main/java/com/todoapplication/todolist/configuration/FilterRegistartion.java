package com.todoapplication.todolist.configuration;

import com.todoapplication.todolist.filter.JWTFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistartion {

    @Bean
    public FilterRegistrationBean jwtFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.addUrlPatterns("/api/v3/*");
        filterRegistrationBean.setFilter(new JWTFilter());
        return filterRegistrationBean;
    }
}
