package com.devjola.fashionblog.service.serviceImpl;

import com.devjola.fashionblog.dto.UserLoginDto;
import com.devjola.fashionblog.dto.UserSignUpDto;
import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.exception.UserAlreadyExists;
import com.devjola.fashionblog.exception.UserNotFound;
import com.devjola.fashionblog.model.User;
import com.devjola.fashionblog.repository.UserRepository;
import com.devjola.fashionblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;


@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;
    @Override
    public User signUp(UserSignUpDto userSignUpDto) {
        String userEmail = userSignUpDto.getEmail();
        boolean users = userRepository.existsByEmail(userEmail);

        if(users){
            throw new UserAlreadyExists("User with " + userEmail+ " already exist");
        }
        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .firstName(userSignUpDto.getFirstName())
                .lastName(userSignUpDto.getLastName())
                .role(Role.VISITOR)
                .build();

        userRepository.save(user);

        return user;
    }

    @Override
    public String login(UserLoginDto userLoginDto) {
        String userEmail = userLoginDto.getEmail();
        String userPassword = userLoginDto.getPassword();

        User dbuser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFound("User not found") );

        if(!dbuser.getPassword().equals(userPassword)){
            throw new UserNotFound("User not found");
        }

        httpSession.setAttribute("User_id", dbuser.getId());
        httpSession.setAttribute("Permission", "User");


        return "Successfully Signed In";
    }


    @Override
    public String logout() {
        httpSession.invalidate();
        return "User Logged Out" ;
    }

}