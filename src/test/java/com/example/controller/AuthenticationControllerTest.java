package com.example.controller;

import com.example.annotation.IT;
import com.example.dto.UserReadDto;
import com.example.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.example.dto.UserCreateEditDto.*;
import static com.example.dto.UserCreateEditDto.Fields.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@IT
@AutoConfigureMockMvc
class AuthenticationControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public AuthenticationControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void registration() throws Exception {
        mockMvc.perform(get("/registration"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/registration"))
                .andExpect(model().attributeExists("userReg"))
                .andExpect(model().attributeExists("roles"))
                .andExpect(model().attribute("roles", equalTo(Role.values())));
    }

    @Test
    void login() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/login"))
                .andExpect(model().attributeExists("userLog"));
    }

    @Test
    void createUser() throws Exception {
        mockMvc.perform(post("/registration")
                        .param(username, "testest")
                        .param(password, "testest10")
                        .param(phoneNumber, "254234")
                        .param(role, "ADMIN")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }

    @Test
    void loginAdmin() throws Exception {
        mockMvc.perform(post("/login")
                                .param(username, "Smith")
                                .param(password, "531242")
                        //role = admin
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/users")
                );
    }

    @Test
    void loginUser() throws Exception {
        mockMvc.perform(post("/login")
                                .param(username, "Ihor")
                                .param(password, "562666")
                        //role = user
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrlPattern("/users/{\\d+}")
                );
    }
}
