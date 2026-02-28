package com.gaadix.common.entity;

import com.gaadix.common.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @Email
    @NotBlank
    @Column(unique = true)
    private String email;
    
    @NotBlank
    private String password;
    
    @Column(unique = true)
    private String phone;
    
    private LocalDate dob;
    
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] profileImage;
    
    @Builder.Default
    private boolean isActive = true;
    
    @Builder.Default
    private boolean accountLocked = false;
    
    @Builder.Default
    private int failedLoginAttempts = 0;
    
    private LocalDateTime lastLoginAt;
    
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<UserRole> roles = new HashSet<>();
}
