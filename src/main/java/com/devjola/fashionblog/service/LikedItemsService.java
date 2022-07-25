package com.devjola.fashionblog.service;

public interface LikedItemsService {
    void likePost(Long postId);

    int noOfLikesPerPost(Long postId);

}
