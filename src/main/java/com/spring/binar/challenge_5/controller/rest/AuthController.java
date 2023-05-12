package com.spring.binar.challenge_5.controller.rest;

import com.spring.binar.challenge_5.dto.AuthenticationRequestDTO;
import com.spring.binar.challenge_5.dto.UserRegisterDTO;
import com.spring.binar.challenge_5.service.UserService;
import com.spring.binar.challenge_5.utils.Constants;
import com.spring.binar.challenge_5.utils.ResponseHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<Object> authenticate(
            @RequestBody AuthenticationRequestDTO request
    ){
        return ResponseHandler.generateResponse(Constants.SUCCESS_RETRIEVE_MSG, HttpStatus.OK, userService.authentication(request));
    }

    @PostMapping("/auth/register")
    public ResponseEntity<Object> register(
            @RequestBody UserRegisterDTO request
    ){
        return ResponseHandler.generateResponse(Constants.SUCCESS_RETRIEVE_MSG, HttpStatus.OK, userService.register(request));
    }

}
