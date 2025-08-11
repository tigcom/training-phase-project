package com.example.user_service.Interface.Impl;
import com.example.user_service.dao.UserDAO;

import com.example.user_service.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.common_lirbary.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.common_lirbary.CommonService;

@RequiredArgsConstructor
@DubboService(interfaceClass=CommonService.class, group="user")
public  class ServiceDubbo implements CommonService<UserDTO> {
    private final UserDAO userDAO;

    @Transactional
    @Override
    public UserDTO getObject(Object id) {
        User u = userDAO.getReferenceById((String) id);
        UserDTO uDTO = new UserDTO();
        uDTO.setUsername(u.getUsername());
        uDTO.setEmail(u.getEmail());
        uDTO.setFullName(u.getFullName());
        return uDTO;
    }
}
