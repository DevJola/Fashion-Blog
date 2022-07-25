package com.devjola.fashionblog.service;

import com.devjola.fashionblog.model.Comment;

import java.util.List;

public interface CommentService {

    void makeComment(Long postId, Comment comment);

    List<Comment> viewPostComments(Long postId);

    int printNumberOfComments(Long postId);
}
