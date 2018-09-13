package com.bonc.medicine.config;

import com.github.pagehelper.PageInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;


/**  
* @desc: PageHelperConfig  
* @Description 
* @author: zhengzhoujin  
* @createTime: 2018年6月12日 上午10:35:46   
* @version: v1.0    
*/  
@Configuration
public class PageHelperConfig {


    @Value("${pagehelper.helperDialect}")
    private String helperDialect;


    @Bean
    public PageInterceptor pageInterceptor(){
        PageInterceptor pageInterceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("helperDialect", helperDialect);
        pageInterceptor.setProperties(properties);
        return pageInterceptor;
    }
}
