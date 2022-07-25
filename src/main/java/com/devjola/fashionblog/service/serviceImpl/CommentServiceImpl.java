package com.devjola.fashionblog.service.serviceImpl;

import com.devjola.fashionblog.util.Util;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.NotPermittedToPerformThisOperation;
import com.devjola.fashionblog.exception.PostNotFound;
import com.devjola.fashionblog.model.Comment;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.CommentRepository;
import com.devjola.fashionblog.repository.PostRepository;
import com.devjola.fashionblog.service.CommentService;
import com.devjola.fashionblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final UserService userService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final HttpSession httpSession;
    private final Util util;


    @Override
    public void makeComment(Long postId, Comment comment) {
        Long userId = util.getLoggedUserById();
        User user = util.findUserById(userId);

        if (!user.getRole().equals(Role.VISITOR)){
            throw new NotPermittedToPerformThisOperation("Login as a visitor to leave a comment");
        }

        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFound("post was not found"));

        Comment comment1 = Comment.builder()
                .content(comment.getContent())
                .user(user)
                .post(post)
                .build();
        commentRepository.save(comment1);

        post.getComments().add(comment1);
        postRepository.save(post);
    }

    @Override
    public List<Comment> viewPostComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound("Post not found"));

        return post.getComments();
    }

    @Override
    public int printNumberOfComments(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new PostNotFound("post not found"));

        return post.getComments().size();
    }
}
