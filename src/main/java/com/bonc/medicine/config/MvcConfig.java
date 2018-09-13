package com.bonc.medicine.config;

import com.bonc.medicine.interceptor.AuthorizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @program: medicine-hn
 * @description:
 * @author: hejiajun
 * @create: 2018-09-10 19:35
 **/

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.bonc.medicine.controller")
@PropertySource(value = "classpath:application.yml", ignoreResourceNotFound = true, encoding = "UTF-8")
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    //@Autowired
    //private CurrentUserMethodArgumentResolver currentUserMethodArgumentResolver;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");

    }

    /*@Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver);
    }*/
}
