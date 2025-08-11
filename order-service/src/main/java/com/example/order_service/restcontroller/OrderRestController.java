package com.example.order_service.restcontroller;

import com.example.order_service.Interface.OrderService;
import com.example.order_service.entity.Order;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.api.trace.Span;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderRestController {
    private final Tracer tracer;
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        Span span = tracer.spanBuilder("getAllOrders").startSpan();
        MDC.put("traceId", span.getSpanContext().getTraceId());
        MDC.put("spanId", span.getSpanContext().getSpanId());

        try (var scope =  span.makeCurrent()) {
            MDC.put("findall", "order");
            List<Order> orders = orderService.getAllOrders();
            MDC.clear();
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } finally {
            span.end();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Span span = tracer.spanBuilder("getOrderById").startSpan();
        MDC.put("traceId", span.getSpanContext().getTraceId());
        MDC.put("spanId", span.getSpanContext().getSpanId());
        try (var scope =  span.makeCurrent()) {
            Order order = orderService.getOrderById(id);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(order, HttpStatus.OK);
        } finally {
            span.end();
            MDC.clear();
        }
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody Order order) {
        Span span = tracer.spanBuilder("createOrder").startSpan();
        MDC.put("traceId", span.getSpanContext().getTraceId());
        MDC.put("spanId", span.getSpanContext().getSpanId());

        try (var scope =  span.makeCurrent()) {
            orderService.saveOrder(order);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        } finally {
            span.end();
            MDC.clear();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @Valid @RequestBody Order order) {
        Span span = tracer.spanBuilder("updateOrder").startSpan();
        MDC.put("traceId", span.getSpanContext().getTraceId());
        MDC.put("spanId", span.getSpanContext().getSpanId());

        try (var scope =  span.makeCurrent()) {
            Order existingOrder = orderService.getOrderById(id);
            if (existingOrder == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            order.setId(id);
            orderService.saveOrder(order);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } finally {
            span.end();
            MDC.clear();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        Span span = tracer.spanBuilder("deleteOrder").startSpan();
        MDC.put("traceId", span.getSpanContext().getTraceId());
        MDC.put("spanId", span.getSpanContext().getSpanId());
        try (var scope =  span.makeCurrent()) {
            Order existingOrder = orderService.getOrderById(id);
            if (existingOrder == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            orderService.removeOrder(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } finally {
            span.end();
            MDC.clear();
        }
    }
}
