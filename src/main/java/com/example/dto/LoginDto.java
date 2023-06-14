package com.example.dto;

import com.example.entity.Role;
import jakarta.persistence.*;
import lombok.Value;

@Value
public class LoginDto {
    String username;
    String password;
}
