package com.devjola.fashionblog.model;

import lombok.*;
import java.io.Serializable;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class LikedItemsPK implements Serializable {
        private Post post;
        private User user;
    }

