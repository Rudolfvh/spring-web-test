package com.example.controller;

import com.example.annotation.IT;
import com.example.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static com.example.dto.UserCreateEditDto.Fields.password;
import static com.example.dto.UserCreateEditDto.Fields.username;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@IT
@AutoConfigureMockMvc
class UserControllerTest {
    private final MockMvc mockMvc;

    @Autowired
    public UserControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void userProfile() throws Exception {
        mockMvc.perform(get("/users/3"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/profile"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void users() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("user/users"))
                .andExpect(model().attributeExists("users"))
                .andExpect(model().attribute("users", hasSize(5)));
    }

    @Test
    void update() throws Exception {
        mockMvc.perform(post("/users/1/update")
                .param("username", "Evgeniy")
                .param("role", Role.USER.name())
        ).andExpectAll(
                status().is3xxRedirection(),
                redirectedUrl("/users/1")
        );

        mockMvc.perform(get("/users/1"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attribute(
                        "user", hasProperty(
                                "username", equalTo("Evgeniy")
                        )
                ))
                .andExpect(model().attribute(
                        "user", hasProperty(
                                "role", equalTo(Role.USER)
                        )
                ));
    }

    @Test
    void delete() throws Exception {
        mockMvc.perform(post("/users/1/delete")
                )
                .andExpectAll(
                        status().is3xxRedirection(),
                        redirectedUrl("/login")
                );
        mockMvc.perform(get("/users/1"))
                .andExpect(status().is4xxClientError());
    }
}
