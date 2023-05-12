package com.spring.binar.challenge_5.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    private String username;
    private String password;
    private String phoneNumber;
    private String role;

}
