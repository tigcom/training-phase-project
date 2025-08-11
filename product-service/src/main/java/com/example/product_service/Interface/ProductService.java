package com.example.product_service.Interface;

import com.example.product_service.entity.Product;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.messaging.Message;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long id);
    void saveProduct(Product product);
    void removeProduct(Long id);
    void consume(Message<byte[]> message);
}