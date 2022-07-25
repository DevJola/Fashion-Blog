package com.devjola.fashionblog.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Getter
@Setter
@Component
public class ErrorResponse {

        private String message;
        private HttpStatus status;
        private LocalDateTime time= LocalDateTime.now();
        private String debugMessage;
}
