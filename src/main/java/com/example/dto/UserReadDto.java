package com.example.dto;

import com.example.entity.Role;
import lombok.Value;

@Value
public class UserReadDto {
    Long id;
    String username;
    String phoneNumber;
    Role role;
}
