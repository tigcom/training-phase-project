package com.example.order_service.dao;

import com.example.order_service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemDAO extends JpaRepository<OrderItem, Long> {
}
