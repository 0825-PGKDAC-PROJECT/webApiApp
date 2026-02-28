package com.gaadix.common.dto;

import com.gaadix.common.enums.UserRole;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate dob;
    private boolean isActive;
    private LocalDateTime lastLoginAt;
    private Set<UserRole> roles;
    private LocalDateTime createdAt;
}
