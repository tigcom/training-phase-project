package com.example.order_service.Interface.Impl;

import com.example.order_service.Interface.OrderService;
import com.example.order_service.dao.OrderDAO;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;
import org.springframework.stereotype.Service;
import com.example.order_service.entity.Order;
import org.example.common_lirbary.CommonService;
import org.example.common_lirbary.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.dubbo.config.annotation.DubboReference;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final Counter orderRequestCounter;
    private final AtomicInteger activeOrderGauge;

    @DubboReference(interfaceClass = CommonService.class, group = "user")
    private CommonService<UserDTO> cmm;

    @Autowired
    private OrderDAO orderDAO;
    public OrderServiceImpl(MeterRegistry registry) {
        orderRequestCounter = Counter.builder("order_requests_total")
                .description("Total number of order requests processed")
                .register(registry);
        activeOrderGauge = registry.gauge("active_orders_count", new AtomicInteger(0));
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderDAO.findAll();
        for (Order order : orders) {
            order.setUser(this.getUserById(order.getUser_id()));
        }
        activeOrderGauge.set(orderDAO.findAll().size());
        return orders;
    }

    @Override
    public Order getOrderById(Long id) {
        orderRequestCounter.increment();
        long startTime = System.currentTimeMillis();
        Order order = orderDAO.findById(id).orElse(null);
        if (order != null) {
            order.setUser(this.getUserById(order.getUser_id()));
        }
        long duration = System.currentTimeMillis() - startTime;
        return order;
    }


    @Override
    public Order saveOrder(Order order) {
        orderRequestCounter.increment();
        long startTime = System.currentTimeMillis();
        Order savedOrder = orderDAO.save(order);
        return savedOrder;
    }

    @Override
    public void removeOrder(Long id) {
        activeOrderGauge.decrementAndGet();
        long startTime = System.currentTimeMillis();
        orderDAO.deleteById(id);
        long duration = System.currentTimeMillis() - startTime;
    }

    @Override
    public UserDTO getUserById(Object id) {
        return cmm.getObject(id);
    }
}
