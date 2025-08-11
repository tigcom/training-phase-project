package com.example.user_service.Interface.Impl;

import com.example.user_service.Interface.DemoRestClientService;

import com.example.user_service.response.ApiResponseWrapper;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.OrderItemDTO;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DemoRestClientServiceImpl implements DemoRestClientService {
    @Value("${product.service.api.url}")
    private String productApiUrl;
    @Value("${order.service.api.url}")
    private String orderApiUrl;
    @Value("${orderItems.service.api.url}")
    private String orderItemsApiUrl;
    private final RestTemplate rest;

    public DemoRestClientServiceImpl(@Qualifier("restTemplateWithUserToken") RestTemplate restTemplateWithUserToken) {
        this.rest = restTemplateWithUserToken;
    }
    @Override
    public List<productDTO> products() {
        ResponseEntity<ApiResponseWrapper<List<productDTO>>> response = rest.exchange(
                productApiUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponseWrapper<List<productDTO>>>() {}
        );
        return response.getBody().getData();
    }

    @Override
    public productDTO createProduct(productDTO product) {
        HttpEntity<productDTO> request = new HttpEntity<>(product);

        ResponseEntity<ApiResponseWrapper<productDTO>> response = rest.exchange(
                productApiUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<ApiResponseWrapper<productDTO>>() {}
        );
        return response.getBody().getData();
    }

    @Override
    public OrderDTO createOrder(OrderDTO order) {
        HttpEntity<OrderDTO> request = new HttpEntity<>(order);

        ResponseEntity<OrderDTO> response = rest.exchange(
                orderApiUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<OrderDTO>() {}
        );
        System.out.println("khang"+response.getBody());
        return response.getBody();
    }

    @Override
    public OrderItemDTO createOrderItem(OrderItemDTO orderItem) {
        HttpEntity<OrderItemDTO> request = new HttpEntity<>(orderItem);

        ResponseEntity<OrderItemDTO> response = rest.exchange(
                orderItemsApiUrl,
                HttpMethod.POST,
                request,
                new ParameterizedTypeReference<OrderItemDTO>() {}
        );
        return response.getBody();
    }

    @Override
    public OrderDTO findOrder(Long id) {
        String url = orderApiUrl + "/" + id;
        ResponseEntity<OrderDTO> response = rest.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<OrderDTO>() {}
        );
        return response.getBody();
    }


}
