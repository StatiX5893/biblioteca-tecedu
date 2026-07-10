package com.example.bibliotecatecedu.dto;

import com.example.bibliotecatecedu.model.Role;
import lombok.Data;

@Data
public class UserDto {

    private String username;
    private String password;
    private Role role;
}