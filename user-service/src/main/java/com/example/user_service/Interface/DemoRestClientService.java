package com.example.user_service.Interface;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.OrderItemDTO;
import org.example.common_lirbary.dto.productDTO;

import java.util.List;

public interface DemoRestClientService {
    List<productDTO> products();
    productDTO createProduct(productDTO product);
    OrderDTO createOrder(OrderDTO order);
    OrderItemDTO createOrderItem(OrderItemDTO orderItem);
    OrderDTO findOrder(Long id);
}
