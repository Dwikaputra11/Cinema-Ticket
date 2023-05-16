package com.spring.binar.challenge_5.controller.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.spring.binar.challenge_5.dto.AuthenticationRequestDTO;
import com.spring.binar.challenge_5.dto.UserRegisterDTO;
import com.spring.binar.challenge_5.dto.UserResponseDTO;
import com.spring.binar.challenge_5.models.User;
import com.spring.binar.challenge_5.service.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping(value = "/auth")
public class UserMvcController {
    
    private final UserService userService;

    @Autowired
    public UserMvcController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/public/login-form")
    public String loginPublic(Model model){

        AuthenticationRequestDTO authenticationRequestDTO = new AuthenticationRequestDTO();
        model.addAttribute("user", authenticationRequestDTO);

        return"user/login";
    }

    @GetMapping("/public/register-form")
    public String registerPublic(Model model){

        UserRegisterDTO userRegisterDTO = new UserRegisterDTO();
        model.addAttribute("user", userRegisterDTO);

        return"user/register";
    }

    @PostMapping("/public/login-form/save")
    public String loginPublicSave(@ModelAttribute("user") AuthenticationRequestDTO authUser){
        userService.authentication(authUser);
        return"redirect:/schedule/list";
    }

    @PostMapping("/public/register-form/save")
    public String registerPublicSave(@Valid @ModelAttribute("user") UserRegisterDTO userRegisterDTO){
        userService.register(userRegisterDTO);
        return"redirect:/user/login";
    }

}
