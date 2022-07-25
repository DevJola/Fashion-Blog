package com.devjola.fashionblog.controller;


import com.devjola.fashionblog.model.Comment;
import com.devjola.fashionblog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/comments")
public class CommentController {

    private final CommentService commentService;


    @PostMapping("/{id}")
    public String makeComment(@PathVariable("id") Long postId, @RequestBody Comment comment){
       commentService.makeComment(postId, comment);
        return "Comment Saved";
    }

    @GetMapping("/{id}")
    public List<Comment> viewPostComment(@PathVariable("id") Long postId){
        return commentService.viewPostComments(postId);
    }

    @GetMapping("/noOfComments/{id}")
    public int printNumberOfComments(@PathVariable("id") Long postId){
        return commentService.printNumberOfComments(postId);
    }


}
