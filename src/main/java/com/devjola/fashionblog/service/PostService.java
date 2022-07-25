package com.devjola.fashionblog.service;

import com.devjola.fashionblog.dto.ModifyPostDto;
import com.devjola.fashionblog.dto.UploadPostDto;
import com.devjola.fashionblog.dto.ViewPostsDto;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.pagination_criteria.PostListPages;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {
    void uploadPost(UploadPostDto uploadPostDto, Long categoryId);


    Page<Post> viewAllPosts(PostListPages postListPages);

    Post viewPostById(Long postId);

    Post modifyPost(Long id, ModifyPostDto post);

    void deletePost(Long postId);


    List<ViewPostsDto> ViewPostByCategory(Long categoryId);
}
