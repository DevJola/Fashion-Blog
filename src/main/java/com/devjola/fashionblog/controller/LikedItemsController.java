package com.devjola.fashionblog.controller;


import com.devjola.fashionblog.model.LikedItems;
import com.devjola.fashionblog.service.LikedItemsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("/api/v1/likes")
@RestController
public class LikedItemsController {

    private final LikedItemsService likedItemsService;

    @PostMapping("/{id}")
    public String likePost(@PathVariable("id") Long postId){

        likedItemsService.likePost(postId);
        return "post liked";
    }

//    @PostMapping("/unLikedPosts/{id}")
//    public String unLikePost(@PathVariable("id") Long likedItemId){
//        likedItemsService.unLikePost(likedItemId);
//        return "unliked";
//    }


    @GetMapping("/{id}")
    public int noOfLikesPerPost(@PathVariable("id") Long postId){
        return likedItemsService.noOfLikesPerPost(postId);
    }


}
