package com.pragma.users.infrastructure.out.jpa.repository;

import com.pragma.users.infrastructure.out.jpa.entity.RoleEntity;
import com.pragma.users.infrastructure.out.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findById(Long id);

    void deleteById(Long id);

    Optional<UserEntity> findOneByRole(RoleEntity role);

    Optional<UserEntity> findByEmail(String email);
}
