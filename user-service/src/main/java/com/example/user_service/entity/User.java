package com.example.user_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Audited
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@EntityListeners(AuditingEntityListener.class)
public class User {
    @Id
    @NotBlank(message = "{user.username.notNull}")
    private String username;
    @Column(name = "password")
    private String password ;
    @NotBlank(message = "{user.fullName.notNull}")
    @Size(max = 100, message = "{user.fullName.size}")
    @Column(name = "full_name")
    private String fullName;
    @NotNull(message = "{user.email.notNull}")
    @Email(message = "{user.email.invalid}")
    @Size(max = 100, message = "{user.email.size}")
    private String email;
    private Boolean enabled = true;
    @NotNull(message = "{user.address.notNull}")
    @Size(max = 100, message = "{user.address.size}")
    private String address;
    @NotNull(message = "{user.gender.notNull}")
    private Boolean gender;
    private String image;
    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;
}
