package org.example.common_lirbary.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO implements Serializable {
    private String username;
    private String fullName;
    private String email;
    private Boolean enabled;
    private String address;
    private Boolean gender;
    private String image;
    private LocalDateTime createdAt;
}
