package com.bonc.medicine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@SpringBootApplication(scanBasePackages = "com.bonc.medicine")
@MapperScan("com.bonc.medicine.mapper")
public class MedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedicineApplication.class, args);
    }

   /* @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowCredentials(true)
                .allowedHeaders("*")
                .allowedOrigins("*")
                .allowedMethods("*");
    }*/
}
