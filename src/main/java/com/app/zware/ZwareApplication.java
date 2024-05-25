package com.app.zware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZwareApplication {

  public static void main(String[] args) {
    System.out.println("Hello world");
    SpringApplication.run(ZwareApplication.class, args);
  }

//  @Bean
//  public FilterRegistrationBean<JwtFilter> jwtFilter() {
//    FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
//    registrationBean.setFilter(new JwtFilter());
//    registrationBean.addUrlPatterns("/api/*");
//
//    return registrationBean;
//  }

}
