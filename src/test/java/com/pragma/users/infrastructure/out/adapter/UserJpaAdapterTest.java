package com.pragma.users.infrastructure.out.adapter;

import com.pragma.users.domain.model.User;
import com.pragma.users.infrastructure.exception.NoDataFoundException;
import com.pragma.users.infrastructure.exception.UserAlreadyExistException;
import com.pragma.users.infrastructure.exception.UserNotFoundException;
import com.pragma.users.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.users.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserJpaAdapterTest {

    private IUserRepository userRepository;
    @Autowired
    private IUserEntityMapper userEntityMapper;
    @Autowired
    private IRoleEntityMapper roleEntityMapper;
    private UserJpaAdapter userJpaAdapter;

    private User user;
    private UserEntity userEntity;


    @BeforeEach
    void setUp() {
        user = createUserMock();
        userEntity = createUserRequestMock();
        userRepository = mock(IUserRepository.class);

        userJpaAdapter = new UserJpaAdapter(userRepository, userEntityMapper, roleEntityMapper);
    }

    @Test
    void testSaveUserWhenUserDoesNotExistThenSaveSuccessfully() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        userJpaAdapter.saveUser(user);

        verify(userRepository).save(userEntity);
    }

    @Test
    void testSaveUserWhenUserAlreadyExistsThenThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        assertThrows(UserAlreadyExistException.class, () -> userJpaAdapter.saveUser(user));
    }

    @Test
    void testGetAllUsersWhenUsersExistThenReturnUserList() {
        List<UserEntity> entities = Arrays.asList(userEntity);

        when(userRepository.findAll()).thenReturn(entities);

        List<User> result = userJpaAdapter.getAllUsers();

        assertEquals(1, result.size());
        assertEquals("Juan", result.get(0).getName());
    }

    @Test
    void testGetAllUsersWhenNoUsersExistThenThrowException() {
        when(userRepository.findAll()).thenReturn(List.of());

        assertThrows(NoDataFoundException.class, () -> userJpaAdapter.getAllUsers());
    }

    @Test
    void testGetUserWhenUserExistsThenReturnUser() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(userEntity));

        User result = userJpaAdapter.getUser(1L);

        assertEquals("Juan", result.getName());
    }

    @Test
    void testGetUserWhenUserDoesNotExistThenThrowException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userJpaAdapter.getUser(1L));
    }

    @Test
    void testUpdateUserWhenUserExistsThenUpdateSuccessfully() {
        userJpaAdapter.updateUser(user);

        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());

        assertEquals(userEntity, userCaptor.getValue());
    }

    @Test
    void testDeleteUserWhenUserExistsThenDeleteSuccessfully() {
        userJpaAdapter.deleteUser(1L);

        verify(userRepository).deleteById(1L);
    }

    User createUserMock() {
        User userMock = new User();
        userMock.setId(1L);
        userMock.setName("Juan");
        userMock.setLastName("Perez");
        userMock.setEmail("a@a.com");
        userMock.setPhoneNumber("+573105412541");
        userMock.setBirthDate(Date.from(Instant.now().minusSeconds(630720000))); // -20 years
        userMock.setIdentityDocument("1234");
        userMock.setPassword("1234");

        return userMock;
    }

    UserEntity createUserRequestMock() {
        UserEntity userRequestMock = new UserEntity();
        userRequestMock.setId(1L);
        userRequestMock.setName("Juan");
        userRequestMock.setLastName("Perez");
        userRequestMock.setEmail("a@a.com");
        userRequestMock.setPhoneNumber("+573105412541");
        userRequestMock.setBirthDate(Date.from(Instant.now().minusSeconds(630720000))); // -20 years
        userRequestMock.setIdentityDocument("1234");
        userRequestMock.setPassword(BCrypt.hashpw("1234", BCrypt.gensalt()));

        return userRequestMock;
    }
}

