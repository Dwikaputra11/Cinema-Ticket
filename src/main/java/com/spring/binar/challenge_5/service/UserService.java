package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.dto.AuthenticationRequestDTO;
import com.spring.binar.challenge_5.dto.AuthenticationResponseDTO;
import com.spring.binar.challenge_5.dto.UserRegisterDTO;
import com.spring.binar.challenge_5.dto.UserUpdateRequestDTO;
import com.spring.binar.challenge_5.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    Page<User> findAll(Pageable pageable);

    AuthenticationResponseDTO authentication(AuthenticationRequestDTO request);

    AuthenticationResponseDTO register(UserRegisterDTO request);

    AuthenticationResponseDTO update(UserUpdateRequestDTO updateUser);

    void delete(int id);
}
