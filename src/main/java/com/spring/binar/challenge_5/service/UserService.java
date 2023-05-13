package com.spring.binar.challenge_5.service;

import com.spring.binar.challenge_5.dto.*;
import com.spring.binar.challenge_5.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    Page<UserResponseDTO> findAll(Pageable pageable);

    List<UserResponseDTO> findAll();

    AuthenticationResponseDTO authentication(AuthenticationRequestDTO request);

    AuthenticationResponseDTO register(UserRegisterDTO request);

    AuthenticationResponseDTO update(UserUpdateRequestDTO updateUser);

    void delete(int id);
}
