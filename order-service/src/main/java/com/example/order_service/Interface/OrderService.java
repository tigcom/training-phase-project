package com.example.order_service.Interface;

import com.example.order_service.entity.Order;
import org.example.common_lirbary.dto.UserDTO;
import org.example.common_lirbary.dto.productDTO;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrders();
    Order getOrderById(Long id);
    Order saveOrder(Order order);
    void removeOrder(Long id);
    UserDTO getUserById(Object id);
}
