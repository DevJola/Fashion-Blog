package com.devjola.fashionblog.service.serviceImpl;

import com.devjola.fashionblog.pagination_criteria.PostListPages;
import com.devjola.fashionblog.util.Util;
import com.devjola.fashionblog.dto.ModifyPostDto;
import com.devjola.fashionblog.dto.UploadPostDto;
import com.devjola.fashionblog.dto.ViewPostsDto;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.*;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.CategoryRepository;
import com.devjola.fashionblog.repository.PostRepository;
import com.devjola.fashionblog.service.PostService;
import com.devjola.fashionblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    public final PostRepository postRepository;
    public final HttpSession httpSession;
    public final UserService userService;
    public final CategoryRepository categoryRepository;
    private final Util util;

    public void uploadPost(UploadPostDto uploadPostDto, Long categoryId){
        Long userId = util.getLoggedUserById();
        User user = util.findUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)){
            throw new NotPermittedToPerformThisOperation("Only admins can upload post");
        }
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new RuntimeException("category not found"));
        Post post = Post.builder()
                .title(uploadPostDto.getTitle())
                .imageUrl(uploadPostDto.getImageUrl())
                .content(uploadPostDto.getContent())
                .category(category)
                .likedItems(new ArrayList<>())
                .comments(new ArrayList<>())
                .user(user)
                .build();
        postRepository.save(post);

        category.getPosts().add(post);
        categoryRepository.save(category);
    }

    @Override
    public Page<Post> viewAllPosts(PostListPages postListPages) {
       List<Post> post = postRepository.findAll();
                if (post.isEmpty()){
            throw new ListIsEmpty("There are no posts yet");
        }
        Sort sort = Sort.by(postListPages.getSortDirection(), postListPages.getSortBy());
        Pageable pageable = PageRequest.of(postListPages.getPageNumber(), postListPages.getPageSize(), sort);
        return postRepository.findAll(pageable);
    }

    @Override
    public Post viewPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound("This post was not found"));
    }

    @Override
    public Post modifyPost(Long id, ModifyPostDto modifyPostDto) {
        Long userId = util.getLoggedUserById();
        User user = util.findUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)){
            throw new NotPermittedToPerformThisOperation("Only admins can upload post");
        }

            Post post1 = postRepository.findById(id).orElseThrow(() -> new PostNotFound("Post was not found"));

            if(Objects.nonNull(modifyPostDto.getTitle()) && !"".equalsIgnoreCase(modifyPostDto.getTitle())){
                post1.setTitle(modifyPostDto.getTitle());
            }
            if(Objects.nonNull(modifyPostDto.getImageUrl()) && !"".equalsIgnoreCase(modifyPostDto.getImageUrl())){
                post1.setImageUrl(modifyPostDto.getImageUrl());
            }
            if(Objects.nonNull(modifyPostDto.getContent()) && !"".equalsIgnoreCase(modifyPostDto.getContent())){
                post1.setContent(modifyPostDto.getContent());
            }

            return postRepository.save(post1);
    }

    @Override
    public void deletePost(Long postId) {
        Long userId = util.getLoggedUserById();
        User user = util.findUserById(userId);

        if (!user.getRole().equals(Role.ADMIN)){
            throw new NotPermittedToPerformThisOperation("Only admins can delete post");
        }
        Post post = postRepository.findById(postId).orElseThrow(()-> new PostNotFound("Post was not found"));
        postRepository.delete(post);
    }

    @Override
    public List<ViewPostsDto> ViewPostByCategory(Long categoryId) {
        Category category = categoryRepository.findCategoryById(categoryId)
                .orElseThrow(() -> new RuntimeException("category not found"));

        List<Post> posts = category.getPosts();

        List<ViewPostsDto> postResponse = new ArrayList<>();

        for (Post p : posts) {
            ViewPostsDto res = ViewPostsDto.builder()
                    .title(p.getTitle())
                    .comments(p.getComments().size())
                    .likes(p.getLikedItems().size())
                    .content(p.getContent())
                    .imageUrl(p.getImageUrl())
                    .build();

            postResponse.add(res);
        }
        return postResponse;
    }
}
