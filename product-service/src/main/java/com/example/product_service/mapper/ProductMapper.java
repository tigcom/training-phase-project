package com.example.product_service.mapper;

import com.example.product_service.entity.Product;
import org.example.common_lirbary.dto.productDTO;

public class ProductMapper {

    public static productDTO toDTO(Product product) {
        if (product == null) return null;

        return productDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }

    public static Product toEntity(productDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());

        return product;
    }
}
