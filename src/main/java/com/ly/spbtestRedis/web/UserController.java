package com.ly.spbtestRedis.web;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ly.spbtestRedis.config.ResultMsg;
import com.ly.spbtestRedis.domain.User;
import com.ly.spbtestRedis.domain.UserRepository;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;  
	
	
	@Autowired
    private RedisTemplate redisTemplate;

    
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
    //@Cacheable(value="user-key")  //1
    @Cacheable(value="key-user") //2
    public User getUser() {
    	
    	User user = userRepository.findByUserName("user1");
    	System.out.println("若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");  
        return user;
    }
    
    
    /**
     * http://127.0.0.1:8080/getUsers?param=1
     * 
     * http://127.0.0.1:8080/getUsers?param=2
     * 
     * */
    @RequestMapping("/getUsers")
    //@Cacheable(value="key-Users")//1
    //--@Cacheable(value="key-user") 
    //@Cacheable(value="key-user1")//2   OK
    public Object getUsers(HttpServletRequest request) {
    	
    	System.out.println("--------------1--------------");
    	//ResultMsg resultMsg = new ResultMsg(0, "OK", "erro");     	
    	String prama = request.getParameter("param");
    	if(prama.equals("1")  ){
    		List<User> users = userRepository.findByUserNameIsNotNull();
    		System.out.println("1 若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功"); 
    		//resultMsg = new ResultMsg(0, "OK", users);  
    		 return users ;
    	}else{
        	User user = userRepository.findByUserName("uaer1");
        	System.out.println("2 若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");  
        	if(user==null){
        		//resultMsg = new ResultMsg(0, "OK", "");  
        	    return "" ;
        	}else{
        		//resultMsg = new ResultMsg(0, "OK", user);  
        		return user ;
        	}
        	
    	}
    	
    }
    	
    /**
     * http://127.0.0.1:8080/getUsers2?param=1
     * 
     * http://127.0.0.1:8080/getUsers2?param=2&username=uaer1
     * http://127.0.0.1:8080/getUsers2?param=2&username=uaer2
     * 
     * */
   @RequestMapping("/getUsers2")
   public Object getUsers2(HttpServletRequest request) {
        	
        System.out.println("--------------2--------------");
        ResultMsg resultMsg = new ResultMsg(0, "OK", "erro");     
        
        String prama = request.getParameter("param");
        String username = request.getParameter("username");
        
        if(prama.equals("1")  ){
        	List<User> users = userRepository.findByUserNameIsNotNull();
        	//System.out.println("1 若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功"); 
        	resultMsg = new ResultMsg(0, "OK", users); 
        }else{
        	
           	//User user = userRepository.findByUserName("uaer1");
           	User user = userRepository.findByUserName(username);           	
           	//System.out.println("2 若下面没出现“无缓存的时候调用”字样且能打印出数据表示测试成功");             	
           	if(user==null){
           		resultMsg = new ResultMsg(0, "OK", "");            	    
           	}else{
           		resultMsg = new ResultMsg(0, "OK", user);            		
           	}
        }
    	return resultMsg ;
        
    }

}
