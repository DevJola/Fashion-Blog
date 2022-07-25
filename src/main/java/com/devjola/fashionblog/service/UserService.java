package com.devjola.fashionblog.service;

import com.devjola.fashionblog.dto.UserLoginDto;
import com.devjola.fashionblog.dto.UserSignUpDto;
import com.devjola.fashionblog.model.User;

public interface UserService {

    User signUp(UserSignUpDto userSignUpDto);

    String login(UserLoginDto userLoginDto);

    String logout();


}
