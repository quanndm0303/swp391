package com.app.zware;

import com.app.zware.Filters.JwtFilter;
import com.app.zware.Service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ZwareApplication {

  public static void main(String[] args) {
    System.out.println("Hello world");
    SpringApplication.run(ZwareApplication.class, args);
  }

  @Bean
  public FilterRegistrationBean<JwtFilter> jwtFilter(UserService userService) {
    FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new JwtFilter(userService));
    registrationBean.addUrlPatterns("/api/*");

    return registrationBean;
  }

}
