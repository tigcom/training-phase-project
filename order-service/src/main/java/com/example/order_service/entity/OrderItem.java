package com.example.order_service.entity;

import com.example.order_service.component.ProductLoader;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.example.common_lirbary.dto.productDTO;

import java.math.BigDecimal;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(ProductLoader.class)
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private Long product_id;
    @Transient
    private productDTO product;

    private Integer quantity;

    private BigDecimal price;


}
