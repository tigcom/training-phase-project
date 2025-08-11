package com.example.user_service.Interface;

import com.example.user_service.entity.User;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.OrderItemDTO;
import org.example.common_lirbary.dto.productDTO;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface UserActivities {
    @ActivityMethod
    public productDTO createProduct(productDTO product);
    @ActivityMethod
    public OrderDTO createOrder(OrderDTO order);
    @ActivityMethod
    public OrderItemDTO addOrderItem(OrderItemDTO orderItem);
    @ActivityMethod
    public OrderDTO getOrder(long orderId);
}
