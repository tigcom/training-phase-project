package com.example.order_service.Interface.Impl;

import com.example.order_service.Interface.OrderItemService;
import com.example.order_service.Interface.OrderService;
import com.example.order_service.dao.OrderDAO;
import com.example.order_service.dao.OrderItemDAO;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.common_lirbary.CommonService;
import org.example.common_lirbary.dto.OrderItemDTO;
import org.example.common_lirbary.dto.UserDTO;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderItemServiceImpl implements OrderItemService {
    @DubboReference(interfaceClass = CommonService.class, group = "user")
    private CommonService<UserDTO> cmm;
    @Autowired
    private OrderItemDAO orderItemDAO;
    @Autowired
    private OrderService orderService;
    @DubboReference(interfaceClass=CommonService.class, group="product")
    private CommonService<productDTO> productService;


    @Override
    public List<OrderItem> getAllOrders() {
        List<OrderItem> ls = orderItemDAO.findAll();
//        for(OrderItem oi : ls){
//            oi.setProduct( this.getProductById(oi.getProduct_id())) ;
//        }
        return ls;
    }

    @Override
    public OrderItem getOrderById(Long id) {
        OrderItem orderItem = orderItemDAO.findById(id).orElse(null);
//        orderItem.setProduct(this.getProductById(orderItem.getProduct_id()));
        return orderItem;
    }

    @Override
    public void saveOrderItem(OrderItem order) {
        orderItemDAO.save(order);
    }

    @Override
    public void removeOrderItem(Long id) {
        orderItemDAO.deleteById(id);
    }

    @Override
    public productDTO getProductById(Object productId) {
        return productService.getObject(productId);
    }

    @Override
    public void saveOrderItems(OrderItemDTO orderItem) {
        OrderItem ot = new OrderItem();
        ot.setQuantity(orderItem.getQuantity());
        ot.setPrice(orderItem.getPrice());
        ot.setProduct_id(orderItem.getProductId());
        ot.setOrder(orderService.getOrderById(orderItem.getOrderId()));
        orderItemDAO.save(ot);
    }
}
