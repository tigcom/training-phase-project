package com.example.product_service.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Schema(description = "Thông tin sản phẩm")
@Data
@NoArgsConstructor
@Table(name = "products")
@AllArgsConstructor
public class Product implements Serializable {
    @Schema(description = "ID của sản phẩm", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Tên của sản phẩm", example = "Điện thoại di động")
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 150, message = "Tên sản phẩm không được vượt quá 150 ký tự")
    private String name;
    @Schema(description = "Mô tả của sản phẩm", example = "Sản phẩm chất lượng cao")
    @Size(max = 1000, message = "Mô tả không được vượt quá 1000 ký tự")
    private String description;
    @Schema(description = "Giá của sản phẩm", example = "99999")
    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.01", inclusive = true, message = "Giá phải lớn hơn 0")
    private BigDecimal price;
    @Schema(description = "Số lượng của sản phẩm", example = "3")
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 0, message = "Số lượng không được âm")
    private Integer stock;
}
