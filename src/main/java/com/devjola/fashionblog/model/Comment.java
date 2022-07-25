package com.devjola.fashionblog.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 5000)
    private String content;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime dateModified;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

}
