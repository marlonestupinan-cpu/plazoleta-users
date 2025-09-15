package com.pragma.users.infrastructure.input.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.users.application.dto.request.UserRequestDto;
import com.pragma.users.application.dto.response.UserResponseDto;
import com.pragma.users.application.handler.IUserHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserHandler userHandler;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateOwnerAndReturn201() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Juan");
        dto.setLastName("Perez");
        dto.setEmail("a@a.com");
        dto.setPhoneNumber("+573105412541");
        dto.setBirthDate(Date.from(Instant.now().minusSeconds(630720000))); // -20 years
        dto.setIdentityDocument("1234");
        dto.setPassword("1234");

        mockMvc.perform(post("/user/owner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        verify(userHandler).createOwner(any(UserRequestDto.class));
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        List<UserResponseDto> users = Arrays.asList(new UserResponseDto(), new UserResponseDto());

        when(userHandler.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        Long userId = 1L;
        UserResponseDto dto = new UserResponseDto();
        dto.setId(userId);

        when(userHandler.getUserById(userId)).thenReturn(dto);

        mockMvc.perform(get("/user/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserRequestDto dto = new UserRequestDto();
        dto.setName("Updated");

        mockMvc.perform(put("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());

        verify(userHandler).updateUser(any(UserRequestDto.class));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        Long id = 5L;

        mockMvc.perform(delete("/user/{id}", id))
                .andExpect(status().isNoContent());

        verify(userHandler).deleteUser(id);
    }
}
