package com.pragma.users.domain.usecase;

import com.pragma.users.domain.model.User;
import com.pragma.users.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserUseCaseTest {

    private IUserPersistencePort userPersistencePort;
    private UserUseCase userUseCase;

    @BeforeEach
    void setUp() {
        userPersistencePort = mock(IUserPersistencePort.class);
        userUseCase = new UserUseCase(userPersistencePort);
    }

    @Test
    void shouldSaveUser() {
        User user = new User();
        userUseCase.saveUser(user);

        verify(userPersistencePort, times(1)).saveUser(user);
    }

    @Test
    void shouldReturnAllUsers() {
        List<User> mockUsers = Arrays.asList(new User(), new User());
        when(userPersistencePort.getAllUsers()).thenReturn(mockUsers);

        List<User> users = userUseCase.getAllUsers();

        assertEquals(2, users.size());
        verify(userPersistencePort, times(1)).getAllUsers();
    }

    @Test
    void shouldReturnUserById() {
        Long userId = 1L;
        User mockUser = new User();
        when(userPersistencePort.getUser(userId)).thenReturn(mockUser);

        User result = userUseCase.getUser(userId);

        assertEquals(mockUser, result);
        verify(userPersistencePort).getUser(userId);
    }

    @Test
    void shouldUpdateUser() {
        User user = new User();
        userUseCase.updateUser(user);

        verify(userPersistencePort).updateUser(user);
    }

    @Test
    void shouldDeleteUser() {
        Long userId = 10L;
        userUseCase.deleteUser(userId);

        verify(userPersistencePort).deleteUser(userId);
    }
}

