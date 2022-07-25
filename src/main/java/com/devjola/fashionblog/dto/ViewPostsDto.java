package com.devjola.fashionblog.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewPostsDto {
    private String title;
    private String content;
    private String imageUrl;
    private int likes;
    private int comments;
}
