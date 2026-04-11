package ru.kpfu.itis.mukminov.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.mukminov.dto.UserDto;
import ru.kpfu.itis.mukminov.model.User;
import ru.kpfu.itis.mukminov.service.UserService;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UserService userService;

    private UserDto testUserDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUserDto = new UserDto(1L, "Ivan", "Ivanov", "ivan@mail.ru");
        testUser = new User();
        testUser.setName("Ivan");
        testUser.setLastname("Ivanov");
        testUser.setEmail("ivan@mail.ru");
        testUser.setPassword("password123");
    }

    @Test
    void findAll_ShouldReturnUsers() throws Exception {
        given(userService.findAll()).willReturn(List.of(testUserDto));

        mockMvc.perform(get("/users")
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name").value("Ivan"))
                .andExpect(jsonPath("$[0].email").value("ivan@mail.ru"));

        verify(userService).findAll();
    }

    @Test
    void findById_ShouldReturnUser() throws Exception {
        given(userService.findById(1L)).willReturn(testUserDto);

        mockMvc.perform(get("/users/{id}", 1L)
                        .with(user("user").roles("USER")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Ivan"));

        verify(userService).findById(1L);
    }

    @Test
    void create_ShouldReturnSavedUser() throws Exception {
        given(userService.save(any(User.class))).willReturn(testUserDto);

        mockMvc.perform(post("/users")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ivan"));

        verify(userService).save(any(User.class));
    }

    @Test
    void update_ShouldReturnUpdatedUser() throws Exception {
        testUser.setId(1L);
        given(userService.update(any(User.class))).willReturn(testUserDto);

        mockMvc.perform(put("/users/{id}", 1L)
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(userService).update(any(User.class));
    }

    @Test
    void delete_ShouldReturnOk() throws Exception {
        doNothing().when(userService).delete(1L);

        mockMvc.perform(delete("/users/{id}", 1L)
                        .with(csrf())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isOk());

        verify(userService).delete(1L);
    }
}