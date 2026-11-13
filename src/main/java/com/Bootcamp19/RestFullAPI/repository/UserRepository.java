package com.Bootcamp19.RestFullAPI.repository;

import com.Bootcamp19.RestFullAPI.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    boolean existsByUsername(String username);

    List<UserEntity> username(String username);
}
