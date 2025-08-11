package com.example.user_service.Interface.Impl;

import com.example.user_service.Interface.DemoRestClientService;
import com.example.user_service.Interface.UserActivities;
import com.example.user_service.Interface.UserService;
import com.example.user_service.entity.User;
import lombok.RequiredArgsConstructor;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.OrderItemDTO;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserActivitiesImpl implements UserActivities {
    private final UserService userService;
    private final DemoRestClientService rest;

    @Override
    public productDTO createProduct(productDTO product) {
        return rest.createProduct(product);
    }

    @Override
    public OrderDTO createOrder(OrderDTO order) {
        return rest.createOrder(order);
    }

    @Override
    public OrderItemDTO addOrderItem(OrderItemDTO orderItem) {
        return rest.createOrderItem(orderItem);
    }

    @Override
    public OrderDTO getOrder(long orderId) {
        return rest.findOrder(orderId);
    }
}
