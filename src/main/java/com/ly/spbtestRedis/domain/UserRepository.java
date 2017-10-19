package com.ly.spbtestRedis.domain;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	
    @Cacheable(value="findByUserName")//4
    User findByUserName(String userName);
    
    
    @Cacheable(value="findByUserNameIsNotNull")//4
    List<User> findByUserNameIsNotNull();

    User findByUserNameOrEmail(String username, String email);
    
}