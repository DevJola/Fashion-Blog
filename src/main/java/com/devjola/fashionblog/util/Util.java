package com.devjola.fashionblog.util;

import com.devjola.fashionblog.exception.UserNotFound;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class Util {

    private final HttpSession httpSession;

    private final UserRepository userRepository;


    public Long getLoggedUserById() {
        Long userId =  (Long) httpSession.getAttribute("User_id");
        if(userId == null) throw new UserNotFound("Please log in to perform this operation!");
        return userId;
    }


    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new UserNotFound("User not found"));
    }
}
