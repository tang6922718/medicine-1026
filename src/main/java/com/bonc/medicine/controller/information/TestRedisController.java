package com.bonc.medicine.controller.information;

import com.bonc.medicine.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
public class TestRedisController {
	
	@Autowired
	private RedisService redisService;
	
	
	/*@RequestMapping("/test")
	public String findAllUser() {
		Tools sk=new Tools();
		return sk.test();
	}*/
	   
	
    /**
     * 向redis存储值
     * @param key
     * @param value
     * @return
     * @throws Exception
     */
    @RequestMapping("/set")
    public String set(String key, String value) throws Exception{
    	
        redisService.set(key, value);
        return "success";  
    }  
    
    /**
     * 获取redis中的值
     * @param key
     * @return
     */
    @RequestMapping("/getKey")
    public String get(String key){  
        try {
			return redisService.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  
    }  
    
    /**
     * 获取redis中的值
     * @param key
     * @return
     */
   /* @RequestMapping("/getKey1")  
    public String get1(String key){  
        try {
			return jedisService.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";  
    }*/ 
}
