package com.example.user_service.Interface.Impl;

import com.example.user_service.Interface.UserService;
import com.example.user_service.Interface.UserWorkflow;
import com.example.user_service.dao.UserDAO;
import com.example.user_service.entity.User;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import lombok.RequiredArgsConstructor;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private final WorkflowClient workflowClient;
    @Override
    public List<User> getAllUsers() {
//        try {
//            Thread.sleep(10000);
            return userDAO.findAll();
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        return List.of();
    }
    @Override
    public User getUserByUserName(String name) {
        return userDAO.findById(name).orElse(null);
    }
    @Override
    public User saveUser(User user) {
        return userDAO.save(user);
    }

    @Override
    public void removeUser(String id) {
        userDAO.deleteById(id);
    }

    @Override
    public OrderDTO shopping(productDTO productDTO) {
        System.out.println("shopping-----------------------------------");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = (Jwt) principal;
        String username = jwt.getClaimAsString("sub");
        System.out.println(username+"-----------------------------------");

        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue("UserTaskQueue")
                .build();

        UserWorkflow workflow = workflowClient.newWorkflowStub(UserWorkflow.class, options);
        return workflow.processOrder(productDTO,username);
    }


}
