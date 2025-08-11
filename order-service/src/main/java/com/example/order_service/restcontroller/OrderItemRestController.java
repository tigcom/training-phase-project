package com.example.order_service.restcontroller;

import com.example.order_service.Interface.OrderItemService;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common_lirbary.dto.OrderItemDTO;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orderItems")
public class OrderItemRestController {
    private final OrderItemService orderItemService;
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrders() {
        List<OrderItem> orders = orderItemService.getAllOrders();
        MDC.clear();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderById(@PathVariable Long id) {
        OrderItem order = orderItemService.getOrderById(id);
        if (order == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<OrderItem> createOrder(@Valid @RequestBody OrderItem order) {
        orderItemService.saveOrderItem(order);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @PostMapping("/dto")
    public ResponseEntity<Void> createOrderdto(@Valid @RequestBody OrderItemDTO order) {
        orderItemService.saveOrderItems(order);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderItem order) {
            OrderItem existingOrder = orderItemService.getOrderById(id);
            if (existingOrder == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            order.setId(id);
            orderItemService.saveOrderItem(order);
            return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        OrderItem existingOrder = orderItemService.getOrderById(id);
        if (existingOrder == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        orderItemService.removeOrderItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
