package com.example.product_service;

import com.example.product_service.Interface.Impl.ProductServiceImpl;
import com.example.product_service.dao.ProductDAO;
import com.example.product_service.entity.Product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductServiceTest {
    @Mock
    private ProductDAO productDAO;
    @InjectMocks
    private ProductServiceImpl productService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void getAllProducts_ReturnsList() {
        when(productDAO.findAll()).thenReturn(Arrays.asList(new Product(),  new Product()));
        List<Product> result = productService.getAllProducts();
        assertEquals(2, result.size());
        verify(productDAO).findAll();
    }
    @Test
    void getProductById_Found() {
        when(productDAO.findById(1L)).thenReturn(Optional.of(new Product(1l,"Điện thoại","Đẹp",new BigDecimal("12345.67"),3)));
        Product result = productService.getProductById(1L);
        assertNotNull(result);
        assertEquals("Điện thoại", result.getName());
        verify(productDAO).findById(1L);
    }
    @Test
    void getProductById_NotFound() {
        when(productDAO.findById(1L)).thenReturn(Optional.empty());
        Product result = productService.getProductById(1L);
        assertNull(result);
    }
    @Test
    void saveProduct_CallsSave() {
        Product product = new Product(1l, "Điện thoại", "Đẹp", new BigDecimal("12345.67"), 3);
        productService.saveProduct(product);
        verify(productDAO).save(product);
    }
    @Test
    void removeProduct_CallsDeleteById() {
        productService.removeProduct(1l);
        verify(productDAO).deleteById(1l);
    }
}
