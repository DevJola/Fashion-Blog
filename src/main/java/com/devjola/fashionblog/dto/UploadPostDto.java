package com.devjola.fashionblog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter @ToString @Builder
public class UploadPostDto {

    private String title;
    private String imageUrl;
    private String content;

}
