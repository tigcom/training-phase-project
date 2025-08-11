package com.example.order_service.component;

import com.example.order_service.Interface.OrderItemService;
import com.example.order_service.entity.OrderItem;
import jakarta.persistence.PostLoad;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboReference;
import org.example.common_lirbary.CommonService;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class ProductLoader {
    @DubboReference(interfaceClass= CommonService.class, group="product")
    private CommonService<productDTO> productService;


    @PostLoad
    public void loadProduct(OrderItem item) {
        System.out.println("Loading product"+item.getProduct_id());
        if (item.getProduct_id() != null) {
            item.setProduct(productService.getObject(item.getProduct_id()));
        }
    }
}