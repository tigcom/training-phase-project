package com.example.user_service.Interface.Impl;

import com.example.user_service.Interface.UserActivities;
import com.example.user_service.Interface.UserWorkflow;
import com.example.user_service.entity.User;
import io.temporal.activity.ActivityOptions;
import io.temporal.client.WorkflowClient;
import io.temporal.workflow.Workflow;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.OrderItemDTO;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;

public class UserWorkflowImpl implements UserWorkflow {
    private final UserActivities activities = Workflow.newActivityStub(UserActivities.class,
            ActivityOptions.newBuilder().setStartToCloseTimeout(Duration.ofSeconds(30)).build());

    @Override
    public OrderDTO processOrder(productDTO product,String username) {

        productDTO p =activities.createProduct(product);
        OrderDTO o = new OrderDTO();
        o.setUser_id(username);
        o.setTotalAmount(BigDecimal.valueOf(2).multiply(p.getPrice()));

        OrderDTO newOrder = activities.createOrder(o);
        OrderItemDTO oi = new OrderItemDTO();
        oi.setPrice(p.getPrice());
        oi.setQuantity(2);
        oi.setProductId(p.getId());
        oi.setOrderId(newOrder.getId());

        activities.addOrderItem(oi);

        return activities.getOrder(newOrder.getId());
    }
}
