package com.devjola.fashionblog.controller;


import com.devjola.fashionblog.dto.UserLoginDto;
import com.devjola.fashionblog.dto.UserSignUpDto;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    public final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody UserSignUpDto userSignUpDto){
        return ResponseEntity.ok(userService.signUp(userSignUpDto));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto userLoginDto){
        return ResponseEntity.ok(userService.login(userLoginDto));
    }

    @GetMapping("/log-out")
    public ResponseEntity<String> logout(){
        return ResponseEntity.ok(userService.logout());
    }
}
