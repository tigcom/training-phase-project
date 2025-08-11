package org.example.common_lirbary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.common_lirbary.dto.productDTO;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private Long orderId;
    private productDTO product;
    private Integer quantity;
    private BigDecimal price;

}