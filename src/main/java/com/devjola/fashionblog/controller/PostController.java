package com.devjola.fashionblog.controller;

import com.devjola.fashionblog.dto.ModifyPostDto;
import com.devjola.fashionblog.dto.UploadPostDto;
import com.devjola.fashionblog.dto.ViewPostsDto;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.pagination_criteria.PostListPages;
import com.devjola.fashionblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/posts")
public class PostController {

    public final PostService postService;


    @PostMapping("/{id}")
    public String uploadPost(@PathVariable("id") Long categoryId, @RequestBody UploadPostDto uploadPostDto){
        postService.uploadPost(uploadPostDto, categoryId);
        return "post uploaded" ;
    }


    @GetMapping("/")
    public Page<Post> viewAllPosts(PostListPages postListPages){
        return postService.viewAllPosts(postListPages);
    }


    @GetMapping("/{id}")
    public Post viewPostById(@PathVariable("id") Long postId){

        return postService.viewPostById(postId);
    }


    @GetMapping("/postByCategoryId/{categoryId}")
    public List<ViewPostsDto> viewPostByCategoryId(@PathVariable("categoryId") Long categoryId){
        return postService.ViewPostByCategory(categoryId);
    }


    @PutMapping("/{id}")
    public Post modifyPost(@PathVariable("id") Long id, @RequestBody ModifyPostDto post){
        return postService.modifyPost(id, post);
    }


    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable("postId") Long postId){
        postService.deletePost(postId);
        return "post deleted";
    }


}
