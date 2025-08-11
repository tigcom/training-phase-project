package com.example.user_service.Interface.Impl;

import com.example.user_service.Interface.FileService;
import com.example.user_service.Interface.UserService;
import com.example.user_service.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {
    @Autowired
    UserService userService;
    @Override
    public void updateFilePath(String username, String key) {
        User us = userService.getUserByUserName(username);
        us.setImage(key);
        userService.saveUser(us);
    }
}
