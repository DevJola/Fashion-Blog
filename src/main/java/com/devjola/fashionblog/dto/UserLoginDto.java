package com.devjola.fashionblog.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class UserLoginDto {
    private String email;

    private String password;
}
