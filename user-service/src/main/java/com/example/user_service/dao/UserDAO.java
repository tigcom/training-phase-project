package com.example.user_service.dao;
import com.example.user_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;


public interface UserDAO extends  JpaRepository<User, String>{
}
