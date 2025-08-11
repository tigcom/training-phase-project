package com.example.product_service.Interface.Impl;

import com.example.product_service.Interface.ProductService;
import com.example.product_service.dao.ProductDAO;
import com.example.product_service.entity.Product;
import com.example.product_service.mapper.ProductMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDAO productDAO;

    @Override
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productDAO.findById(id).orElse(null);
    }

    @Override
    public void saveProduct(Product product) {
        productDAO.save(product);
    }

    @Override
    public void removeProduct(Long id) {
        productDAO.deleteById(id);
    }

    @Override
    @KafkaListener(topics = "my-topic", groupId = "my-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(Message<byte[]> message) {
        try {
            byte[] payload = message.getPayload();
            ObjectMapper objectMapper = new ObjectMapper();
            productDTO dto = objectMapper.readValue(payload, productDTO.class);
            System.out.println("Received productDTO: " + objectMapper.writeValueAsString(dto));
            productDAO.save(ProductMapper.toEntity(dto));
        } catch (Exception e) {
            System.out.println("Error processing the message: " + e.getMessage());
        }
    }
}