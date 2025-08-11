package com.example.product_service.Interface.Impl;
import com.example.product_service.dao.ProductDAO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.example.common_lirbary.CommonService;
import com.example.product_service.entity.Product;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.beans.factory.annotation.Autowired;
@RequiredArgsConstructor
@DubboService(interfaceClass=CommonService.class, group="product")
public class productDubboService implements  CommonService<productDTO>{

    private final ProductDAO productDAO;
    @Transactional
    @Override
    public productDTO getObject(Object id) {
        Product product = productDAO.getReferenceById((Long) id);
        productDTO dto = new productDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        return dto;
    }
}
