package com.example.dto;

import com.example.entity.Role;
import lombok.Value;
import lombok.experimental.FieldNameConstants;

@Value
@FieldNameConstants
public class UserCreateEditDto {
    String username;
    String password;
    String phoneNumber;
    Role role;
}
