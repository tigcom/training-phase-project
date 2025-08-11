package com.example.product_service.restcontroller;

import com.example.product_service.Interface.ProductService;
import com.example.product_service.entity.Product;
import com.example.product_service.response.ApiResponseWrapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    @Autowired
    private ProductService productService;
    @Operation(summary = "Lấy tất cả sản phẩm", description = "Lấy danh sách tất cả các sản phẩm")
    @GetMapping
    public ResponseEntity<ApiResponseWrapper<List<Product>>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        System.out.println(products.size());
        products.forEach(System.out::println);
        ApiResponseWrapper<List<Product>> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                  "Find the product list successfully",
                products
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Lấy thông tin sản phẩm", description = "Lấy thông tin sản phẩm theo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Thành công",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class),
                            examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "name": "Điện thoại di động",
                      "price": 15000.0,
                      "description": "Đẹp",
                      "stock": 2
                    }
                """)
                    )),
            @ApiResponse(responseCode = "404", description = "Không tìm thấy sản phẩm")
    })
    @GetMapping("/{id}")
    @Cacheable(value = "product", key = "#id")
    public ResponseEntity<ApiResponseWrapper<Product>> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (product == null) {
            ApiResponseWrapper<Product> response = new ApiResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        ApiResponseWrapper<Product> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Product retrieved successfully",
                product
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Tạo mới sản phẩm", description = "Thêm một sản phẩm mới vào hệ thống")
    @PostMapping
    public ResponseEntity<ApiResponseWrapper<Product>> createProduct(@Valid @RequestBody Product product) {
        productService.saveProduct(product);
        ApiResponseWrapper<Product> response = new ApiResponseWrapper<>(
                HttpStatus.CREATED.value(),
                "Product created successfully",
                product
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @Operation(summary = "Cập nhật sản phẩm", description = "Cập nhật thông tin sản phẩm theo ID")
    @PutMapping("/{id}")
    @CachePut(value = "product", key = "#id")
    public ResponseEntity<ApiResponseWrapper<Product>> updateProduct(@PathVariable Long id, @Valid @RequestBody Product product) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            ApiResponseWrapper<Product> response = new ApiResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        productService.saveProduct(product);
        ApiResponseWrapper<Product> response = new ApiResponseWrapper<>(
                HttpStatus.OK.value(),
                "Product updated successfully",
                product
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Xóa sản phẩm", description = "Xóa một sản phẩm theo ID")
    @DeleteMapping("/{id}")
    @CacheEvict(value = "product", key = "#id")
    public ResponseEntity<ApiResponseWrapper<Void>> deleteProduct(@PathVariable Long id) {
        Product existingProduct = productService.getProductById(id);
        if (existingProduct == null) {
            ApiResponseWrapper<Void> response = new ApiResponseWrapper<>(
                    HttpStatus.NOT_FOUND.value(),
                    "Product not found",
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        productService.removeProduct(id);
        ApiResponseWrapper<Void> response = new ApiResponseWrapper<>(
                HttpStatus.NO_CONTENT.value(),
                "Product deleted successfully",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
}
