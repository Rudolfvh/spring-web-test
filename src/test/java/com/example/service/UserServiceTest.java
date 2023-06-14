package com.example.service;
import com.example.annotation.IT;
import com.example.dto.LoginDto;
import com.example.dto.UserCreateEditDto;
import com.example.dto.UserReadDto;
import com.example.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@IT
public class UserServiceTest {
    private final UserService userService;
    private static final Long USER_ID_1 = 1L;


    @Autowired
    public UserServiceTest(UserService userService) {
        this.userService = userService;
    }

    @Test
    void findAll() {
        List<UserReadDto> result = userService.findAll();
        assertThat(result).hasSize(5);
    }

    @Test
    void findById() {
        Optional<UserReadDto> maybeUser = userService.findById(USER_ID_1);
        assertTrue(maybeUser.isPresent());
        maybeUser.ifPresent(user -> assertEquals("Ivanov", user.getUsername()));
    }

    @Test
    void create() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "tests",
                "tests",
                Role.ADMIN
        );
        UserReadDto actualResult = userService.create(userDto);

        assertEquals(userDto.getUsername(), actualResult.getUsername());
        assertEquals(userDto.getPhoneNumber(), actualResult.getPhoneNumber());
        assertSame(userDto.getRole(), actualResult.getRole());
    }

    @Test
    void update() {
        UserCreateEditDto userDto = new UserCreateEditDto(
                "test@gmail.com",
                "tets",
                "tets",
                Role.ADMIN
        );

        Optional<UserReadDto> actualResult = userService.update(USER_ID_1, userDto);

        assertTrue(actualResult.isPresent());
        actualResult.ifPresent(user -> {
            assertEquals(userDto.getUsername(), user.getUsername());
            assertEquals(userDto.getPhoneNumber(), user.getPhoneNumber());
            assertSame(userDto.getRole(), user.getRole());
        });
    }

    @Test
    void login() {
        var userDtoReal = new LoginDto("Ivanov", "53532");
        assertThat(userService.login(userDtoReal)).isPresent();

        var userDtoUnreal = new LoginDto("test123", "testssss");
        assertThat(userService.login(userDtoUnreal)).isEmpty();
    }

    @Test
    void delete() {
        assertFalse(userService.delete(-124L));
        assertTrue(userService.delete(USER_ID_1));
    }
}
