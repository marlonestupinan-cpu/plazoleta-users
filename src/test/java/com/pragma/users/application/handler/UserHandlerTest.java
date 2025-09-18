package com.pragma.users.application.handler;

import com.pragma.users.application.dto.request.UserRequestDto;
import com.pragma.users.application.dto.response.UserResponseDto;
import com.pragma.users.application.handler.impl.UserHandler;
import com.pragma.users.application.mapper.IUserRequestMapper;
import com.pragma.users.application.mapper.IUserResponseMapper;
import com.pragma.users.domain.api.IRoleServicePort;
import com.pragma.users.domain.api.IUserServicePort;
import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import com.pragma.users.infrastructure.configuration.RoleProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserHandlerTest {

    private IUserServicePort userServicePort;
    private IRoleServicePort roleServicePort;
    private RoleProperties roleProperties;


    private final IUserRequestMapper userRequestMapper = Mappers.getMapper(IUserRequestMapper.class);
    private final IUserResponseMapper userResponseMapper = Mappers.getMapper(IUserResponseMapper.class);

    private UserHandler userHandler;

    @BeforeEach
    void setUp() {
        userServicePort = mock(IUserServicePort.class);
        roleServicePort = mock(IRoleServicePort.class);
        roleProperties = mock(RoleProperties.class);

        PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);

        userHandler = new UserHandler(
                userServicePort,
                userRequestMapper,
                userResponseMapper,
                roleServicePort,
                roleProperties,
                passwordEncoder
        );
    }

    @Test
    void shouldSaveUser() {
        UserRequestDto dto = generateUserRequestMock();
        User expectedUser = userRequestMapper.toUser(dto);

        userHandler.saveUser(dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userServicePort).saveUser(userCaptor.capture());

        User realUser = userCaptor.getValue();

        assertEquals(expectedUser, realUser);

    }

    @Test
    void shouldReturnAllUsers() {
        List<User> users = Arrays.asList(new User(), new User());
        List<UserResponseDto> responseDtos = Arrays.asList(new UserResponseDto(), new UserResponseDto());

        when(userServicePort.getAllUsers()).thenReturn(users);

        List<UserResponseDto> result = userHandler.getAllUsers();

        assertEquals(2, result.size());
        assertEquals(responseDtos, result);
    }

    @Test
    void shouldReturnUserById() {
        Long userId = 5L;
        User user = new User();
        UserResponseDto responseDto = new UserResponseDto();

        when(userServicePort.getUser(userId)).thenReturn(user);

        UserResponseDto result = userHandler.getUserById(userId);

        assertEquals(responseDto, result);
    }

    @Test
    void shouldUpdateUser() {
        UserRequestDto dto = generateUserRequestMock();
        User expectedUser = userRequestMapper.toUser(dto);

        userHandler.updateUser(dto);

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userServicePort).updateUser(userCaptor.capture());

        User realUser = userCaptor.getValue();

        assertEquals(expectedUser, realUser);
    }

    @Test
    void shouldDeleteUser() {
        Long userId = 99L;

        userHandler.deleteUser(userId);

        verify(userServicePort).deleteUser(userId);
    }

    @Test
    void shouldCreateOwner() {
        UserRequestDto dto = generateUserRequestMock();
        User expectedUser = userRequestMapper.toUser(dto);
        String ownerRoleName = "propietario";
        Role role = new Role();
        role.setName(ownerRoleName);
        expectedUser.setRole(role);

        when(roleProperties.getRoleName("owner")).thenReturn(ownerRoleName);
        when(roleServicePort.getRoleByName(ownerRoleName)).thenReturn(role);

        userHandler.createOwner(dto);

        assertEquals(role, expectedUser.getRole());
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userServicePort).saveUser(userCaptor.capture());

        User realUser = userCaptor.getValue();
        assertEquals(expectedUser, realUser);
    }

    @Test
    void shouldReturnTrueWhenUserIsOwner() {
        Long userId = 1L;
        String ownerRoleName = "propietario";

        Role ownerRole = new Role();
        ownerRole.setId(1L);
        ownerRole.setName(ownerRoleName);

        User userWithOwnerRole = new User();
        userWithOwnerRole.setId(userId);
        userWithOwnerRole.setRole(ownerRole);

        when(userServicePort.getUser(userId)).thenReturn(userWithOwnerRole);
        when(roleProperties.getRoleName("owner")).thenReturn(ownerRoleName);
        when(roleServicePort.getRoleByName(ownerRoleName)).thenReturn(ownerRole);

        boolean isOwner = userHandler.isOwner(userId);

        assertTrue(isOwner);

        verify(userServicePort).getUser(userId);
        verify(roleProperties).getRoleName("owner");
        verify(roleServicePort).getRoleByName(ownerRoleName);
    }

    @Test
    void shouldReturnFalseWhenUserIsNotOwner() {
        Long userId = 1L;
        Long ownerRoleId = 1L;
        Long clientRoleId = 2L;
        String ownerRoleName = "propietario";
        String clientRoleName = "cliente";

        Role ownerRole = new Role();
        ownerRole.setId(ownerRoleId);
        ownerRole.setName(ownerRoleName);

        Role clientRole = new Role();
        clientRole.setId(clientRoleId);
        clientRole.setName(clientRoleName);

        User userWithClientRole = new User();
        userWithClientRole.setId(userId);
        userWithClientRole.setRole(clientRole);

        when(userServicePort.getUser(userId)).thenReturn(userWithClientRole);
        when(roleProperties.getRoleName("owner")).thenReturn(ownerRoleName);
        when(roleServicePort.getRoleByName(ownerRoleName)).thenReturn(ownerRole);

        boolean isOwner = userHandler.isOwner(userId);

        assertFalse(isOwner);

        verify(userServicePort).getUser(userId);
        verify(roleProperties).getRoleName("owner");
        verify(roleServicePort).getRoleByName(ownerRoleName);
    }


    UserRequestDto generateUserRequestMock() {
        UserRequestDto user = new UserRequestDto();
        LocalDate date = LocalDate.now().minusYears(20);
        Date birthDate = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setBirthDate(birthDate);
        return user;
    }
}
