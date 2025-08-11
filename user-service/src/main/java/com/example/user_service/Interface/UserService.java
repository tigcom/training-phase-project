package com.example.user_service.Interface;

import com.example.user_service.entity.User;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.productDTO;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserByUserName(String username);
    User saveUser(User user);
    void removeUser(String name);
    OrderDTO shopping (productDTO productDTO);
}
