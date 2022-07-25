package com.devjola.fashionblog.service.serviceImpl;

import com.devjola.fashionblog.util.Util;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.NotPermittedToPerformThisOperation;
import com.devjola.fashionblog.exception.PostNotFound;
import com.devjola.fashionblog.model.LikedItems;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.PostRepository;
import com.devjola.fashionblog.service.LikedItemsService;
import com.devjola.fashionblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Service
public class LikedItemsServiceImpl implements LikedItemsService {

    private final HttpSession httpSession;
    private final UserService userService;
    private final PostRepository postRepository;
    private final Util util;



    @Override
    public void likePost(Long postId) {
        Long userId = util.getLoggedUserById();
        User user = util.findUserById(userId);

        if (!user.getRole().equals(Role.VISITOR)){
            throw new NotPermittedToPerformThisOperation("Login as a visitor to like a post");
        }

        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFound("post was not found"));

        LikedItems likedItems1 = LikedItems.builder()
                .post(post)
                .user(user)
                .build();

//        likedItemsRepository.save(likedItems1);

        post.getLikedItems().add(likedItems1);

        postRepository.save(post);
    }

    @Override
    public int noOfLikesPerPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFound("post not found"));

        return post.getLikedItems().size();
    }
}
