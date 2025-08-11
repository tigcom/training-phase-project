package com.example.product_service;

import com.example.product_service.Interface.ProductService;
import com.example.product_service.dao.ProductDAO;
import com.example.product_service.entity.Product;
import com.example.product_service.restcontroller.ProductRestController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductRestController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ProductDAO productDAO;
    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Product 1", "Mô tả 1", new BigDecimal("100.00"), 5),
                new Product(2L, "Product 2", "Mô tả 2", new BigDecimal("200.00"), 3)
        );
        when(productService.getAllProducts()).thenReturn(mockProducts);
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("Product 1"))
                .andExpect(jsonPath("$.data[1].name").value("Product 2"));
    }

    @Test
    public void testGetProductById() throws Exception {
        Product mockProduct = new Product(1L, "Điện thoại", "Đẹp", new BigDecimal("12345.67"), 3);
        when(productService.getProductById(1L)).thenReturn(mockProduct);

        mockMvc.perform(get("/api/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Điện thoại"))
                .andExpect(jsonPath("$.data.price").value(12345.67));
    }

    @Test
    public void testGetProductById_NotFound() throws Exception {
        when(productService.getProductById(999L)).thenReturn(null);

        mockMvc.perform(get("/api/products/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Product not found"));
    }
}
