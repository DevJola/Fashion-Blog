package com.devjola.fashionblog.dto;


import com.devjola.fashionblog.enums.Role;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpDto {
    private String email;

    private String password;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Role role;
}
