package com.example.order_service.Interface;

import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import org.example.common_lirbary.dto.OrderItemDTO;
import org.example.common_lirbary.dto.UserDTO;
import org.example.common_lirbary.dto.productDTO;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> getAllOrders();
    OrderItem getOrderById(Long id);
    void saveOrderItem(OrderItem order);
    void removeOrderItem(Long id);
    productDTO getProductById(Object id);
    void saveOrderItems(OrderItemDTO orderItem);
}
