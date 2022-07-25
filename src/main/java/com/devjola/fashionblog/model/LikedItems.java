package com.devjola.fashionblog.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "liked_items")
@IdClass(LikedItemsPK.class)
public class LikedItems implements Serializable {

    @Id
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @Id
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    @CreationTimestamp
    private LocalDateTime timestamp;
}
