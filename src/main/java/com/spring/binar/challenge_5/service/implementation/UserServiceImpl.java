package com.spring.binar.challenge_5.service.implementation;

import com.spring.binar.challenge_5.security.JwtService;
import com.spring.binar.challenge_5.dto.AuthenticationRequestDTO;
import com.spring.binar.challenge_5.dto.AuthenticationResponseDTO;
import com.spring.binar.challenge_5.dto.UserRegisterDTO;
import com.spring.binar.challenge_5.dto.UserUpdateRequestDTO;
import com.spring.binar.challenge_5.exception.UserErrorException;
import com.spring.binar.challenge_5.models.Role;
import com.spring.binar.challenge_5.models.User;
import com.spring.binar.challenge_5.repos.UserRepository;
import com.spring.binar.challenge_5.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Override
    public Page<User> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public AuthenticationResponseDTO authentication(AuthenticationRequestDTO request) {
        authManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
        );

        var user    = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new UserErrorException("User not found."));
        var claims  = new HashMap<String, Object>();
        claims.put("phoneNumber", user.getPhoneNumber());

        var jwtToken = jwtService.generateToken(claims,user);

        return AuthenticationResponseDTO.builder()
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .token(jwtToken)
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthenticationResponseDTO register(UserRegisterDTO request) {

        if(request.getPassword() == null || request.getPhoneNumber() == null || request.getRole() == null || request.getUsername() == null)
            throw new UserErrorException("Input not valid! Please provide correct username, password, phone number and role");

        if(request.getUsername().isEmpty() || request.getPassword().isEmpty() || request.getPhoneNumber().isEmpty())
            throw new UserErrorException("Input is empty! Please fill with the correct username, password and phone number and role");

        if(request.getPhoneNumber().length() > 13 || request.getPhoneNumber().length() < 10)
            throw new UserErrorException("Invalid phone number, the length must be > 10 or < 14. Please provide a valid phone number");

        if(!isRoleValid(request.getRole().toUpperCase()))
            throw new UserErrorException("Role must be a valid role (COSTUMER, ADMIN, STAFF)");

        var user = User.builder()
                .username(request.getUsername())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(getRole(request.getRole().toUpperCase()))
                .build();

        userRepository.save(user);
        var claims  = new HashMap<String, Object>();
        claims.put("phoneNumber", user.getPhoneNumber());

        var jwtToken = jwtService.generateToken(claims,user);

        return AuthenticationResponseDTO.builder()
                .username(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .token(jwtToken)
                .role(user.getRole().name())
                .build();
    }

    @Override
    public AuthenticationResponseDTO update(UserUpdateRequestDTO updateUser) {
        return null;
    }

    @Override
    public void delete(int id) {

    }

    private boolean isRoleValid(String role) {
        return Arrays.stream(Role.values()).anyMatch((r -> r == Role.valueOf(role)));
    }

    private Role getRole(String role) {
        return Role.valueOf(role);
    }
}
