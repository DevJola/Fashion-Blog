package com.devjola.fashionblog.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@Builder @ToString
public class ModifyPostDto {
    private String title;
    private String imageUrl;
    private String content;
}
