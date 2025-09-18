package com.pragma.users.infrastructure.out.jpa.adapter;

import com.pragma.users.domain.model.Role;
import com.pragma.users.domain.model.User;
import com.pragma.users.domain.spi.IUserPersistencePort;
import com.pragma.users.infrastructure.exception.NoDataFoundException;
import com.pragma.users.infrastructure.exception.UserAlreadyExistException;
import com.pragma.users.infrastructure.exception.UserNotFoundException;
import com.pragma.users.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.users.infrastructure.out.jpa.mapper.IRoleEntityMapper;
import com.pragma.users.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.users.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {
    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;
    private final IRoleEntityMapper roleEntityMapper;

    @Override
    public void saveUser(User user) {
        if (userRepository.findById(user.getId()).isPresent()) {
            throw new UserAlreadyExistException();
        }
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public List<User> getAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        if (userEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }
        return userEntityMapper.toUserList(userEntityList);
    }

    @Override
    public User getUser(Long id) {
        return userEntityMapper.toUser(userRepository.findById(id).orElseThrow(UserNotFoundException::new));
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(userEntityMapper.toEntity(user));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findOneUserByRole(Role role) {
        return userRepository.findOneByRole(roleEntityMapper.toRoleEntity(role))
                .map(userEntityMapper::toUser)
                .orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUser)
                .orElseThrow(UserNotFoundException::new);
    }
}
