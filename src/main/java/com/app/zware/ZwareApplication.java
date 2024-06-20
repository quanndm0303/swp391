package com.app.zware;

import com.app.zware.Filters.CORSFilter;
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
  public FilterRegistrationBean<CORSFilter> corsFilter() {
    FilterRegistrationBean<CORSFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new CORSFilter());
    registrationBean.addUrlPatterns("/*"); // Apply to all paths
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<JwtFilter> jwtFilter(UserService userService) {
    FilterRegistrationBean<JwtFilter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new JwtFilter(userService));
    registrationBean.addUrlPatterns("/*");

    return registrationBean;
  }

}

/*
 * Quang: InboundTransaction , (auth, user)
 * Quan: OutboundTransaction, Category, Product, Item (nếu có)
 * Hai: Disposal, warehouse, zone,
 *
 * [new format]
 * Quan: Cate, Item, Outbound, Warehousezone + item, product
 * Hai: GoodDisposal, User, Inbound, Warehouse
 * */
