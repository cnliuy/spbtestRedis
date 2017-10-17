package com.ly.spbtestRedis.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.ly.spbtestRedis.domain.User;
import com.ly.spbtestRedis.domain.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;  

    
    //@RequestMapping("/")
    @RequestMapping(value = "/", method = RequestMethod.GET)    
    public String index() {
    	System.out.println("------------------------------");
    	return "hello";
    }
    
    /**
     * http://127.0.0.1:8080/getUser
     * 
     * */
    @RequestMapping("/getUser")
    @Cacheable(value="user-key")
    public User getUser() {
    	
    	User user = userRepository.findByUserName("aaa");
    	System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");  
        return user;
    }
    
    
    /**
     * http://127.0.0.1:8080/getUsers
     * 
     * */
    @RequestMapping("/getUsers")
    @Cacheable(value="key-Users")
    public List<User> getUsers() {
    	
    	List<User> users=userRepository.findAll();
    	System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");  
        return users;
        
    }

}
