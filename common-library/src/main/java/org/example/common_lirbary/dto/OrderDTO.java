package org.example.common_lirbary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.common_lirbary.dto.UserDTO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String user_id;
    private UserDTO user;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
}